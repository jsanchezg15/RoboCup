package ia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

public class Network implements Serializable {

	public Vector<Layer> layers;
	public int inputSize;
	
	public Network(int inputs) {
		this.layers    = new Vector<Layer>();
		this.inputSize = inputs;
	}
	
	public static Network createNetworkUsingFile(String file_name) {
	
		Network net = null;
    
		try {
    		FileInputStream   file   = new FileInputStream(file_name);
    		ObjectInputStream obj_in = new ObjectInputStream(file);
            
            net = (Network) obj_in.readObject();
            
            obj_in.close();
        } 
    	catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
		
		return net;
	}
	
	public void storeNetwork(String filename) {
    	try {
    		FileOutputStream   file    = new FileOutputStream(filename);
        	ObjectOutputStream obj_out = new ObjectOutputStream(file);
        	
        	obj_out.writeObject(this);
        	obj_out.close();            
        } 
    	catch (IOException ex) {
            System.out.println(ex);
        }
	}
	
	public void addLayer(int nodes) {
		
		int layerInputs = 0;
		
		if(this.layers.size() == 0) 
			layerInputs = this.inputSize;
		else
			layerInputs = this.layers.get(this.layers.size() - 1).outputs.length;
		
		this.layers.add(new Layer(nodes, layerInputs));
	}
	
	public double[] propagate(double[] inputs) {

		for(Layer l : this.layers)
			inputs = l.propagate(inputs);
		
		return inputs;
	}
	
	public void calculateDeltas(double[] Y) {
				
		Node[] outputNodes = this.layers.get(this.layers.size() - 1).nodes;
		
		for(int i = 0; i < Y.length; i++) 
			outputNodes[i].delta = outputNodes[i].output * (1 - outputNodes[i].output) * (Y[i] - outputNodes[i].output);

			
		for(int i = this.layers.size() - 2; i >= 0; i--) {
			
			Layer layer     = this.layers.get(i);
			Layer nextLayer = this.layers.get(i + 1);
			
			for(int j = 0; j < layer.nodes.length; j++) {
				
				Node   node = layer.nodes[j];
				double sum  = 0;
				
				for(int k = 0; k < nextLayer.nodes.length; k++) {
					
					double delta  = nextLayer.nodes[k].delta;
					double weight = nextLayer.nodes[k].weights[j];
					
					sum += delta * weight;
				}
				
				node.delta = node.output * (1 - node.output) * sum;
			}
		}
	}
	
	public void fixWeights(double[] inputs) {
		
		for(Layer l : this.layers)
			inputs = l.fixWeights(inputs);
	}
	
	public void backpropagation(double[] inputs, double[] outputs) {
		this.calculateDeltas(outputs);
		this.fixWeights(inputs);
	}
	
	public void printWeights() {
		int i = 0;
		
		for(Layer l : this.layers) {
			System.out.println("l" + i++);
			l.printWeights();
			System.out.println();
		}
	}
	
}
