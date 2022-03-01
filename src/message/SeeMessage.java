package src.message;

public class SeeMessage extends Message {

    public String   ball;
    public String[] flags;
    public String[] players;
    public String[] lines;
    public String[] goals;

    public SeeMessage(int turn, String ball, String[] flags, String[] players, String[] lines, String[] goals) {
        
        super(turn, "SEE_MESSAGE");
        
        this.ball    = ball;
        this.flags   = flags;
        this.players = players;
        this.lines   = lines;
        this.goals   = goals;
    }
    
    public String toString() {
    	
    	String str = super.toString();
    	
    	str += "Ball: " + this.ball + "\n";
    	str += "Flags: \n";

    	for(int i = 0; i < this.flags.length; i++) 
        	str += this.flags[i] + "\n";
    	
    	str += "Players: \n";

    	for(int i = 0; i < this.players.length; i++) 
        	str += this.players[i] + "\n";
    	
    	str += "Lines: \n";

    	for(int i = 0; i < this.lines.length; i++) 
        	str += this.lines[i] + "\n";
    	
    	str += "Goals: \n";

    	for(int i = 0; i < this.goals.length; i++) 
        	str += this.goals[i] + "\n";

    	return str;
    }
    
}
