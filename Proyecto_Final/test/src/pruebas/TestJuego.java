package pruebas;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.Hashtable;

import org.junit.Test;

import Excepciones.QueueException;
import junit.framework.TestCase;
import mundo.*;

public class TestJuego extends TestCase{

	
	private Juego juego;
	
	public void setUpEscenario2()
	{
		juego = new Juego();
		ArrayList<Vertex> Vlist = new ArrayList<Vertex>();
		Vlist.add(new Vertex("0","Estacion",1,20,20));
		Vlist.add(new Vertex("1","Estacion",1,400,60));
		Vlist.add(new Vertex("2","Estacion",6,220,230));
		Vlist.add(new Vertex("3","Estacion",1,50,420));
		Vlist.add(new Vertex("4","Estacion",3,420,420));
		Vlist.add(new Vertex("5","Estacion",2,240,610));
		Vlist.add(new Vertex("6","Estacion",4,600,230));
		
		Hashtable<String, ArrayList<int[]>> grafoListaAd = new Hashtable<>(Vlist.size());
		
		ArrayList<int[]> datosVertice0 = new ArrayList<>();
		int[] adyacencia0Vertice0 = {2,5};
		datosVertice0.add(adyacencia0Vertice0);
		
		ArrayList<int[]> datosVertice1 = new ArrayList<>();
		int[] adyacencia0Vertice1 = {2,5};
		int[] adyacencia1Vertice1 = {6,5};
		datosVertice1.add(adyacencia0Vertice1);
		datosVertice1.add(adyacencia1Vertice1);
		
		ArrayList<int[]> datosVertice2 = new ArrayList<>();
		int[] adyacencia0Vertice2 = {0,5};
		int[] adyacencia1Vertice2 = {1,5};
		int[] adyacencia2Vertice2 = {3,5};
		int[] adyacencia3Vertice2 = {4,5};
		datosVertice2.add(adyacencia0Vertice2);
		datosVertice2.add(adyacencia1Vertice2);
		datosVertice2.add(adyacencia2Vertice2);
		datosVertice2.add(adyacencia3Vertice2);

		ArrayList<int[]> datosVertice3 = new ArrayList<>();
		int[] adyacencia0Vertice3 = {2,5};
		int[] adyacencia1Vertice3 = {5,5};
		datosVertice3.add(adyacencia0Vertice3);
		datosVertice3.add(adyacencia1Vertice3);
		
		ArrayList<int[]> datosVertice4 = new ArrayList<>();
		int[] adyacencia0Vertice4 = {2,5};
		int[] adyacencia1Vertice4 = {5,5};
		int[] adyacencia2Vertice4 = {6,5};
		datosVertice4.add(adyacencia0Vertice4);
		datosVertice4.add(adyacencia1Vertice4);
		datosVertice4.add(adyacencia2Vertice4);
		
		ArrayList<int[]> datosVertice5 = new ArrayList<>();
		int[] adyacencia0Vertice5 = {3,5};
		int[] adyacencia1Vertice5 = {4,5};
		datosVertice5.add(adyacencia0Vertice5);
		datosVertice5.add(adyacencia1Vertice5);
		
		ArrayList<int[]> datosVertice6 = new ArrayList<>();
		int[] adyacencia0Vertice6 = {1,5};
		int[] adyacencia1Vertice6 = {4,5};
		datosVertice6.add(adyacencia0Vertice6);
		datosVertice6.add(adyacencia1Vertice6);
		
		grafoListaAd.put(Vlist.get(0).getCodigo(), datosVertice0);
		grafoListaAd.put(Vlist.get(1).getCodigo(), datosVertice1);
		grafoListaAd.put(Vlist.get(2).getCodigo(), datosVertice2);
		grafoListaAd.put(Vlist.get(3).getCodigo(), datosVertice3);
		grafoListaAd.put(Vlist.get(4).getCodigo(), datosVertice4);
		grafoListaAd.put(Vlist.get(5).getCodigo(), datosVertice5);
		grafoListaAd.put(Vlist.get(6).getCodigo(), datosVertice6);
		
		juego.setGrafoListaAd(grafoListaAd);
		juego.setVertices(Vlist);
	}
	
	public void setUpEscenario1(){
		juego = new Juego();
		ArrayList<Vertex> Vlist = new ArrayList<Vertex>();
		Vlist.add(new Vertex("0","Estacion",5,30,40));
		Vlist.add (new Vertex("1","Estacion",5,60,80));
		Vlist.add (new Vertex("2","Estacion",5,120,160));
		Vlist.add (new Vertex("3","Estacion",5,70,30));
		Vlist.add (new Vertex("4","Estacion",5,20,40));
		
		int[][] grafoMatrizAd = {{0,0,0,1,1},{0,0,0,1,1},{0,0,0,1,0},{1,1,1,0,1},{1,1,0,1,0}};
		
		juego.setGrafoMatrizAd(grafoMatrizAd);
		juego.setVertices(Vlist);
	}
	
	@Test
	public void testBFS() throws QueueException {
		setUpEscenario1();
		assertEquals("(0,3) (0,4) (3,1) (3,2) ",juego.BFS(juego.getGrafoMatrizAd()));
	}
	@Test
	public void testDFS() {
		setUpEscenario1();
		assertEquals("(0,3) (3,1) (1,4) (3,2) / ",juego.DFS(juego.getGrafoMatrizAd()));
	}
	@Test
	public void testKruskal1()
	{
		setUpEscenario1();
		String resultado = juego.kruskal(juego.getGrafoMatrizAd());
		String aristas = juego.aristasPrimKruskal(juego.kruskal(juego.getGrafoMatrizAd()));
		int peso = juego.pesosPrimKruskal(juego.kruskal(juego.getGrafoMatrizAd()));
		
		assertEquals(resultado, "0-3,1 0-4,1 1-3,1 2-3,1 ");
		assertEquals(aristas, "0-3\n" + "0-4\n" + "1-3\n" + "2-3\n");
		assertEquals(peso, 4);
	}
	@Test
	public void testKruskal2()
	{
		setUpEscenario2();
		String resultado = juego.kruskal(juego.convertirLista(juego.getGrafoListaAd()));
		String aristas = juego.aristasPrimKruskal(juego.kruskal(juego.convertirLista(juego.getGrafoListaAd())));
		int peso = juego.pesosPrimKruskal(juego.kruskal(juego.convertirLista(juego.getGrafoListaAd())));
		
		assertEquals(resultado, "0-2,5 1-2,5 1-6,5 2-3,5 2-4,5 3-5,5 ");
		assertEquals(aristas, "0-2\n" + "1-2\n" + "1-6\n" + "2-3\n" + "2-4\n" + "3-5\n");
		assertEquals(peso, 30);
	}
	@Test
	public void testPrim1() 
	{
		setUpEscenario1();
		String resultado = juego.primMST(juego.getGrafoMatrizAd());
		String aristas = juego.aristasPrimKruskal(juego.kruskal(juego.getGrafoMatrizAd()));
		int peso = juego.pesosPrimKruskal(juego.kruskal(juego.getGrafoMatrizAd()));
		
		assertEquals(resultado, "3,1,1 3,2,1 0,3,1 0,4,1 ");
		assertEquals(aristas, "0-3\n" + "0-4\n" + "1-3\n" + "2-3\n");
		assertEquals(peso, 4);
	}
	@Test
	public void testPrim2() 
	{
		setUpEscenario2();
		String resultado = juego.primMST(juego.convertirLista(juego.getGrafoListaAd()));
		String aristas = juego.aristasPrimKruskal(juego.kruskal(juego.convertirLista(juego.getGrafoListaAd())));
		int peso = juego.pesosPrimKruskal(juego.kruskal(juego.convertirLista(juego.getGrafoListaAd())));
		
		assertEquals(resultado, "2,1,5 0,2,5 2,3,5 2,4,5 3,5,5 1,6,5 ");
		assertEquals(aristas, "0-2\n" +"1-2\n" + "1-6\n" + "2-3\n" + "2-4\n" + "3-5\n");
		assertEquals(peso, 30);
	}
//	@Test
//	public void testFW() {
//		setUpEscenario2();
//		int[][] expected= {{0,73,114,57,45},{73,0,102,45,28},{114,102,0,57,117},{57,45,57,0,60},{45,28,117,60,0}};
//		int[][] actual = juego.floydWarshall(juego.getGrafoMatrizAd());
//		assertArrayEquals(expected,actual);
//	}
	
	
}
