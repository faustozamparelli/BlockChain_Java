package userpack;

import net.Network;
import transactionspack.Transaction;
import blockpack.Block;
import cryptographypack.CRYPTO;
import java.security.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class User {
    public final Network Network;
    public String Username;
    private final PrivateKey PrivateKey;
    public final PublicKey PublicKey;
    public final int UserNumber;
    private ArrayList<int[]> wallet = new ArrayList<int[]>(); //first element amount, second element block number, third element transaction number
    public void gotmoney(int[] reference) {synchronized (indexkey){wallet.add(reference);};}
    private final Miner miner;

    public User(Network Network) throws NoSuchAlgorithmException {
        this.Network = Network;
        KeyPair keys = CRYPTO.constructkeys();
        this.PrivateKey = keys.getPrivate();
        this.PublicKey = keys.getPublic();
        this.UserNumber = Network.NewUser(this);
        this.miner = new Miner();
        this.Username = "USER - " + UserNumber;
    }
    public User(Network Network, String Username)  throws NoSuchAlgorithmException{
        this(Network);
        this.Username = Username;
    }
    private  int index = 0;
    private final Object indexkey = new Object();
    public void give(int recivernumber, int amount, int minereward) throws Exception {
        int money =0;
        ArrayList<int[]> References = new ArrayList<int[]>();
        synchronized (indexkey) {
            int indexstart = index;
            if (index >= wallet.size()) {
                index = 0;
            }
            if (index < 0) {
                index = 0;
            }
            for (; index < wallet.size(); index++) {
                if (money >= amount + minereward) {
                    break;
                }
                int[] reference = wallet.get(index);
                money += reference[0];
                References.add(new int[]{reference[1], reference[2]});
            }
            if (money >= amount + minereward) {
                int getback = money - amount - minereward;
                Transaction transaction = new Transaction(this.PrivateKey, this.PublicKey, getback, References, recivernumber, amount, minereward);
                Network.pushTransaction(transaction);
            } else {
                index = indexstart;
                System.out.println(Username + " has not enough money to do transaction.");
            }
        }
        }
    public void mine() {
        miner.start();
    }
    public void stopmining() {
        miner.stopmining();
    }
    public void removeReferences(int size) {
        synchronized (indexkey) {
            index -= size;
            while (size != 0) {
                size--;
                wallet.remove(0);
            }
        }
    }
    private class Miner extends Thread{
        boolean mine = false;
        void stopmining() {
            this.mine = false;
        }

        public void run() {
            Block lastblock = null;
            mine = true;
            while (mine) {
                if (lastblock == null) {
                    lastblock = Network.getlastblock();
                }
                else {
                    while (Network.getlastblock() == lastblock) {
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                        }
                    }
                    lastblock = Network.getlastblock();
                }
                Transaction[] myarray = buildarray();
                Network.pushBlock(buildblock(lastblock.Hash, myarray));
            }
        }

        private Block buildblock(String previusblockash, Transaction[] array) {
            String difficulty = "0".repeat(Network.difficulty);
            int nonce = 0;
            while (true) {
                String Hash = CRYPTO.createHash(previusblockash, array, nonce);
                if (Hash.startsWith(difficulty)) {
                    return new Block(Hash, previusblockash, array, nonce);
                }
                nonce++;
            }
        }

        private Transaction[] buildarray() {
            Set<PublicKey> RepetedPublicKeys = new HashSet<>();
            int i =0;

            Transaction[] checkedTransactions = new Transaction[5];
            int finded = 1;
            int reward = Network.newblockreward;
            while ((Network.TransactionsPool.size() >= i) && (finded < 5)) {
                Transaction tocheck = Network.getTransactionsPool(i);
                i++;
                if (tocheck == null){continue;}
                if(RepetedPublicKeys.contains(tocheck.publickey) ){continue;}
                Object[] check = {false, 0};
                try {check = check(tocheck);} catch (Exception e) {Network.notvalidTransaction(tocheck);}
                if ((boolean) (check[0]) == true) {
                    RepetedPublicKeys.add(tocheck.publickey);
                    checkedTransactions[finded] = tocheck;
                    reward += (int) (check[1]);
                    finded++;
                }
                else{Network.notvalidTransaction(tocheck);}
            }
            checkedTransactions[0] = new Transaction(UserNumber, reward);
            return checkedTransactions;
        }

         private Object[] check(Transaction transaction) throws Exception {
            Object[] ret = {false, 0};
            ArrayList<int[]> references = transaction.references;
            if (Transaction.arequal(CRYPTO.decrypt(transaction.signature, transaction.publickey),(transaction.transactiondata))) {
                int money = 0;
                for (int i = 0; i < references.size(); i++) {
                    int[] reference = references.get(i);
                    Object[] transactionsi = Network.TransactionsList.get(reference[0])[reference[1]];
                    if (((boolean) (transactionsi[0]) == true) && (((PublicKey)(transactionsi[1])).equals(transaction.publickey))) {
                        money += (int) (transactionsi[2]);
                    } else return ret;
                }
                if (money == transaction.transactiondata.amount + transaction.transactiondata.minereward + transaction.transactiondata.getback) {
                    ret[0] = true;
                    ret[1] = transaction.transactiondata.minereward;
                    }
                }
            return ret;
        }
    }
}


