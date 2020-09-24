import java.util.Comparator;

public class ProcessPNComparator implements Comparator<Process>{
	public int compare(Process p1, Process p2){
		return p1.getPName().compareToIgnoreCase(p2.getPName());
	}

}