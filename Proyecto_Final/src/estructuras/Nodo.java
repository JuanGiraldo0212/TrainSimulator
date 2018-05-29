package estructuras;

public class Nodo<T> {
	
	private Nodo<T> next;
	
	private T value;
	
	public Nodo(T value){
		this.value=value;
		next=null;
	}
	
	public void setNext(Nodo<T> item){
		next=item;
	}
	
	public Nodo<T> getNext(){
		return next;
	}
	
	public T getValue(){
		return value;
	}

}
