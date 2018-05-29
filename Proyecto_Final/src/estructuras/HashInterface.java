package estructuras;

public interface HashInterface<K,T> {
	
	public boolean isEmpty();
	
	public int tableLenght();
	
	public void tableInsert(K key,T item);
	
	public void tableDelete(K key);
	
	public T tableRetrieve(K key);
	
}
