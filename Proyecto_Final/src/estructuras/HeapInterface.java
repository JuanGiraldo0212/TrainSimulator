package estructuras;

public interface HeapInterface<T>{	
	
	public void parent(int i);
	public void left(int i);
	public void right(int i);
	public void heapMaximum(T A);

	public void maxHeapify(T A, int i);
	public void buildMaxHeap(T A);
	public void heapSort(T A);
	public void heapExtractMax(T A);
	public void heapIncreaseKey(T A, int i, int key);
	public void maxHeapInsert(T A, int key);
	
	
}
