package ia;

import java.util.Scanner;

public class Program {

	public static void main(String[] args) {

		// Red neuronal
		// 3 inputs (dos entradas + un selector)
		// El selector elige la operacion logica que se aplicará a las entradas
		// Selector =  0 => OR
		// Selector =  1 => AND
		// Selector = -1 => XOR
		
		
		Network net = new Network(3);
		
		net.addLayer(4);  // L1 - 4  nodos
		net.addLayer(12); // L2 - 12 nodos
		net.addLayer(1);  // L3 - 1  nodo  (output)

		
		double[][] inputs  = {	{0,  0,  0}, {0,  1,  0}, {1,  0,  0}, {1,  1,  0},   // OR
								{0,  0,  1}, {0,  1,  1}, {1,  0,  1}, {1,  1,  1},   // AND
								{0,  0, -1}, {0,  1, -1}, {1,  0, -1}, {1,  1, -1}};  // XOR
		
		double[][] outputs = {      {0},         {1},         {1},         {1},       // OR
								    {0},         {0},         {0},         {1},       // AND
								    {0},         {1},         {1},         {0}};      // XOR
		
		
		// Visualización de resultados
		
		for(int i = 0; i < inputs.length; i++) {
			
			System.out.print("Inputs:   ");
			
			for(int j = 0; j < inputs[i].length; j++) 
				System.out.print(inputs[i][j] + "   ");
			
			System.out.print("Output:   ");
			
			double[] results = net.propagate(inputs[i]);
			System.out.println((results[0] > 0.5) ? "1" : "0");
			
			if(i % 4 == 3)
				System.out.println();
		}
		
		
		
		// Entrenamiento
		
		System.out.println("Pulse enter para comenzar entrenamiento");
		Scanner sc = new Scanner(System.in);
		sc.nextLine();
		System.out.println("Entrenando...");
		
		int epoch = 0;
		int total = 1_000_000;
		
		while(epoch++ < total) {
			for(int i = 0; i < inputs.length; i++) {
				net.propagate(inputs[i]);
				net.backpropagation(inputs[i], outputs[i]);
			}
		}
		
		
		
		// Visualización de resultados
		
		for(int i = 0; i < inputs.length; i++) {
			
			System.out.print("Inputs:   ");
			
			for(int j = 0; j < inputs[i].length; j++) 
				System.out.print(inputs[i][j] + "   ");
			
			System.out.print("Output:   ");
			
			double[] results = net.propagate(inputs[i]);
			System.out.println((results[0] > 0.5) ? "1" : "0");
			
			if(i % 4 == 3)
				System.out.println();
		}
		
		
		System.out.println("Escribe el nombre del arcivo en el que quieres guardar la red neuronal: ");
		
		String fileName = sc.nextLine();
		
		net.storeNetwork(fileName);
	}
}
