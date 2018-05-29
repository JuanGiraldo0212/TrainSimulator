package interfaz;


import java.awt.Color;

import estructuras.DisjointNode;
import mundo.Juego;

public class Cronometro extends Thread{

	private InterfazJuego frame;
	private int m, s, cs;
	private Color color;
	private int tiempoDijkstra;
	
	public Cronometro(InterfazJuego frame)
	{
		this.frame = frame;
		color = Color.GREEN;
		m = 0;
		s = 0;
		cs = 0;
	}
	public void run()
	{
		while(!frame.getMundo().getTren().isDetenido())
		{
			super.run();
			cs++;
			if(cs == 100)
			{
				cs = 0;
				++s;
			}
			if(s == 60)
			{
				s = 0;
				++m;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(frame.getMundo().getTipo().equals(Juego.TIPO_MATRIZ)){
				
				tiempoDijkstra = (((frame.getMundo().dijkstra(frame.getMundo().getGrafoMatrizAd(), Integer.parseInt(frame.getMundo().getTren().getSalida().getCodigo()), Integer.parseInt(frame.getMundo().getTren().getLlegada().getCodigo())).getPeso())/50)*2);
			}
			else{
				tiempoDijkstra = (((frame.getMundo().dijkstra(frame.getMundo().corregirMatriz(frame.getMundo().convertirLista(frame.getMundo().getGrafoListaAd())), Integer.parseInt(frame.getMundo().getTren().getSalida().getCodigo()), Integer.parseInt(frame.getMundo().getTren().getLlegada().getCodigo())).getPeso())/50)*2);
			}

		if(60 >= tiempoDijkstra)
		{
				if(s > tiempoDijkstra)
			{
				color = Color.RED;
			}	
		}
		else 
		{
			if(m*60 > tiempoDijkstra)
			{
					color = Color.RED;
			}
		}
		frame.getPanelJuego().repaint();

		}
	}
	public int getM() {
		return m;
	}
	public int getS() {
		return s;
	}
	public int getCs() {
		return cs;
	}
	public void setM(int m) {
		this.m = m;
	}
	public void setS(int s) {
		this.s = s;
	}
	public void setCs(int cs) {
		this.cs = cs;
	}
	public InterfazJuego getFrame() {
		return frame;
	}
	public Color getColor() {
		return color;
	}
	public void setFrame(InterfazJuego frame) {
		this.frame = frame;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getTiempo(){
		return m+s+cs;
	}
	
	public String tiempoToString(){
		return m+":"+s+":"+cs;
	}
	public int getTiempoDijkstra() {
		return tiempoDijkstra;
	}
	public void setTiempoDijkstra(int tiempoDijkstra) {
		this.tiempoDijkstra = tiempoDijkstra;
	}
	
	public String tiempoDijkstraToString(){
		int tiempo=tiempoDijkstra;
		return tiempo/60+":"+tiempo%60+":0";
	}
	
	public String calificacion(){
		int dif=Math.abs(tiempoDijkstra-getTiempo());
		return 100-dif+"%";
	}
}
