package mundo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

import estructuras.DisjointNode;
import estructuras.DisjointSets;
import estructuras.NodoDijkstra;
import Excepciones.QueueException;
import auxiliares.CompararAristasPorPeso;
import estructuras.Queue;

public class Juego {
	
	public static final String TIPO_MATRIZ="Matriz";
	public static final String TIPO_LISTA="Lista";
	private String tipo;
	private ArrayList<Vertex> vertices;
	private Tren tren;
	private int[][] grafoMatrizAd;
	private Hashtable<String, ArrayList<int[]>> grafoListaAd;
	private int time;
	private boolean jugando;
	private ArrayList<Vertex> camino;
	private String usuario;
	
	public Juego(){
		setUsuario("");
		jugando=true;
		grafoMatrizAd=null;
		grafoListaAd = null;
		setCamino(new ArrayList<>());
	}
	
	public int[][] convertirLista(Hashtable<String, ArrayList<int[]>> grafoListaAd)
	{
		int[][] grafoMatrizAd = new int[vertices.size()][vertices.size()];
		for (int i = 0; i < vertices.size(); i++) 
		{
			ArrayList<int[]> adyacentes = grafoListaAd.get(vertices.get(i).getCodigo());
			for (int j = 0; j < adyacentes.size(); j++)
			{
				int[] datosRelacion = adyacentes.get(j);
				grafoMatrizAd[i][datosRelacion[0]] = datosRelacion[1];
			}
		}
		return grafoMatrizAd;
	}
	
	public void cargarGrafoListaAd(File file)
	{
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(file));
			int cantidadVertices = Integer.parseInt(br.readLine());
			grafoListaAd = new Hashtable<>(cantidadVertices);
			vertices = new ArrayList<Vertex>();
			
			for(int i = 0; i < cantidadVertices; i++)
			{
				String [] datos=br.readLine().split(" ");
				vertices.add(i, new Vertex(datos[0], datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), Integer.parseInt(datos[4]))); 
				grafoListaAd.put(vertices.get(i).getCodigo(), new ArrayList<>());
			}
			for(int i = 0; i < cantidadVertices; i++)
			{
				String[] relaciones=br.readLine().split(" ");
				for(int j = 0; j < relaciones.length; j++)
				{
					String [] info = relaciones[j].split(",");
					int[] datosArista = {Integer.parseInt(info[0]), Integer.parseInt(info[1])};
					grafoListaAd.get(vertices.get(i).getCodigo()).add(datosArista);
				}
			}
			br.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		iniciarTren();
		
		
	}
	
	public void iniciarTren(){
		Random ran=new Random();
		int i=ran.nextInt(vertices.size());
		Vertex actual=vertices.get(i);
		tren=new Tren(actual.getPosX()+10, actual.getPosY()+10, Tren.DIR_ABAJO);
		tren.setSalida(actual);
		tren.setActual(actual);
		tren.setInicio(actual);
		NodoDijkstra[][] FW; 
		if(tipo.equals(TIPO_MATRIZ)){
			
			FW=floydWarshall(grafoMatrizAd);
		}
		else{
			
			FW=floydWarshall(corregirMatriz(convertirLista(grafoListaAd)));
		}
		ArrayList<Integer> verticesLejos = new ArrayList<>();
		for(int k = 0; k < FW.length; k++) {
			if(FW[Integer.parseInt(tren.getSalida().getCodigo())][k].getCamino().size() >= 3 && vertices.get(k).getTipo().equals(Vertex.VERTEX_ESTACION)) {
				verticesLejos.add(k);
			}
		}
		int j=ran.nextInt(verticesLejos.size());
	
		Vertex temp=vertices.get(verticesLejos.get(j));
		temp.setImagen("./data/Escenario/Meta.png");
		tren.setMeta(temp);
		jugando = true;
	}
	public void cargarGrafoMatrizAd(File arch){
		try {
			BufferedReader br = new BufferedReader(new FileReader(arch));
			int numGraf=Integer.parseInt(br.readLine());
			grafoMatrizAd = new int[numGraf][numGraf];
			vertices= new ArrayList<>();
			for(int i=0;i<numGraf;i++){
				String [] datos=br.readLine().split(" ");
				vertices.add(i, new Vertex(datos[0], datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), Integer.parseInt(datos[4]))); 
			}
			for(int i=0;i<numGraf;i++){
				String[] relaciones=br.readLine().split(" ");
				for(int j=0;j<relaciones.length;j++){
					String [] info = relaciones[j].split(",");
					grafoMatrizAd[i][Integer.parseInt(info[0])]=Integer.parseInt(info[1]);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		grafoMatrizAd=corregirMatriz(grafoMatrizAd);
		iniciarTren();
		
		
	}
	
	public int[][] corregirMatriz(int [][] mat){
		int[][] matrix=mat.clone();
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix.length;j++){
				if(i!=j && matrix[i][j]==0){
					matrix[i][j]=99999;
				}
			}
		}
		return matrix;
	}
	
	public  String kruskal(int[][] grafoMatrizAd)
	{
		String mensaje = "";
		DisjointSets<Vertex> conjuntos = new DisjointSets<>();
		ArrayList<int[]> aristas = new ArrayList<>(); 
		for (int i = 0; i < vertices.size(); i++) 
		{
			conjuntos.makeSet(vertices.get(i));
		}
		for (int i = 0; i < grafoMatrizAd[0].length; i++) 
		{
			for (int j = 0; j < grafoMatrizAd.length; j++)
			{
				if(grafoMatrizAd[i][j] != 0)
				{
					int[] datos = {i, j, grafoMatrizAd[i][j]};
					aristas.add(datos);
				}
			}
		}
		aristas.sort(new CompararAristasPorPeso());
		ArrayList<int[]> aristasMTS = new ArrayList<>(); 
		for(int k = 0; k < aristas.size()&& aristasMTS.size() != vertices.size(); k++) 
		{
			int[] datosArista = aristas.get(k);
			LinkedList<DisjointNode<Vertex>> set1 = conjuntos.findSet(vertices.get(datosArista[0]));
			LinkedList<DisjointNode<Vertex>> set2 = conjuntos.findSet(vertices.get(datosArista[1]));
			if(!(set1.getFirst().getValue().equals(set2.getFirst().getValue())))
			{
				aristasMTS.add(datosArista);
				conjuntos.union(vertices.get(datosArista[0]), vertices.get(datosArista[1]));
			}
		}
		for (int i = 0; i < aristasMTS.size(); i++) 
		{
			int[] datosArista = aristasMTS.get(i);
			mensaje += vertices.get(datosArista[0]).getCodigo()+"-"+ vertices.get(datosArista[1]).getCodigo() + "," + datosArista[2] + " ";
		}
		return mensaje;
	}
	
	public String BFS(int[][] grafoMatrizAd) throws QueueException {
		String tree = "";
		//Ciclo para volver todos los vertices blancos.
		for(int i = 0; i < vertices.size(); i++) {
			//actualiza a blanco
			vertices.get(i).setColor(Vertex.BLANCO);
			//asigna distancia infinita del vertice
			vertices.get(i).setDistancia(999999999);
		}
		//Vertice de inicio del BFS
		Vertex temp = vertices.get(0);
		//Asigna color gris al vertice de inicio
		temp.setColor(Vertex.GRIS);
		//asigna distancia 0 al vertice
		temp.setDistancia(0);
		//Estructura queue de apoyo para el algoritmo
		Queue<Vertex> Q = new Queue<>();
		//Se encola el vertice de inicio
		Q.enqueue(temp);
		

		while(!Q.isEmpty()){
		//Se desencola el vertice primero en la cola
		Vertex u = Q.dequeue();
		//Ciclo para recorrer los adyacentes de u. Se usa la matriz de adyacencia, de manera que la posicio X de la matriz sea constante (el codigo de u)
		//y la posicion Y de la matriz sea un j que recorre todos los vertices
		for(int j = 0; j < vertices.size(); j++) {
			//Verifica que el vertice sea blanco y que sea adyacente
			if(grafoMatrizAd[Integer.parseInt(u.getCodigo())][j] != 0 && vertices.get(j).getColor()==Vertex.BLANCO) {
				//asigna color gris al vertice adyacente
				vertices.get(j).setColor(Vertex.GRIS);
				//Asigna la nueva distancia
				vertices.get(j).setDistancia(u.getDistancia()+1);
				//Se agrega el par (X,Y), donde X es padre y Y es hijo, a la salida "tree"
				tree+="("+u.getCodigo()+","+j+") ";
				//Se encola el vertice adyacente a u con el objetivo de luego revisar sus adyacentes.
				Q.enqueue(vertices.get(j));
				
			}
		}
		//se asigna color negro a u, pues ya se revisaron todos sus adyacentes.
		u.setColor(Vertex.NEGRO);
			
		}
		
		return tree;
	}
	
	public String DFS(int[][] grafoMatrizAd) {
		String tree = "";
		//Todos los vertices se vuelven blancos
		for(int i = 0; i < vertices.size(); i++) {
			vertices.get(i).setColor(Vertex.BLANCO);
		}
		//El tiempo que sera la distancia de cada vertice posteriormente, inicia en 0
		time = 0;
		//Se recorren todos los vertices blancos. Es decir, no visitados.
		for(int i = 0; i < vertices.size(); i++) {
			if(vertices.get(i).getColor()==Vertex.BLANCO) {
				//recursion para cada arbol del bosque de arboles de expansion minima
				tree+=DFS_Visit(vertices.get(i),grafoMatrizAd);
				//Cada arbol del bosque se separa por "/ ".
				tree+="/ ";
			}
		}
		
		return tree;
	}
	
	public String DFS_Visit(Vertex u, int[][] grafoMatrizAd){
		
		String tree = "";
		time+=1;
		//Se asigna la distancia al vertice
		u.setDistancia(time);
		//Se da por descubierto el vertice, por lo que se vuelve gris.
		u.setColor(Vertex.GRIS);
		//Ciclo para recorrer vertices adyacentes.
		for(int j = 0; j < vertices.size(); j++) {
			//Se chequea adyacencia y que no se halla descubierto
			if(grafoMatrizAd[Integer.parseInt(u.getCodigo())][j] != 0 && vertices.get(j).getColor()==Vertex.BLANCO) {
				//Se agrega el par (X,Y), donde X es padre y Y es hijo, a uno de los arboles. Tenga en cuenta que la salida del algoritmo entero puede ser un bosque
				tree+="("+u.getCodigo()+","+j+") ";
				//Recursion para revisar el adyacente del adyacente hasta descubrir todos. 
				tree+=DFS_Visit(vertices.get(j),grafoMatrizAd);
			}
		}
		//Se descubren todos sus adyacentes, se vuelve negro.
		u.setColor(Vertex.NEGRO);
		//se aumenta el tiempo.
		time+=1;
		
		return tree;
	}
	
	public String primMST(int grafoMatrizAd[][])
    {
        // Arreglo que guarda el arbol minimo de expansion
        int parent[] = new int[vertices.size()];
 
        // Valores de los cuales se escoger la arista minima
        int key[] = new int [vertices.size()];
 
        // Representa si el vertices esta o no en el arbol
        Boolean mstSet[] = new Boolean[vertices.size()];
 
        for (int i = 0; i < vertices.size(); i++)
        {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }
        // Vertice 0 se escoge como el primer vertice del arbol
        key[0] = 0;
        parent[0] = -1; // El primer elemento es la raiz del arbol
 
        for (int count = 0; count < vertices.size()-1; count++)
        {
            // Escoge el vertice de menor indice que no se encuentra en el arbol
            int u = minKey(key, mstSet);
 
            mstSet[u] = true;
 
            // Actualiza el peso, si es menor, de la interseccion entre el minimo y sus adyacentes que no estan en el arbol
            for (int v = 0; v < vertices.size(); v++)
            {
                if (grafoMatrizAd[u][v]!=0 && mstSet[v] == false &&
                    grafoMatrizAd[u][v] <  key[v])
                {
                    parent[v]  = u;
                    key[v] = grafoMatrizAd[u][v];
                }
            }
        }
 
        String prim = "";
        for (int i = 1; i < vertices.size(); i++)
            prim += parent[i]+","+ i+","+
                               grafoMatrizAd[i][parent[i]] + " ";
        return prim;
    }
	
	public int minKey(int key[], Boolean mstSet[])
    {
        int min = Integer.MAX_VALUE, min_index=-1;
 
        for (int v = 0; v < vertices.size(); v++)
            if (mstSet[v] == false && key[v] < min)
            {
                min = key[v];
                min_index = v;
            }
 
        return min_index;
    }
	//Retorna en un string las aristas pertenecientes al arbol de expansion minima de prim, no ordenadamente
	public String aristasPrimKruskal(String aristaYpeso) {
    	String[] arrDatos = aristaYpeso.split(" ");
    	String retorno ="";
    	for (int i = 0; i < arrDatos.length; i++) {
    		String[] aristas = arrDatos[i].split(",");
    		retorno+=aristas[0]+"\n";	
		}
    	return retorno;
    }
    //Retorna la suma de los pesos de las aristas pertenecientes al arbol de expansion minima de prim
    public int pesosPrimKruskal(String aristaYpeso) {
    	String[] arrDatos = aristaYpeso.split(" ");
    	int retorno =0;
    	for (int i = 0; i < arrDatos.length; i++) {
    		String[] aristas = arrDatos[i].split(",");
    		retorno+=Integer.parseInt(aristas[1]);
		}
    	return retorno;
    }

	public int[] aristaMenorPeso(int[][] grafoMatrizAd)
	{
		int menor = Integer.MIN_VALUE;
		int fila = 0;
		int columna = 0;
		for (int i = 0; i < grafoMatrizAd[0].length; i++)
		{
			for (int j = 0; j < grafoMatrizAd.length; j++) 
			{
				if(menor > grafoMatrizAd[i][j])
				{
					menor = grafoMatrizAd[i][j];
					fila = i;
					columna = j;
					grafoMatrizAd[i][j] = 0;
				}
			}
		}
		int datos[] = {fila, columna};
		return datos;
	}
	
	/**
	 * 
	 * @param adyacencia es la matriz donde van a estar los pesos 
	 * @param inicio es el indice del vertice de inicio
	 * @param llegada es el indice del vertice de llegada
	 * @return un entero que representa el peso minimo desde inicio a llegada
	 */
	public NodoDijkstra dijkstra(int [][] adyacencia,int inicio,int llegada){
        NodoDijkstra[] pesos=new NodoDijkstra[adyacencia.length];
        ArrayList<Integer> S = new ArrayList<Integer>();
        for(int i=0;i<adyacencia.length;i++){
            pesos[i]=new NodoDijkstra(99999);
        }
        pesos[inicio].setPeso(0);;
        while(!S.contains(llegada)){
            int u=minimoEnArreglo(pesos,S);
            S.add(u);
            pesos[u].getCamino().add(u);
            int[] vertices=verticesNoEnS(pesos, S);
            for(int i=0;i<vertices.length;i++){
                if(pesos[u].getPeso()+adyacencia[u][vertices[i]]<pesos[vertices[i]].getPeso()){
                    pesos[vertices[i]].setPeso(pesos[u].getPeso()+adyacencia[u][vertices[i]]);
                    pesos[vertices[i]].setCamino((ArrayList<Integer>)pesos[u].getCamino().clone());
                }
            }
        }
      
        return pesos[llegada];
    }

	/**
	 * 
	 * @param arr el arreglo de pesos
	 * @param val la lista de vertices
	 * @return el indice del vertice con peso minimo que no esta en la lista pasada por parametro
	 */
    public int minimoEnArreglo(NodoDijkstra[] arr,ArrayList<Integer> val){
        int index=0;
        int min=99999;
        NodoDijkstra[] aux=arr.clone();
        for(int i=0;i<val.size();i++){
            aux[val.get(i)].setPeso(99999);;
        }
        for(int i=0;i<aux.length;i++){
            if(aux[i].getPeso()<min){
                min=aux[i].getPeso();
                index=i;
            }
        }
        return index;
    }
    
    /**
     * 
     * @param arr el arreglo de los pesos 
     * @param val la lista de vertices
     * @return un arreglo de indices que no estan en la lista 
     */
    public int[] verticesNoEnS(NodoDijkstra[] arr,ArrayList<Integer> val){
        NodoDijkstra[] temp= new NodoDijkstra[arr.length];
        for(int i=0;i<temp.length;i++){
        	temp[i]=new NodoDijkstra(arr[i].getPeso());
        }
        int[] vertices = new int[(temp.length)-val.size()];
        int index=0;
        for(int i=0;i<val.size();i++){
            temp[val.get(i)].setPeso(-1);
        }
        for(int i=0;i<temp.length;i++){
            if(temp[i].getPeso()!=-1){
                vertices[index]=i;
                index++;
            }
        }
        return vertices;
    }
	
    /**
     * 
     * @param ad la lista de adyacencia con los pesos de las aristas como interceccion entre filas y columnas
     * @return una matriz con los pesos de los caminos minimos
     */
	public NodoDijkstra[][] floydWarshall(int[][] ad){
		NodoDijkstra [][] caminos= new NodoDijkstra[ad.length][ad.length];
		for(int i=0;i<caminos.length;i++){
			for(int j=0;j<caminos.length;j++){
				caminos[i][j]=new NodoDijkstra(ad[i][j]);
				if(ad[i][j]!=99999 && ad[i][j]!=0){
					ArrayList<Integer> actual=new ArrayList<>();
					actual.add(i);
					actual.add(j);
					caminos[i][j].setCamino(actual);
				}
			}
		}
		for(int k=0;k<ad.length;k++){
			for(int i=0;i<ad.length;i++){
				for(int j=0;j<ad.length;j++){
					if(caminos[i][j].getPeso()>caminos[i][k].getPeso()+caminos[k][j].getPeso()){
						caminos[i][j].setPeso(caminos[i][k].getPeso()+caminos[k][j].getPeso());
						caminos[i][j].setCamino(unirListas(caminos[i][k].getCamino(), caminos[k][j].getCamino(), i));;
						
					}
				}
			}
		}
		return caminos;
	}
	
	public ArrayList<Integer> unirListas(ArrayList<Integer> first,ArrayList<Integer> second,int val){
		ArrayList<Integer> actual = new ArrayList<>();
		actual.add(val);
		for(int i=0;i<first.size();i++){
			actual.add(first.get(i));
		}
		for(int i=0;i<second.size();i++){
			actual.add(second.get(i));
		}
		ArrayList<Integer> retorno = limpiarLista(actual);
		return retorno;
		
	}
	
	public ArrayList<Integer> limpiarLista(ArrayList<Integer> act){
		ArrayList<Integer> actual = act;
		ArrayList<Integer> limpia = new ArrayList<>();
		int num = 0;
		for(int i = 0; i < actual.size(); i++) {
			if(act.get(i)!=num) {
				num = act.get(i);
				limpia.add(act.get(i));
			}
		}
		return limpia;
	}
	public int distanciaRecorrida(int[][] mat){
		int dist=0;
		for(int i=0;i<camino.size()-1;i++){
			dist+=mat[Integer.parseInt(camino.get(i).getCodigo())][Integer.parseInt(camino.get(i+1).getCodigo())];
		}
		return dist;
	}
	
	public void agregarVerticeListaAd(Vertex vertice, ArrayList<int[]> adyacencias)
	{
		vertices.add(vertice);
		grafoListaAd.put(vertice.getCodigo(), adyacencias);
	}
	public void eliminarVerticeListaAd(String codigo) 
	{
		Vertex encontrado = buscarVertice(codigo);
		vertices.remove(encontrado);
		grafoListaAd.remove(encontrado.getCodigo());
	}
	public Vertex buscarVertice(String codigo)
	{
		Vertex encontrado = null;
		for (Vertex vertex : vertices) 
		{
			if(vertex.getCodigo().equals(codigo))
			{
				encontrado = vertex;
			}
		}
		return encontrado;
	}
	
	public void agregarVerticeMatAd(Vertex vertice, String adyacencia){
		vertices.add(vertice);
		String[] adya=adyacencia.split(" ");
		int[][] nuevaMat=new int[grafoMatrizAd.length+1][grafoMatrizAd.length+1];
		for(int i=0;i<grafoMatrizAd.length;i++){
			for(int j=0;j<grafoMatrizAd.length;j++){
				nuevaMat[i][j]=grafoMatrizAd[i][j];
			}
		}
		for(int i=0;i<adya.length;i++){
			String[] actual=adya[i].split(",");
			nuevaMat[grafoMatrizAd.length][Integer.parseInt(actual[0])]=Integer.parseInt(actual[1]);
		}
		grafoMatrizAd=nuevaMat;
	}
	
	public void eliminarVerticeMatAd(String codigo){
		int indice=Integer.parseInt(codigo);
		for(int i=0;i<grafoMatrizAd.length;i++){
			grafoMatrizAd[indice][i]=0;
		}
		int[][] nuevaMat=new int [grafoMatrizAd.length-1][grafoMatrizAd.length-1];
		int in_Mat=0;
		for(int i=0;i<nuevaMat.length;i++){
			if(i!=indice){
				for(int j=0;j<nuevaMat.length;j++){
					nuevaMat[in_Mat][j]=grafoMatrizAd[i][j];
				}
				in_Mat++;
		}
		}
		grafoMatrizAd=nuevaMat;
	}
	
	public int[][] getGrafoMatrizAd() {
		return grafoMatrizAd;
	}

	public void setGrafoMatrizAd(int[][] grafoMatrizAd) {
		this.grafoMatrizAd = grafoMatrizAd;
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}
	
	public Tren getTren(){
		return tren;
	}
	public Hashtable<String, ArrayList<int[]>> getGrafoListaAd() {
		return grafoListaAd;
	}
	public void setGrafoListaAd(Hashtable<String, ArrayList<int[]>> grafoListaAd) {
		this.grafoListaAd = grafoListaAd;
	}

	public boolean isJugando() {
		return jugando;
	}
	public void setJugando(boolean jugando) {
		this.jugando = jugando;
	}

	public ArrayList<Vertex> getCamino() {
		return camino;
	}

	public void setCamino(ArrayList<Vertex> camino) {
		this.camino = camino;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
