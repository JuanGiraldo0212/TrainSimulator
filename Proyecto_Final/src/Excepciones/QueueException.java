package Excepciones;

public class QueueException extends Exception{

	public QueueException()
	{
		super("No hay elementos en la lista");
	}
}
