package estructuras;

import Excepciones.StackException;

public interface StackInterface<T> {
	
	public boolean isEmpty();
	
	public void push(T item) throws StackException;
	
	public T top() throws StackException;
	
	public void pop() throws StackException;
	
	

}
