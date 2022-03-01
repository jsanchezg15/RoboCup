package src;

import src.team.*;
import src.message.*;

public class Programa {
    
    public static void main(String[] args) {

        System.out.println("Start");

        Team t1 = new Team("Jorge");
        Team t2 = new Team("Valero");

        t1.start();
        t2.start();
    }
    
}
