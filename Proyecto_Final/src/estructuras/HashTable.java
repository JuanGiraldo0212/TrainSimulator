package estructuras;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HashTable <K,T> implements HashInterface<K,T>{
	
	public final static double A=(Math.sqrt(5)-1)/2;
	
	private ElementoHash<K,T>[] hash;
	
	private int m;
	
	@SuppressWarnings("unchecked")
	public HashTable(int size) {
		ElementoHash<K,T> clase = new ElementoHash("clasePrueba", 2);
		hash= (ElementoHash<K,T>[]) Array.newInstance(clase.getClass(), size);
		m=size;
	}
	
	public int hashFunction(int key){
		
		int valor=(int) Math.floor(m*((key*A)%1));
		
		return valor;
	}
	
	@Override
	public boolean isEmpty() {
		boolean resultado = true;
		for (int i = 0; i < hash.length && resultado; i++) {
			if(hash[i] != null)
			{
				resultado = false;
			}
		}
		return resultado;
	}

	@Override
	public int tableLenght() {
		return hash.length;
	}

	@Override
	public void tableInsert(K key,T item) {
			int keyN =key.hashCode();
			int index=hashFunction(keyN);
			ElementoHash<K,T> actual= new ElementoHash<K,T>(key, item);
			if(hash[index]!=null){
				hash[index].setCollision(actual);
			}
			else{
				hash[index]=actual;
			}
		
	}

	@Override
	public void tableDelete(K key){
		
			int keyN =key.hashCode();
			int index=hashFunction(keyN);
			ElementoHash<K,T> actual=hash[index];
			if(!actual.getKey().equals(key)){
				
				while(!(actual.getCollision().getKey().equals(key))){
					actual=actual.getCollision();
				}
				if(actual.getCollision().getCollision()!=null){
					actual.setCollision(actual.getCollision().getCollision());
				}
				else{
					actual.setCollision(null);
				}
			}
			else{
				if(actual.getCollision()==null){
					
					hash[index]=null;
				}
				else{
					hash[index]=actual.getCollision();
				}
			}
		} 
		
		
	

	@Override
	public T tableRetrieve(K key) {
		ElementoHash<K,T> retorno=null;
		
			int keyN =key.hashCode();
			int index=hashFunction(keyN);
			ElementoHash<K,T> actual=hash[index];
			if(actual == null)
			{
				return null;
			}
			if(!actual.getKey().equals(key)){
				
				while(!(actual.getCollision().getKey().equals(key))){
					actual=actual.getCollision();
				}
				retorno=actual.getCollision();
				
			}
			else{
				retorno=actual;
			}

		return retorno.getValue();
		
	}

	
}
