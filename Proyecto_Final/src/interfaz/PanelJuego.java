package interfaz;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;

import mundo.Juego;
import mundo.Tren;
import mundo.Vertex;
public class PanelJuego extends JPanel implements KeyListener{

	private int[][] grafoMatrizAd;
	private Hashtable<String, ArrayList<int[]>> grafoListaAd;
	private ArrayList<Vertex> vertices;
	private Tren tren;
	private Vertex seleccionado;
	private Juego principal;
	private Cronometro cronometro;
	private InterfazJuego frame;
	
	public PanelJuego(int[][] mat, ArrayList<Vertex> ver,Tren tren, Juego principal, InterfazJuego frame){
		setLayout(new BorderLayout());
		setFocusable(true);
		addKeyListener(this);
		this.frame = frame;
		grafoMatrizAd=mat;
		grafoListaAd = null;
		vertices=ver;
		this.principal = principal;
		this.tren=tren;
		cronometro = new Cronometro(frame);
		seleccionado=tren.getActual();
		cambiarSeleccionadoDer();
	}
	public PanelJuego(Hashtable<String, ArrayList<int[]>> grafoListaAd, ArrayList<Vertex> vertices, Tren tren, InterfazJuego frame,Juego principal)
	{
		setLayout(new BorderLayout());
		setFocusable(true);
		addKeyListener(this);
		this.frame = frame;
		this.grafoListaAd = grafoListaAd;
		grafoMatrizAd = null;
		this.vertices= vertices;
		this.tren=tren;
		this.principal = principal;
		cronometro = new Cronometro(frame);
		if(vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(0)[0]).getCodigo().equalsIgnoreCase(tren.getActual().getCodigo()))
		{
			seleccionado = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(1)[0]);
		}
		else
		{
			seleccionado = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(0)[0]);
		}
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawString("Velocidad: "+tren.getVelocidad(), 400, 20);

//		g2.drawImage(new ImageIcon("./data/fondo1.png").getImage(),0,0,this);
		g2.setColor(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, 1400, 1000);
		g2.drawImage(new ImageIcon("./data/Escenario/Panel.png").getImage(), 0, 793, this);

		g2.drawImage(new ImageIcon("./Proyecto_Final/data/fondo1.png").getImage(),0,0,this);
		//g2.drawImage(new ImageIcon("./data/helpButt.png").getImage(), 700, 600, this);
		String time = cronometro.getM() + ":" + cronometro.getS()
		+ ":" + cronometro.getCs();
		g2.setFont(new Font("Impact", Font.BOLD, 30));
		g2.setColor(Color.GREEN);
		g2.drawString("Time: ",60, 870);
		g2.drawString("Speed: ",340, 870);
		g2.drawString(tren.getVelocidad()*10+" Km/H", 340, 920);
		g2.drawString("Speed Limit: ",620, 870);
		g2.drawString(tren.getActual().getVelocidad()*10+" Km/H", 620, 920);
		g2.setColor(cronometro.getColor());
		g2.drawString(time+" S",60, 920);
		if(grafoMatrizAd != null)
		{
			for(int i=0;i<grafoMatrizAd.length;i++){
				Vertex actual=vertices.get(i);
				for(int j=0;j<grafoMatrizAd[0].length;j++){
					if(grafoMatrizAd[i][j]!=0 && grafoMatrizAd[i][j]!=99999){
						Vertex relacion=vertices.get(j);
						g2.setStroke(new BasicStroke(8));
						g2.setColor(Color.GRAY);
						g2.drawLine(actual.getPosX()+20, actual.getPosY()+20, relacion.getPosX()+20, relacion.getPosY()+20);
						g2.setColor(Color.BLACK);
						g2.setStroke(new BasicStroke(3));
						g2.drawLine(actual.getPosX()+20, actual.getPosY()+20, relacion.getPosX()+20, relacion.getPosY()+20);
					}
				}
			}
			
		}
		else if(grafoListaAd != null)
		{
			for(int i = 0; i < vertices.size(); i++)
			{
				Vertex actual = vertices.get(i);
				ArrayList<int[]> aristas = grafoListaAd.get(actual.getCodigo());
				for(int j = 0; j < aristas.size();j++)
				{
					Vertex adyacente = vertices.get(aristas.get(j)[0]);
					g2.setColor(Color.BLACK);
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(actual.getPosX()+20, actual.getPosY()+20, adyacente.getPosX()+20, adyacente.getPosY()+20);
				}
			}
			
		}
		g2.setColor(Color.RED);
		if(!principal.isJugando()) {
			
			ArrayList<Vertex> camino = principal.getCamino();
			for(int i = 0;i<camino.size()-1;i++) {
				g2.setColor(Color.GREEN);
				g2.setStroke(new BasicStroke(3));
				g2.drawLine(camino.get(i).getPosX()+20, camino.get(i).getPosY()+20, camino.get(i+1).getPosX()+20, camino.get(i+1).getPosY()+20);
			}
			
			ArrayList<Integer> temp;
			if(principal.getTipo().equals(Juego.TIPO_MATRIZ)){
				
				temp=principal.dijkstra(principal.getGrafoMatrizAd(), Integer.parseInt(tren.getInicio().getCodigo()), Integer.parseInt(tren.getMeta().getCodigo())).getCamino();
			}
			else{
				temp=principal.dijkstra(principal.corregirMatriz(principal.convertirLista(principal.getGrafoListaAd())), Integer.parseInt(tren.getInicio().getCodigo()), Integer.parseInt(tren.getMeta().getCodigo())).getCamino();
			}
			for(int i = 0;i<temp.size()-1;i++) {
				g2.setColor(Color.RED);
				g2.setStroke(new BasicStroke(3));
				g2.drawLine(principal.getVertices().get(temp.get(i)).getPosX()+20, principal.getVertices().get(temp.get(i)).getPosY()+20, principal.getVertices().get(temp.get(i+1)).getPosX()+20, principal.getVertices().get(temp.get(i+1)).getPosY()+20);
			}
		}
		Vertex inicial = tren.getActual();
		g2.setColor(Color.green);
		g2.drawLine(inicial.getPosX()+20, inicial.getPosY()+20, seleccionado.getPosX()+20, seleccionado.getPosY()+20);
		g2.setStroke(new BasicStroke(2));
		for(int i=0;i<vertices.size();i++){
			g2.setColor(Color.RED);
			if(vertices.get(i).getTipo()==Vertex.VERTEX_ESTACION){
				g2.drawImage(new ImageIcon(tren.getImagen()).getImage(), tren.getPosX()+3, tren.getPosY(), this);
				g2.drawImage(new ImageIcon(vertices.get(i).getImagen()).getImage(),vertices.get(i).getPosX()-3, vertices.get(i).getPosY(),this);
			}
			else{
				g2.drawImage(new ImageIcon(vertices.get(i).getImagen()).getImage(),vertices.get(i).getPosX()+8, vertices.get(i).getPosY()+8,this);
				g2.drawImage(new ImageIcon(tren.getImagen()).getImage(), tren.getPosX()+3, tren.getPosY(), this);
			}
			
		}
		g2.setColor(Color.CYAN);
		//g2.fillOval(tren.getPosX(), tren.getPosY(), 20, 20);
		
		
		
	}
	
	public void cambiarSeleccionadoDer(){
		if(grafoMatrizAd != null)
		{
			Vertex llegada=tren.getActual();
			int salida = Integer.parseInt(tren.getSalida().getCodigo());
			int fila = Integer.parseInt(llegada.getCodigo());
			int columna=Integer.parseInt(seleccionado.getCodigo());
			int valor=0;
			while(valor==0 || valor==99999){
				if(columna+1>grafoMatrizAd[0].length-1){
					columna=-1;
				}else{
					if(columna+1!=salida){
						columna++;
						valor=grafoMatrizAd[fila][columna];
					}
					else{
						columna+=1;
					}
				}
			}
			seleccionado=vertices.get(columna);
		}
		else if(grafoListaAd != null)
		{
			int indice = -1;
			int indiceSalida = -1;
			for (int i = 0; i < grafoListaAd.get(tren.getActual().getCodigo()).size(); i++) 
			{
				if(grafoListaAd.get(tren.getActual().getCodigo()).get(i)[0] == Integer.parseInt(seleccionado.getCodigo()))
				{
					indice = i;
				}
				if(grafoListaAd.get(tren.getActual().getCodigo()).get(i)[0] == Integer.parseInt(tren.getSalida().getCodigo()))
				{
					indiceSalida = i;
				}
			}
			int proximo = indice + 1;
			if(proximo < grafoListaAd.get(tren.getActual().getCodigo()).size())
			{
				Vertex vertice = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(proximo)[0]);
				if(indiceSalida != proximo)
				{
					seleccionado = vertice;
					System.out.println(vertice.getCodigo() + " " + tren.getSalida().getCodigo());
				}
				else
				{
					proximo++;
					try 
					{
						seleccionado = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(proximo)[0]);
					}
					catch (Exception e) 
					{
						seleccionado = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(0)[0]);		
					}
					
				}
				
			}
			else
			{
				Vertex vertice = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(0)[0]);
				if(indiceSalida != proximo)
				{
					seleccionado = vertice;
				}
				else
				{
					seleccionado = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(1)[0]);
				}
			}
		}
	}
	public void cambiarSeleccionadoIzq(){
		if(grafoMatrizAd != null)
		{
			Vertex actual=tren.getActual();
			int salida = Integer.parseInt(tren.getSalida().getCodigo());
			int fila = Integer.parseInt(actual.getCodigo());
			int columna=Integer.parseInt(seleccionado.getCodigo());
			int valor=0;
			while(valor==0 || valor==99999) {
				if(columna-1<0){
					columna=grafoMatrizAd[0].length;
				}else{
					if(columna-1!=salida){
						
						columna--;
						valor=grafoMatrizAd[fila][columna];
					}
					else{
						columna-=1;
					}
				}
			}
			seleccionado=vertices.get(columna);
		}
		else if(grafoListaAd != null)
		{
			int indice = 0;
			int indiceSalida = 0;
			for (int i = 0; i < grafoListaAd.get(tren.getActual().getCodigo()).size(); i++) 
			{
				if(grafoListaAd.get(tren.getActual().getCodigo()).get(i)[0] == Integer.parseInt(seleccionado.getCodigo()))
				{
					indice = i;
				}
				if(grafoListaAd.get(tren.getActual().getCodigo()).get(i)[0] == Integer.parseInt(tren.getSalida().getCodigo()))
				{
					indiceSalida = i;
				}
			}
			int proximo = indice - 1;
			if(proximo >= 0)
			{
				Vertex vertice = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(proximo)[0]);
				if(indiceSalida != indice)
				{
					seleccionado = vertice;
				}
				else
				{
					proximo--;
					try 
					{
						seleccionado = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(proximo)[0]);
					}
					catch (Exception e) 
					{
						seleccionado = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(grafoListaAd.get(tren.getActual().getCodigo()).size()-1)[0]);
	
					}
				}
				
			}
			else
			{
				Vertex vertice = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(grafoListaAd.get(tren.getActual().getCodigo()).size()-1)[0]);
				if(indiceSalida != indice)
				{
					seleccionado = vertice;
				}
				else
				{
					try
					{
						seleccionado = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(grafoListaAd.get(tren.getActual().getCodigo()).size()-2)[0]);
					}
					catch (Exception e) 
					{
						seleccionado = vertices.get(grafoListaAd.get(tren.getActual().getCodigo()).get(0)[0]);
					}
				}
			}
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stu
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int comando= e.getKeyCode();
		if(comando==KeyEvent.VK_RIGHT){
			
				
				cambiarSeleccionadoDer();
				repaint();
			
		}
		else if(comando==KeyEvent.VK_LEFT){
			
				
				cambiarSeleccionadoIzq();
				repaint();
			
		}
		else if(comando==KeyEvent.VK_SHIFT){
			if(!tren.isDetenido()){
				
				tren.aumentarVelocidad();
				
			}
		}
		else if(comando==KeyEvent.VK_SPACE){
			if(!tren.isDetenido()){
				
				tren.disminuirVelocidad();
			}
		}
		else if(comando==KeyEvent.VK_ENTER){
			if(tren.isDetenido()){
				tren.setDetenido(false);
				frame.iniciarHilos();
				comenzarTiempo();
			}
			tren.aumentarVelocidad();
			System.out.println("ok");
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public Vertex getSeleccionado(){
		return seleccionado;
	}
	
	public void cambiarSeleccionado(){
		//seleccionado=tren.getActual();
		if(seleccionado.getTipo().equals(Vertex.VERTEX_GIRO)){
			cambiarSeleccionadoDer();
		}
	}
	
	public Juego getPrincipal() {
		return principal;
	}
	public Cronometro getCronometro() {
		return cronometro;
	}
	public InterfazJuego getFrame() {
		return frame;
	}
	public void setPrincipal(Juego principal) {
		this.principal = principal;
	}
	public void setCronometro(Cronometro cronometro) {
		this.cronometro = cronometro;
	}
	public void setFrame(InterfazJuego frame) {
		this.frame = frame;
	}

	public void comenzarTiempo()
	{
		cronometro.start();
	}
}
