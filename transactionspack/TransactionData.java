package transactionspack;
import java.io.Serializable;

public class TransactionData implements Serializable {
    public final int reciver;
    public final int amount;
    public final int minereward;
    public final int getback;

    public TransactionData( int reciver, int amount, int minereward, int getback){
        this.reciver = reciver;
        this.amount = amount;
        this.minereward = minereward;
        this.getback = getback;
    }
}