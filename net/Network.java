package net;

import transactionspack.Transaction;
import userpack.User;
import blockpack.Block;
import cryptographypack.CRYPTO;

import java.security.PublicKey;
import java.util.*;


public class Network extends Thread {
    public ArrayList<Block> BlockChain= new ArrayList<Block>();
    public ArrayList<Block> BlockQueque = new ArrayList<Block>();
    public volatile ArrayList<Transaction> TransactionsPool= new ArrayList<Transaction>();
    private Object TransactionsPoolKey = new Object();
    public volatile ArrayList<Object[][]> TransactionsList = new ArrayList<Object[][]>();
    public ArrayList<User> Users= new ArrayList<User>();
    public Dictionary<PublicKey, User> PublicKeys= new Hashtable<>();
    public int NewUser(User user) {
        PublicKeys.put(user.PublicKey, user);
        Users.add(user);
        System.out.println("USER-"+ n + " JOINED THE NETWORK");
        return n++;
    }// could be useful
    public int newblockreward;
    private int n = 0;
    public int difficulty;
    private boolean run = false; public void stopnet(){run=false;}
    volatile public boolean Queque = false;
    private Set<Transaction> RepetedTransactionsChecks= new HashSet<>();
    public Network(int difficulty, int blockreward) {
        this.difficulty = difficulty;
        this.newblockreward = blockreward;
        Block GENESIS = new Block(("0".repeat(difficulty) + "F".repeat(256 - difficulty)));
        BlockChain.add(GENESIS);
        TransactionsList.add(new Object[9][3]);
    }
    public Block getlastblock() {
        return BlockChain.get(BlockChain.size() - 1);
    }
    public void pushBlock(Block builtblock) {
        System.out.println(Users.get(builtblock.Transactions[0].transactiondata.reciver).Username + " mined a new block.");
        BlockQueque.add(builtblock);
        this.Queque = true;
    }
    public Transaction getTransactionsPool(int index) {synchronized (TransactionsPoolKey) {try{return TransactionsPool.get(index);} catch(Exception e){return null;}}}
    public void pushTransaction (Transaction transaction){synchronized (TransactionsPoolKey){TransactionsPool.add(transaction);}}
    public void removet (Transaction transaction) {
            synchronized (TransactionsPoolKey) {
                if (!RepetedTransactionsChecks.contains(transaction)) {{RepetedTransactionsChecks.add(transaction);
                        System.out.println("TRANSACTION IS NOT VALID: " + PublicKeys.get(transaction.publickey).Username+ " give " + transaction.transactiondata.amount + " to " + Users.get(transaction.transactiondata.reciver).Username );
                        TransactionsPool.remove(transaction);
                    }
                }
            }
    }
    public void removeg (Transaction transaction) {
        synchronized (TransactionsPoolKey) {TransactionsPool.remove(transaction);
            }
    }
    public void notvalidTransaction(Transaction transaction) {
        new removeTransactions(transaction).start();
    }
    public void run() {
        System.out.println("NETWORK IS STARTED");
        currentThread().setPriority(9);
        run=true;
        while (run) {
            waitfornewblock();
            blockcheck();
        }
    }
    private void waitfornewblock() {
        try {
            while (!Queque) {
                System.out.println("NETWORK is waiting for a new block . . .");
                sleep(2000);
            }
        } catch (Exception e) {
        }
    }
    private String passedtransactions(Transaction[] transactions) {
        String transprint = "[ ";
        transprint += Users.get(transactions[0].transactiondata.reciver).Username + " gets " + transactions[0].transactiondata.amount;

        for (int i = 1; i<5; i++){
            Transaction transaction = transactions[i];
            if (transaction == null){break;}
            transprint += ", "+ Users.get(transaction.transactiondata.reciver).Username + " gets " + transactions[i].transactiondata.amount + " from " + PublicKeys.get(transaction.publickey).Username;
            if (transaction.transactiondata.getback != 0){transprint +=", " + PublicKeys.get(transaction.publickey).Username + " gets back " + transaction.transactiondata.getback;}
        }
        transprint += " ]";
        return transprint;
    }
    private  void blockcheck() {
        while (Queque) {
            Block newBlock = BlockQueque.remove(0);
            boolean validity = checkforvalidity(newBlock);
            System.out.println();
            if (validity) {
                System.out.print("Correct transactions. . . ");
                sendtowallets(newBlock.Transactions,BlockChain.size());
                System.out.println("BLOCK "+BlockChain.size()+" OF BLOCKCHAIN HAS BEEN MINED BY "+ Users.get(newBlock.Transactions[0].transactiondata.reciver).Username + " !!!");
                System.out.println("Passed Transactions: "+passedtransactions(newBlock.Transactions));
                System.out.println("Pool: "+TransactionsPool + " " + TransactionsPool.size());
                System.out.println();
                BlockChain.add(newBlock);
            }else{System.out.println();System.out.println(Users.get(newBlock.Transactions[0].transactiondata.reciver).Username + " Block is NOT VALID.");}
            if (BlockQueque.size() == 0) {
                Queque = false;
            }
        }
    }
    private boolean checkforvalidity(Block newBlock) {
        System.out.println("NETWORK is checking for " + Users.get(newBlock.Transactions[0].transactiondata.reciver).Username + " Block validity.");
        Block previus = getlastblock();
        if (newBlock.prevHash == previus.Hash) {
            System.out.print("Correct prevASH . . . ");
            if (CRYPTO.createHash(newBlock.prevHash, newBlock.Transactions, newBlock.nonce).equals(newBlock.Hash)) {
                System.out.print("Correct ASH . . . ");
                return checkforTransactions(newBlock);
            } else return false;
        } else return false;
    }
    private boolean checkforTransactions(Block newBlock) {
        Set<PublicKey> RepetedPublicKeys = new HashSet<>();
        Transaction[] Transactions = newBlock.Transactions;
        Object[][] TransBool = new Object[9][3];
        int minerreward = newblockreward;
        int counter = 0;
        for (int i = 1; i < 9; i+=2) {
            ++counter;
            Transaction transaction = Transactions[counter];
            if (transaction == null){break;}
            if(RepetedPublicKeys.contains(transaction.publickey)){return false;}
            RepetedPublicKeys.add(transaction.publickey);
            try {
                if (checktransaction(transaction)){
                    TransBool[i][0]= (Object)true;
                    TransBool[i][1]= (Object)Users.get(transaction.transactiondata.reciver).PublicKey;
                    TransBool[i][2]= (Object)transaction.transactiondata.amount;
                    minerreward += transaction.transactiondata.minereward;
                    TransBool[i+1][0]= (Object)true;
                    TransBool[i+1][1]= (Object)transaction.publickey;
                    TransBool[i+1][2]= (Object)transaction.transactiondata.getback;
                } else {removet(transaction);return false;}
            } catch (Exception e) {return false;}
        }
        if (minerreward == Transactions[0].transactiondata.amount){
            TransBool[0][0]= (Object)(true);
            TransBool[0][1]= (Object)Users.get(Transactions[0].transactiondata.reciver).PublicKey;
            TransBool[0][2]= (Object)Transactions[0].transactiondata.amount;
            TransactionsList.add(TransBool);
            return true;
        }else return false;
    }
    // set transaction bool to 0
    private boolean checktransaction (Transaction transaction) throws Exception {
        ArrayList<int[]> references = transaction.references;
        if (Transaction.arequal(CRYPTO.decrypt(transaction.signature, transaction.publickey),transaction.transactiondata)) {
            int money = 0;
            for (int i = 0; i < references.size(); i++) {
                int[] reference = references.get(i);
                Object[] transactionsi = TransactionsList.get(reference[0])[reference[1]];
                if (((boolean) (transactionsi[0]) == true) && (((PublicKey)(transactionsi[1])).equals(transaction.publickey))) {
                    money += (int)(transactionsi[2]);
                } else return false;
            }
            if (money == transaction.transactiondata.amount + transaction.transactiondata.minereward + transaction.transactiondata.getback) {
                return true;
            }
        }
        return false;

    }
    private void sendtowallets (Transaction[]Transactions,int blocknumber){
            int[] referencem ={Transactions[0].transactiondata.amount, blocknumber, 0};
            Users.get(Transactions[0].transactiondata.reciver).gotmoney(referencem);
            int counter = 1;
            for (int i = 1; i<9; i+=2){
                Transaction transaction = Transactions[counter];
                counter++;
                if(transaction==null){break;}
                User recever = Users.get(transaction.transactiondata.reciver);
                int[] reference = {transaction.transactiondata.amount, blocknumber, i};
                recever.gotmoney(reference);
                User sender = PublicKeys.get(transaction.publickey);
                if (transaction.transactiondata.getback !=0) {
                    int[] referencegetback = {transaction.transactiondata.getback, blocknumber, i + 1};
                    sender.gotmoney(referencegetback);
                }
                ArrayList<int[]> referencesi = transaction.references;
                sender.removeReferences(referencesi.size());
                for (int j = 0; j<referencesi.size(); j++){
                    int[] referenceij = referencesi.get(j);
                    TransactionsList.get(referenceij[0])[referenceij[1]][0] = (Object)(false);
                }
                removeg(transaction);
        }
    }
    private class removeTransactions extends Thread {
            private Transaction transaction;

            removeTransactions(Transaction transaction) {
                this.transaction = transaction;
            }

            public void run() {
                if (TransactionsPool.contains(transaction)  && !RepetedTransactionsChecks.contains(transaction)) {
                    try {
                        if (Transaction.arequal(CRYPTO.decrypt(transaction.signature, transaction.publickey),(transaction.transactiondata))) {
                            ArrayList<int[]> references = transaction.references;
                            int money = 0;
                            for (int i = 0; i < references.size(); i++) {
                                int[] reference = references.get(i);
                                Object[] transactionsi = TransactionsList.get(reference[0])[reference[1]];
                                if (((boolean) (transactionsi[0]) == true) && (((PublicKey)(transactionsi[1])).equals(transaction.publickey))) {
                                    money += (int) (transactionsi[2]);
                                } else removet(transaction);
                            }
                            if (money == transaction.transactiondata.amount + transaction.transactiondata.minereward + transaction.transactiondata.getback) {
                                return;
                            } else removet(transaction);
                        } else removet(transaction);
                    } catch (Exception e) {
                        removet(transaction);;
                    }

                }
            }
        }
}
