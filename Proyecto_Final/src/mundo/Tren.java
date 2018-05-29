package mundo;

public class Tren {
	
	public final static char DIR_ARRIBA='U';
	public final static char DIR_ABAJO='D';
	public final static char DIR_DERECHA='R';
	public final static char DIR_IZQUIERDA='L';
	
	private int posX;
	private int posY;
	private char direccion;
	private boolean detenido;
	private Vertex llegada;
	private Vertex salida;
	private Vertex meta;
	private Vertex inicio;
	private int velocidad;
	private String imagen;
	
	public Tren (int posX, int posY, char direccion) {
		
		this.posX=posX;
		this.posY=posY;
		this.direccion=direccion;
		detenido=true;
		setActual(null);
		velocidad=0;
		setImagen("./data/Tren/Front_Down.png");
	}
	
	public void movimiento(){
		int difX=llegada.getPosX()-salida.getPosX();
		int difY=llegada.getPosY()-salida.getPosY();
		if(difX!=0 && difY!=0){
			int pendiente=difY/difX;
			
			if(difX>0 && difY>0){
				posX+=5;
				posY+=(Math.abs(pendiente*5));
				imagen="./data/Tren/Turn_Front_Right.png";
			}
			else if(difX>0 && difY<0){
				posX+=5;
				posY-=(Math.abs(pendiente*5));
				imagen="./data/Tren/Turn_Back_Right.png";
			}
			else if(difX<0 && difY>0){
				posX-=5;
				posY+=(Math.abs(pendiente*5));
				imagen="./data/Tren/Turn_Front_Left.png";
			}
			else if(difX<0 && difY<0){
				posX-=5;
				posY-=(Math.abs(pendiente*5));
				imagen="./data/Tren/Turn_Back_Left.png";
			}
		
		}
		else if(difX==0){
			if(difY>0){
				posY+=5;
				imagen="./data/Tren/Front_Down.png";
			}
			else{
				posY-=5;
				imagen="./data/Tren/Front_Up.png";
			}
		}
		else if(difY==0){
			if(difX>0){
				posX+=5;
				imagen="./data/Tren/Side_Right.png";
			}
			else{
				posX-=5;
				imagen="./data/Tren/Side_Left.png";
			}
		}
		
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

	public char getDireccion() {
		return direccion;
	}

	public void setDireccion(char direccion) {
		this.direccion = direccion;
	}

	public boolean isDetenido() {
		return detenido;
	}

	public void setDetenido(boolean detenido) {
		this.detenido = detenido;
	}

	public Vertex getActual() {
		return llegada;
	}

	public void setActual(Vertex actual) {
		this.llegada = actual;
	}
	
	public boolean isOnVertex(){
		
		return (posX>=llegada.getPosX()+10 && posX<=llegada.getPosX()+10) && (posY>=llegada.getPosY()+10 && posY<=llegada.getPosY()+10) ? true:false;
	}

	public Vertex getSalida() {
		return salida;
	}

	public void setSalida(Vertex salida) {
		this.salida = salida;
	}

	public int getVelocidad() {
		return velocidad;
	}

	public void aumentarVelocidad() {
		velocidad++;
	}
	public void disminuirVelocidad() {
		if(velocidad>0){	
			velocidad--;
		}
	}

	public Vertex getLlegada() {
		return llegada;
	}

	public void setLlegada(Vertex llegada) {
		this.llegada = llegada;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Vertex getMeta() {
		return meta;
	}

	public void setMeta(Vertex meta) {
		this.meta = meta;
	}

	public Vertex getInicio() {
		return inicio;
	}

	public void setInicio(Vertex inicio) {
		this.inicio = inicio;
	}
	
	

}
