package transactionspack;
import cryptographypack.CRYPTO;
import java.security.*;
import java.util.ArrayList;


public class Transaction {
    public final byte[] signature;
    public final PublicKey publickey;
    public final TransactionData transactiondata;
    public final ArrayList<int[]> references;//element 0 is block number, 1 is trans position

    public Transaction(PrivateKey PrivateK, PublicKey publickey, int getback, ArrayList<int[]> references, int recivernumber, int amount, int minereward)  throws Exception{
        this.transactiondata = new TransactionData(recivernumber, amount, minereward, getback);
        this.signature = CRYPTO.encrypt(transactiondata, PrivateK);
        this.publickey = publickey;
        this.references = references;

    }
    public Transaction(int minernumber, int reward){
        this.transactiondata = new TransactionData(minernumber, reward, 0, 0);
        this.signature = null;
        this.publickey = null;
        this.references = null;
    }
    public static boolean arequal(TransactionData transactiondata1, TransactionData transactiondata2) {return (transactiondata1.reciver == transactiondata2.reciver)&&(transactiondata1.amount == transactiondata2.amount)&&(transactiondata1.minereward == transactiondata2.minereward)&&(transactiondata1.getback == transactiondata2.getback);}

}
