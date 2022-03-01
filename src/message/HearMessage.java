package src.message;

public class HearMessage extends Message {

	public String sender;
	public String message;
	
	public HearMessage(int turn, String sender, String message) {
		
		super(turn, "HEAR_MESSAGE");
		
		this.sender  = sender;
		this.message = message;
	}

    public String toString() {
    	
    	String str = super.toString();
    	
    	return str;
    }
}
