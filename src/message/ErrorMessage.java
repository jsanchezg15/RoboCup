package src.message;

public class ErrorMessage extends Message {

	String error_message;
	
    public ErrorMessage(String error) {
        super(0, "ERROR_MESSAGE");
        
        this.error_message = error;
        
        System.out.println("ERROR_MESSAGE: " + error);
    }
    
    public String toString() {
    	
    	String str = super.toString();
    	
    	str += "ErrorMessage: " + this.error_message + "\n";
    	
    	return str;
    }
}
