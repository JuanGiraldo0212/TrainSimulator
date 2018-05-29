package mundo;

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex>{
	
	public final static String VERTEX_ESTACION="Estacion";
	public final static String VERTEX_GIRO="Giro";
	public final static int BLANCO=0;
	public final static int GRIS=1;
	public final static int NEGRO=2;
	
	String codigo;
	private String tipo;
	private int color;
	private int velocidad;
	private int posX;
	private int posY;
	private int distancia;
	private String imagen;
	private ArrayList<Vertex> listaAdj;
	
	public Vertex(String codigo,String tipo,int velocidad, int posX, int posY){
		this.codigo=codigo;
		this.velocidad=velocidad;
		this.posX=posX;
		color = 0;
		this.posY=posY;
		if(tipo.equals("E")){
			this.tipo=VERTEX_ESTACION;
			setImagen("./data/Escenario/Estacion.png");
		}
		else{
			this.tipo=VERTEX_GIRO;
			setImagen("./data/Escenario/Giro.png");
		}
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ArrayList<Vertex> getListaAdj() {
		return listaAdj;
	}

	public void setListaAdj(ArrayList<Vertex> listaAdj) {
		this.listaAdj = listaAdj;
	}

	@Override
	public int compareTo(Vertex o) {
		
		return this.getCodigo().compareTo(o.getCodigo());
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
	

}
