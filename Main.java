import java.util.*;

class Main{

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		System.out.print("Select number of processes(3 - 10): ");
		int numP = scan.nextInt();

		while(numP < 3 || numP > 10){
			System.out.print("Wrong Input! \n Select number of processes(3 - 10): ");
			numP = scan.nextInt();
		}
		
		Process[] process = new Process[numP];
		for(int i = 0; i < numP; i++){
			System.out.println("Process P" + i + ": ");
			System.out.print("Arrival Time: ");
			int aT = scan.nextInt();
			System.out.print("Burst Time: ");
			int bT = scan.nextInt();
			System.out.print("Priority: ");
			int p = scan.nextInt();
			process[i] = new Process(aT, bT, p);
		}
		
		System.out.print("Time Quantum for Round Robin: ");
		int timeQuantum = scan.nextInt();
		
		scan.close();

		Calculation cal = new Calculation();
		
 		System.out.println("-----------------------------------------------------------------");
		System.out.println("| Process \t| Arrival time \t| Burst Time \t| Priority \t|");
		System.out.println("-----------------------------------------------------------------");
		
		for(Process i: process){
			System.out.println(i);
			System.out.println("-----------------------------------------------------------------");
		}
		
		System.out.println("\n\n");
		System.out.println("Non-preemptive Priority schedule: ");
		cal.nPreemptive(process, "Priority");


		System.out.println("\n\n");
		System.out.println("Non-preemptive SJF: ");
		cal.nPreemptive(process, "SJF");
		
		System.out.println("\n\n");
		System.out.println("Round Robin with Quantum-" + timeQuantum +" : ");
		cal.rRobin(process, timeQuantum);
		
		System.out.println("\n\n");
		System.out.println("Preemptive SJF: ");
		cal.preemptive(process, "SJF");
		
		System.out.println("\n\n");
		System.out.println("Preemptive Priority: ");
		cal.preemptive(process, "Priority");
		
		System.exit(0);
		
	}
}



