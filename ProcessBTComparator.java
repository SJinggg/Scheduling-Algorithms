import java.util.Comparator;

public class ProcessBTComparator implements Comparator<Process>{
	public int compare(Process p1, Process p2){
		return p1.getBT() - p2.getBT();
	}

}