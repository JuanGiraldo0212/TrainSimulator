package estructuras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class DisjointSets<T extends Comparable<T>> {

	public ArrayList<LinkedList<DisjointNode<T>>> sets;
	
	public DisjointSets()
	{
		sets = new ArrayList<>();
	}
	public void makeSet(T value)
	{
		LinkedList<DisjointNode<T>> set = new LinkedList<>();
		set.add(new DisjointNode<T>(value, set));
		sets.add(set);
	}
	public void union(T value1, T value2)
	{
		LinkedList<DisjointNode<T>> set1 = findSet(value1);
		LinkedList<DisjointNode<T>> set2 = findSet(value2);
		if(set1.size() >= set2.size())
		{
			for (DisjointNode<T> disjointNode : set2) 
			{
				disjointNode.setSet(set1);
				set1.add(disjointNode);
			}
			sets.remove(set2);
		}
		else
		{
			for (DisjointNode<T> disjointNode : set1) 
			{
				disjointNode.setSet(set2);
				set2.add(disjointNode);
			}
			sets.remove(set1);
		}
	}
	public LinkedList<DisjointNode<T>> findSet(T value)
	{
		LinkedList<DisjointNode<T>> set = null;
		for (int i = 0; i < sets.size(); i++) 
		{
			for (int j = 0; j < sets.get(i).size(); j++) 
			{
				if(sets.get(i).get(j).compareTo(value) == 0)
				{
					set = sets.get(i);
				}
			}
		}
		return set;
	}
}
