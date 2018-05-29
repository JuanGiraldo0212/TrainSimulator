package estructuras;

import java.util.ArrayList;

import mundo.Vertex;

public class NodoDijkstra {
	
	private int peso;
	private ArrayList<Integer> camino;
	
	public NodoDijkstra(int peso){
		this.setPeso(peso);
		camino=(new ArrayList<>());
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public ArrayList<Integer> getCamino() {
		return camino;
	}

	public void setCamino(ArrayList<Integer> actual ) {
		camino=actual;
	}
	
	

}
