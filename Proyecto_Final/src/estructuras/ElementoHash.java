package estructuras;

import java.util.HashMap;

public class ElementoHash<K,T> {
	
	private K key;
	
	private T value;
	
	private ElementoHash<K,T> collision;
	
	public ElementoHash(K key,T value){
		this.value=value;
		this.key=key;
		collision=null;
	}
	
	public ElementoHash<K,T> getCollision(){
		return collision;
	}
	
	public void setCollision(ElementoHash<K,T> collision){
		if(this.collision==null){
			this.collision=collision;
		}
		else{
			this.collision.setCollision(collision);
		}
	}
	
	public T getValue(){
		return value;
	}
	
	public K getKey(){
		return key;
	}

}
