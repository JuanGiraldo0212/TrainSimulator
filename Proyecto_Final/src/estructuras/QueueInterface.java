package estructuras;

import Excepciones.QueueException;

public interface QueueInterface<T> {
	
	public boolean isEmpty();
	
	public void enqueue(T item);
	
	public T front() throws QueueException;
	
	public T dequeue() throws QueueException;

}
