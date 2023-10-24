package blockpack;
import transactionspack.Transaction;
public class Block {
    public final String  Hash;
    public final String  prevHash;
    public final Transaction[] Transactions;
    public final int nonce;
    public Block(String hash, String prevHash, Transaction[] transactions, int nonce) {
        this.Hash = hash;
        this.prevHash = prevHash;
        this.Transactions = transactions;
        this.nonce = nonce;
    }
    public Block(String hash) {
        this.Hash = hash;
        this.prevHash = null;
        this.Transactions = null;
        this.nonce = 0;
    }
}
