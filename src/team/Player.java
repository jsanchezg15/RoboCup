package src.team;

import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import src.message.*;

public class Player extends Thread {

	int            num;
	char           side;
	String         team_name;
	String         init_cmd;
	String         play_mode;
	FileWriter     file;

	int            PORT;
	InetAddress    HOST;
	DatagramSocket sck;

	public Player(String team_name, int num) {
		try {
			this.team_name = team_name;
			this.init_cmd  = "(init " + team_name + ")";
			this.play_mode = "before_kick_off";
			this.num       = num;
			this.file      = new FileWriter("./src/dataFiles/data " + this.team_name + this.num + ".txt");			

			this.PORT = 6000;
			this.HOST = InetAddress.getByName("127.0.0.1");
			this.sck  = new DatagramSocket();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			init();

			while(true) {
				String  str = receive(); 
				Message msg = Parser.parse(str);

				if(msg == null)
					continue;

				String cmd = processMsg(msg);
				
				if(this.play_mode.equals("before_kick_off"))
					continue;
				
				this.file.write(str + "\n\n" + cmd + "\n\n");

				if(cmd == null)
					continue;

				this.send(cmd);
				
				Thread.sleep(50);
			}
		}
		catch(Exception e) {
			try {
				e.printStackTrace();

				if(this.sck  != null) this.sck.close();
				if(this.file != null) this.file.close();	
			}
			catch(IOException error) {
				System.out.println("Error al cerrar los recursos");
			}
		}
	}

	private void init() throws IOException {
		
		/*
		 * Envia el comando de iniciar jugador.
		 * Procesa la respuesta del servidor.
		 * Envia la posicion inicial del jugador.
		 */
		
		this.send(this.init_cmd);

		String  str = receive();
		Message msg = Parser.parse(str);
		String  cmd = processInitMsg((InitMessage) msg);

		this.send(cmd);

		this.file.write(cmd + "\n\n");
	}
	
	private void send(String cmd) {
		
		// Envia el string al servidor
		
		try {
			byte[]         msg = cmd.getBytes();
			DatagramPacket out = new DatagramPacket(msg, msg.length, HOST, PORT);
	
			this.sck.send(out);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String receive() {
		
		// Recibe el mensage del servidor y lo devuelve
		
		try {
			byte[]         msg = new byte[1000];
			DatagramPacket in  = new DatagramPacket(msg, msg.length);

			this.sck.receive(in);
			
			return new String(in.getData());
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String processMsg(Message msg) {
		
		/*
		 * Procesa el mensaje recibido como parámetro.
		 * Devuelve el string que se pretende enviar al servidor.
		 * Si no se quiere enviar un mensaje al servidor devuelve null.
		 */
		
		try {
			if(msg.type.equals("INIT_MESSAGE"))
				return processInitMsg((InitMessage) msg);
			
			if(msg.turn == 0)
				return null;
			
			if(msg.type.equals("SEE_MESSAGE")) 
				return processSeeMsg((SeeMessage) msg);

			if(msg.type.equals("HEAR_MESSAGE"))
				return processHearMsg((HearMessage) msg);

			if(msg.type.equals("SENSE_BODY_MESSAGE"))
				return processSenseBodyMsg((SenseBodyMessage) msg);
			
			return null;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(msg);
			return null;
		}
	}

	private String processSeeMsg(SeeMessage msg) {
		
		/*
		 * Procesa el mensaje recibido con la informacion de lo que ve el jugador.
		 * Si el jugador no ve la pelota -> Gira 60 grados
		 * Si el jugador ve la pelota ----> Avanza hacia ella
		 * Si la pelota esta cerca -------> Dispara hacia delante
		 */
					
		if(msg.ball.equals("NOT_FOUND"))
			return "(turn 60)";
		
		String[] words = msg.ball.split(" ");
		
		Float    dist  = Float.parseFloat(words[1]);
		Integer  angle = Integer.parseInt(words[2].replace(")", ""));
		
		if(dist < 1) 
			return "(kick 40 0)";
			
		if(Math.abs(angle) > 20)
			return "(turn " + angle + ") (dash 65)";
	
		return "(dash 65)";	
	}

	private String processInitMsg(InitMessage msg) {

		this.num  = msg.num;
		this.side = msg.side;

		switch(this.num) {
			case 1:  return "(move -40   0)";
			case 2:  return "(move -30  24)";
			case 3:  return "(move -30   8)";
			case 4:  return "(move -30  -8)";
			case 5:  return "(move -30 -24)";
			case 6:  return "(move -20  24)";
			case 7:  return "(move -20   8)";
			case 8:  return "(move -20  -8)";
			case 9:  return "(move -20 -24)";
			case 10: return "(move -10   8)";
			case 11: return "(move -10  -8)";
			default: return "(move   0   0)";
		}
	}
	
	private String processHearMsg(HearMessage msg) {
		
		if(msg.sender.equals("referee"))
			this.play_mode = msg.message;
		
		return null;
	}
	
	private String processSenseBodyMsg(SenseBodyMessage msg) {
		return null;
	}
	
	private String kick(String[] goals, String[] lines) {
		// TEST FUNCTION
		
		String goal_l = null;
		String goal_r = null;
		String line_l = null;
		String line_r = null;

		for(int i = 0; i < goals.length; i++) {
			
			if(goals[i].startsWith("goal l"))
				goal_l = goals[i];
			if(goals[i].startsWith("goal r"))
				goal_r = goals[i];
		}

		for(int i = 0; i < lines.length; i++) {
						
			if(lines[i].startsWith("line l"))
				line_l = lines[i];
			if(lines[i].startsWith("line r"))
				line_r = lines[i];
		}
		
		if(this.side == 'r') {
			if(goal_l != null) {
				String[] words = goal_l.split(" ");				
				Integer  angle = Integer.parseInt(words[3].replace(")", ""));
				return "(kick 30 " + angle + ")";
			}
			
			if(goal_r != null) {
				String[] words = goal_r.split(" ");
				Integer  angle = Integer.parseInt(words[3].replace(")", ""));
				return "(kick 30 " + ((angle > 0) ? (180 - angle) : (180 + angle)) + ")";
			}
			
			if(line_l != null) {
				String[] words = line_l.split(" ");
				Integer  angle = Integer.parseInt(words[3].replace(")", ""));
				return "(kick 30 " + angle + ")";
			}
			
			if(line_r != null) {
				String[] words = line_r.split(" ");
				Integer  angle = Integer.parseInt(words[3].replace(")", ""));
				return "(kick 30 " + ((angle > 0) ? (180 - angle) : (180 + angle)) + ")";
			}
		}
		
		if(this.side == 'l') {
			if(goal_r != null) {
				String[] words = goal_r.split(" ");
				Integer  angle = Integer.parseInt(words[3].replace(")", ""));
				return "(kick 30 " + angle + ")";
			}
			
			if(goal_l != null) {
				String[] words = goal_l.split(" ");
				Integer  angle = Integer.parseInt(words[3].replace(")", ""));
				return "(kick 30 " + ((angle > 0) ? (180 - angle) : (180 + angle)) + ")";
			}
			
			if(line_l != null) {
				String[] words = line_l.split(" ");
				Integer  angle = Integer.parseInt(words[3].replace(")", ""));
				return "(kick 30 " + ((angle > 0) ? (180 - angle) : (180 + angle)) + ")";
			}
			
			if(line_r != null) {
				String[] words = line_r.split(" ");
				Integer  angle = Integer.parseInt(words[3].replace(")", ""));				
				return "(kick 30" + angle + ")";
			}
		}
				
		return "(kick 30 0)";
	}

}
