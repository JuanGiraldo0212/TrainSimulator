package estructuras;

import Excepciones.QueueException;

public class Queue<T> implements QueueInterface<T> {
	
	private Nodo<T> first;
	
	private Nodo<T> last;
	
	public Queue(){
		first=null;
		last=null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return last==null;
	}

	@Override
	public void enqueue(T item) {
		// TODO Auto-generated method stub
		Nodo<T> nuevo=new Nodo<>(item);
		if(isEmpty()){
			first=nuevo;
			last=nuevo;
		}
		else{
			last.setNext(nuevo);
			last=nuevo;
		}
		
		
	}

	@Override
	public T front() throws QueueException{
		if(first == null)
		{
			throw new QueueException();

		}
		Object resultado = first.getValue();
		return (T) resultado;
	}

	@Override
	public T dequeue() throws QueueException{
		// TODO Auto-generated method stub
		Nodo<T> firstNodo=null;
		if(!isEmpty()){
			firstNodo=first;
			if(first==last){
				last=null;
			}
			else{
				first =first.getNext();
			}			
		}
		else
		{
			throw new QueueException();
		}
		return firstNodo.getValue();
	}

}
