import java.util.Comparator;
import java.util.List;
import java.util.Arrays;

class ProcessComparator implements Comparator<Process>{	
	private List<Comparator<Process>> listComparators;
	
	@SafeVarargs
	public ProcessComparator(Comparator<Process>... comparators){
		listComparators = Arrays.asList(comparators);
	}
	
	public int compare(Process p1, Process p2){
		for(Comparator<Process> comparator: listComparators){
			int result = comparator.compare(p1, p2);
			if(result != 0){
				return result;
			}
		}
		return 0;
	}
}