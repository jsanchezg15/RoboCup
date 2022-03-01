package src.message;

public class Message {
    
    public int    turn;
    public String type;

    public Message(int turn, String type) {
        this.turn = turn;
        this.type = type;
    }
    
    public String toString() {
    	
    	String str = "";
    	
    	str += "Type: " + this.type + "\n";
    	str += "Turn: " + this.turn + "\n";
    	
    	return str;
    }

}
