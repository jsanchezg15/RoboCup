package ia;

import java.io.Serializable;

public class Node implements Serializable {

	public double[] weights;
	public double output;
	public double bios;
	public double delta;
	
	public Node(int inputs) {
		this.weights = new double[inputs];
		this.output  = -1;
		this.bios    = 0;
		this.delta   = 0;
		
		this.randomizeWeights();
	}
	
	public void randomizeWeights() {
		for(int i = 0; i < this.weights.length; i++) 
			this.weights[i] = Math.random();
	}
	
	
	public double propagate(double[] inputs) {
		
		double sum = this.bios;
		
		for(int i = 0; i < inputs.length; i++) 
			sum += inputs[i] * this.weights[i];
		
		this.output = Functions.sigmoid(sum);
		
		return this.output;
	}
	
	public void fixWeight(double[] inputs) {
		
		for(int i = 0; i < inputs.length; i++) 
			this.weights[i] = this.weights[i] + this.delta * inputs[i];
	}

	public void printWeights() {
		System.out.print("[");

		for(int i = 0; i < this.weights.length; i++) 
			System.out.print(this.weights[i] + (  (i < this.weights.length - 1) ? ", \t" : ""  )  );
		
		System.out.println("]");
	}
	
	public double calculateDelta(double error) {
		this.delta = this.output * (1 - this.output) * error;
		return this.delta;
	}
}
