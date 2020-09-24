import java.util.Comparator;

public class ProcessATComparator implements Comparator<Process>{
	public int compare(Process p1, Process p2){
		return p1.getAT() - p2.getAT();
	}

}