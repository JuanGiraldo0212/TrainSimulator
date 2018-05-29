package interfaz;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import javax.swing.*;

import hilos.HiloTren;
import mundo.Juego;
public class InterfazJuego extends JFrame{
	public final static int ANCHO =1400;
	public final static int LARGO =1000;
	
	private Juego mundo;
	private PanelInicio inicio;
	private PanelJuego juego;
	private HiloTren tren;
	
	public InterfazJuego(){
		mundo=new Juego();
		setTitle("Jueguirris");
		setLayout(new BorderLayout());
		setSize(new Dimension(ANCHO, LARGO));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		inicio= new PanelInicio(this);
		add(inicio,BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		InterfazJuego ventana= new InterfazJuego();
		ventana.setVisible(true);
	}
	
	public void cargarArchivo(){
		JFileChooser fc = new JFileChooser("./data");
//		JFileChooser fc = new JFileChooser("./data");
		int result = fc.showOpenDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			String[] opciones = {"Matriz de Adyacencia", "Lista de Adyacencia"};
			String tipo = null;
		    do
		    {
			    tipo = (String) JOptionPane.showInputDialog(null, "Escoge el tipo de implementación", "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
		    	if(tipo != null)
		    	{
		    		if(tipo.equals("Matriz de Adyacencia"))
		    		{
		    			mundo.setTipo(Juego.TIPO_MATRIZ);
		    			mundo.cargarGrafoMatrizAd(file);
		    		}
		    		else if(tipo.equals("Lista de Adyacencia"))
		    		{
		    			mundo.setTipo(Juego.TIPO_LISTA);
		    			mundo.cargarGrafoListaAd(file);
		    		} 		
		    	}
		    }while(tipo == null);
		    
		}
		cambiarPantalla();
		//mundo.floydWarshall(mundo.getGrafoMatrizAd());
		
	}
	
	public void cambiarPantalla(){
		remove(inicio);
		if(mundo.getGrafoMatrizAd() != null)
		{
			juego= new PanelJuego(mundo.getGrafoMatrizAd(),mundo.getVertices(),mundo.getTren(), mundo, this);
		}
		else if(mundo.getGrafoListaAd() != null)
		{
			juego = new PanelJuego(mundo.getGrafoListaAd(), mundo.getVertices(), mundo.getTren(), this,mundo);
		}
		add(juego,BorderLayout.CENTER);
		pack();
		setSize(new Dimension(ANCHO, LARGO));
		juego.requestFocusInWindow();
	}
	
	public void darUsuario(String datos){
		mundo.setUsuario(datos);
	}
	
	public void terminar(){
		String[] datos=mundo.getUsuario().split("-");
		double distR;
		double distD;
		if(mundo.getTipo().equals(Juego.TIPO_MATRIZ)){
			
			distR=mundo.distanciaRecorrida(mundo.getGrafoMatrizAd());
			distD=mundo.dijkstra(mundo.getGrafoMatrizAd(), Integer.parseInt(mundo.getTren().getInicio().getCodigo()), Integer.parseInt(mundo.getTren().getMeta().getCodigo())).getPeso();
		}
		else{
			distR=mundo.distanciaRecorrida(mundo.corregirMatriz(mundo.convertirLista(mundo.getGrafoListaAd())));
			distD=mundo.dijkstra(mundo.corregirMatriz(mundo.convertirLista(mundo.getGrafoListaAd())), Integer.parseInt(mundo.getTren().getInicio().getCodigo()), Integer.parseInt(mundo.getTren().getMeta().getCodigo())).getPeso();
		}
		double cal=(distD/distR)*100;
		if(cal>100){
			cal=100;
		}
		JOptionPane.showMessageDialog(this, "Nombre: "+datos[0]+"\n"+"Codigo operario: "+datos[1]+"\n"+"Estado: Completo"+"\n"+"Tiempo: "+juego.getCronometro().tiempoToString()+"\n"+"Distancia recorrida: "+(int)distR+" Mts"+"\n"+"Distancia esperada: "+(int)distD+" Mts"+"\n"+"Calificacion: "+Math.ceil(cal)+"%");
	}
	
	public void jugarOtraVez(){
		int resp=JOptionPane.showConfirmDialog(this, "Desea iniciar otra simulacion?");
		if(resp==JOptionPane.YES_OPTION){
			mundo.iniciarTren();
			mundo.setJugando(true);
		}
	}
	
	public void altaVelocidad(){
		String[] datos=mundo.getUsuario().split("-");
		double distR;
		double distD;
		if(mundo.getTipo().equals(Juego.TIPO_MATRIZ)){
			
			distR=mundo.distanciaRecorrida(mundo.getGrafoMatrizAd());
			distD=mundo.dijkstra(mundo.getGrafoMatrizAd(), Integer.parseInt(mundo.getTren().getInicio().getCodigo()), Integer.parseInt(mundo.getTren().getMeta().getCodigo())).getPeso();
		}
		else{
			distR=mundo.distanciaRecorrida(mundo.corregirMatriz(mundo.convertirLista(mundo.getGrafoListaAd())));
			distD=mundo.dijkstra(mundo.corregirMatriz(mundo.convertirLista(mundo.getGrafoListaAd())), Integer.parseInt(mundo.getTren().getInicio().getCodigo()), Integer.parseInt(mundo.getTren().getMeta().getCodigo())).getPeso();
		}
		double cal=(distD/distR)*100;
		if(cal>100){
			cal=100;
		}
		JOptionPane.showMessageDialog(this, "Nombre: "+datos[0]+"\n"+"Codigo operario: "+datos[1]+"\n"+"Estado: Incompleto, Limite de velocidad excedido"+"\n"+"Tiempo: "+juego.getCronometro().tiempoToString()+"\n"+"Distancia recorrida: "+(int)distR+" Mts"+"\n"+"Distancia esperada: "+(int)distD+" Mts"+"\n"+"Calificacion: 0%");
	}
	
	public void iniciarHilos(){
		tren=new HiloTren(mundo.getTren(),this);
		tren.start();
		mundo.setJugando(true);
	}
	
	public void actualizar(){
		juego.repaint();
		}
	public PanelJuego getPanelJuego(){
		return juego;
	}
	
	public void cambiarSeleccionado(){
		juego.cambiarSeleccionado();
	}

	public Juego getMundo() {
		return mundo;
	}

	public void setMundo(Juego mundo) {
		this.mundo = mundo;
	}

	
}
