package ia;

import java.io.Serializable;

public class Layer implements Serializable {

	public Node[]   nodes;
	public double[] outputs;
	
	public Layer(int nodes, int inputs) {
		
		this.nodes   = new Node[nodes];
		this.outputs = new double[nodes];
		
		for(int i = 0; i < this.nodes.length; i++) {
			this.nodes[i]   = new Node(inputs);
			this.outputs[i] = -1;
		}
	}

	
	public double[] propagate(double[] inputs) {
		
		for(int i = 0; i < this.nodes.length; i++)
			this.outputs[i] = this.nodes[i].propagate(inputs);
		
		return this.outputs;
	}
	
	public double[] fixWeights(double[] inputs) {
		
		for(int i = 0; i < this.nodes.length; i++) 
			this.nodes[i].fixWeight(inputs);
		
		return this.outputs;
	}

	public void printWeights() {
		
		for(int i = 0; i < this.nodes.length; i++) {
			System.out.print("n" + i + " -> ");
			this.nodes[i].printWeights();
		}
	}
	
	public void calculateDeltas(Node[] next) {
		
		for(int i = 0; i < this.nodes.length; i++) {
			
			double error = 0;
			
			for(int j = 0; j < next.length; j++) 
				error += next[j].weights[i] * next[j].delta;
			
			this.nodes[i].calculateDelta(error);
		}
		
	}
}
