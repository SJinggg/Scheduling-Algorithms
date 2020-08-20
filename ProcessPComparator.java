import java.util.Comparator;

public class ProcessPComparator implements Comparator<Process>{
	public int compare(Process p1, Process p2){
		return p1.getPriority() - p2.getPriority();
	}

}