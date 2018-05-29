package estructuras;

import Excepciones.StackException;

public class Stack<T> implements StackInterface<T>{

	private T[] stack;
	
	private int lenght;
	
	private int top;
	
	public Stack(int n){
		lenght=n;
		stack = (T[]) new Object[lenght];
		top=-1;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return top<0;
		
	}

	@Override
	public void push(T item) throws StackException{
		// TODO Auto-generated method stub
		if(top == lenght - 1)
		{
			throw new StackException("La pila está llena");
		}
		if(top<lenght-1){
			top++;
			stack[top]=item;
		}
		
	}

	@Override
	public T top() throws StackException{
		// TODO Auto-generated method stub
		T item=null;
		if(!isEmpty()){
			item=stack[top];
		}
		else
		{
			throw new StackException("La pila está vacía");
		}
		return item;
	}

	@Override
	public void pop() throws StackException{
		// TODO Auto-generated method stub
		if(!isEmpty()){
			top=top-1;
		}
		else
		{
			throw new StackException("La pila está vacía");
		}
		
	}
	
	
}
