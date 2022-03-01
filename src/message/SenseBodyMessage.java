package src.message;

public class SenseBodyMessage extends Message {

    /*
    (
        sense_body 0 
        (view_mode high normal) 
        (stamina 8000 1) 
        (speed 0) 
        (kick 0) 
        (dash 0) 
        (turn 0) 
        (say 0)
    )
    */

	public String stamina;
	public String speed;
	
    public SenseBodyMessage(int turn, String stamina, String speed) {
        super(turn, "SENSE_BODY_MESSAGE");
        
        this.stamina = stamina;
        this.speed   = speed;
    }
    
    public String toString() {
    	
    	String str = super.toString();
    	
    	return str;
    }
}
