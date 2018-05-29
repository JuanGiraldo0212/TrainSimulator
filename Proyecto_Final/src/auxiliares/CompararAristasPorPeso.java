package auxiliares;



import java.util.Comparator;

public class CompararAristasPorPeso implements Comparator<int[]>{

	@Override
	public int compare(int[] o1, int[] o2) {
		return o1[2] - o2[2];
	}



}
