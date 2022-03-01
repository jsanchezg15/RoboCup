package src.message;

public class Parser {

    public static Message parse(String msg) {

        if(msg.startsWith("(sense_body")) 
            return parseSenseBodyMessage(msg);
        
        if(msg.startsWith("(see")) 
            return parseSeeMessage(msg);

        if(msg.startsWith("(hear")) 
            return parseHearMessage(msg);
        
        if(msg.startsWith("(init")) 
            return parseInitMessage(msg);
        
        return new ErrorMessage(msg);
    }

    private static SeeMessage parseSeeMessage(String msg) {
    	try {
            String[] words = msg.split("[(][(]");

            int flagsNum   = 0;
            int playersNum = 0;
            int linesNum   = 0;
            int goalsNum   = 0;

            for(int i = 1; i < words.length; i++) {
            	
                if(words[i].startsWith("flag")) 
                    flagsNum++;
                
                else if(words[i].startsWith("player"))
                    playersNum++;
                
                else if(words[i].startsWith("line"))
                	linesNum++;
                
                else if(words[i].startsWith("goal"))
                	goalsNum++;
            }

            int      turn    = Integer.parseInt(words[0].split(" ")[1].replace(")", ""));
            String   ball    = "NOT_FOUND";
            String[] flags   = new String[flagsNum];
            String[] players = new String[playersNum];
            String[] lines   = new String[linesNum];
            String[] goals   = new String[goalsNum];

            for(int i = 1; i < words.length; i++) {
            	
                if(words[i].startsWith("flag")) 
                    flags[--flagsNum] = words[i];
                
                else if(words[i].startsWith("player")) 
                    players[--playersNum] = words[i];
                
                else if(words[i].startsWith("line")) 
                    lines[--linesNum] = words[i];
                
                else if(words[i].startsWith("goal")) 
                	goals[--goalsNum] = words[i];
                
                else if(words[i].startsWith("ball"))
                    ball = words[i];
            }

            return new SeeMessage(turn, ball, flags, players, lines, goals);        
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return new SeeMessage(1, "NOT_FOUND", new String[0], new String[0], new String[0], new String[0]);
    	}
    }

    private static SenseBodyMessage parseSenseBodyMessage(String msg) {
    	
    	
        return new SenseBodyMessage(0, "", "");
    }
    
    private static HearMessage parseHearMessage(String msg) {
    	try {
        	String[] words  = msg.split(" ");
        	
        	Integer time    = Integer.parseInt(words[1]);
        	String  sender  = words[2];
        	String  message = words[3];
        	
        	return new HearMessage(time, sender, message);	
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    private static InitMessage parseInitMessage(String msg) {
        try {
	        String[] words  = msg.split(" ");
	
			char   side     = words[1].charAt(0);
			int    num      = Integer.parseInt(words[2]);
	        String playMode = words[3].replace(")", "");
	
	        return new InitMessage(side, num, playMode);
        }
        catch(Exception e) {
        	e.printStackTrace();
        	return null;
        }
    }
}