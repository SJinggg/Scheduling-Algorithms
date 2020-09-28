import java.util.*;
import java.text.DecimalFormat;

class Calculation1{
	int time = 0, count = 0;
	private DecimalFormat df = new DecimalFormat("#.##");
	
	public void rRobin(Process[] p, int tQuantum){
		ArrayList<Process> RR = new ArrayList<>();
		ArrayList<Process> arrived = new ArrayList<>();
		Process[] arrP = new Process[p.length];
		for(int i = 0; i < arrP.length; i++){
			Process pr = p[i];
			if(pr != null)
				arrP[i] = new Process(pr);
		}
		ArrayList<Process> process = new ArrayList<>(Arrays.asList(arrP));
		count = 0; time = 0; int maxTime = 0, cumulative = 0, minAT = 100;
		
		for(Process i:p){
			maxTime += i.getBT();
			if(minAT > i.getAT())
				minAT = i.getAT();
		}
		maxTime += minAT;
		cumulative += minAT;
		

		do{
			for(int j = 0; j < process.size(); j++){
				if(process.get(j).getAT() == time){
					arrived.add(process.get(j));
				}
			}
			//arrange by burst time
			/* if(arrived.size() > 1)
				for(int i = 0; i < arrived.size(); i++){
					for(int j = i; j < arrived.size()-i; j++){
						if(arrived.get(i).getAT() == arrived.get(j).getAT())
							if(arrived.get(i).getBT() > arrived.get(j).getBT()){
								Collections.swap(arrived, i, j);
							}
					}
				} */
			//System.out.println(arrived.get(0));
			//System.out.println("Test: " + arrived);
			//System.out.println("Time: " + time + ", cumulative: " + cumulative + ", arrivedSize: " + arrived.size());
			if(arrived.size() > 0){
				RR.add(arrived.get(0));
				RR.get(count).setStartExTime(cumulative);
				if(RR.get(count).getBT() >= tQuantum){
					if(RR.get(count).getBT() > tQuantum){
						process.add(new Process(arrived.get(0)));
						process.get(process.size() - 1).setBT(arrived.get(0).getBT() - tQuantum);
						process.get(process.size() - 1).setAT(cumulative + tQuantum);
					}
					RR.get(count).setBT(tQuantum);
				}
				cumulative += (RR.get(count).getBT() % tQuantum == 0) ? tQuantum: RR.get(count).getBT() % tQuantum;
				RR.get(count).setEndExTime(RR.get(count).getStartExTime() + RR.get(count).getBT());
				arrived.remove(0);
				count++;
			}
			time++;
		}while(time < maxTime);
		
		for(Process i: RR)
			System.out.print("    " + i.getPName() + "\t"); 
		
		System.out.println();
		for(int i = 0; i < RR.size(); i++){
			System.out.print(RR.get(i).getStartExTime() + "\t");
		} 
		System.out.println(maxTime);
		
		double avgTurnTime = turnAroundTime(p, RR);
		double avgWaitTime = waitingTime(p);
		
		printTable(p);
		
		System.out.println("Avg = " + df.format(avgTurnTime));
		System.out.println("AvgWait = " + df.format(avgWaitTime));
	}
	
	public void preemptive(Process[] pro, String pType){
		ArrayList<Process> p = new ArrayList<>();
		ArrayList<Process> arrived = new ArrayList<>(); 
		Process[] arrP = new Process[pro.length];
		for(int i = 0; i < arrP.length; i++){
			Process pr = pro[i];
			if(pr != null)
				arrP[i] = new Process(pr);
		}
		ArrayList<Process> process = new ArrayList<>(Arrays.asList(arrP));
		count = 0; time = 0; int maxTime = 0, cumulative = 0, minAT = 100, currentBT = 0;
		boolean result;
		for(Process i:pro){
			maxTime += i.getBT();
			if(minAT > i.getAT())
				minAT = i.getAT();
		}
		maxTime += minAT;
		cumulative += minAT;

		do{
			
			for(int j = 0; j < process.size(); j++){
				if(process.get(j).getAT() == time){
					arrived.add(process.get(j));
				}
			}
			if(pType.equals("SJF"))
				Collections.sort(arrived, new ProcessComparator(new ProcessBTComparator(), new ProcessPNComparator()));
			else
				Collections.sort(arrived, new ProcessComparator(new ProcessPComparator(), new ProcessPNComparator()));
			//System.out.println("cum" + cumulative + " time "+ time);
			if(arrived.size() > 0){
				p.add(arrived.get(0));
				//System.out.println(p);
				p.get(count).setStartExTime(cumulative);
				if(pType.equals("SJF")){
					if(p.size() >= 2){
						currentBT = p.get(count-1).getBT() - (time - p.get(count-1).getStartExTime());
						//System.out.println(currentBT + " : " + p.get(count).getBT());
						//System.out.println((currentBT==0) + "-----" + "preB: " +p.get(count-1).getBT() + "-----" + p.get(count).getBT() );
					}
					result = p.size() > 1 && (currentBT > p.get(count).getBT() || (currentBT == 0 && p.get(count-1).getBT() > p.get(count).getBT()));
					
				}
				else
					result = p.size() > 1 && p.get(count-1).getPriority() > p.get(count).getPriority();
				if(result){
					p.get(count).setStartExTime(time);
					process.add(new Process(p.get(count-1)));
					process.get(process.size() - 1).setBT(cumulative - p.get(count).getAT());
					process.get(process.size() - 1).setAT(p.get(count).getBT() + p.get(count).getStartExTime());
					//System.out.println(process.get(process.size() - 1).getPName() + ": " + process.get(process.size() - 1).getBT());
					cumulative -= process.get(process.size() - 1).getBT();
					if(process.get(process.size() - 1).getBT() == 0)
						process.remove(process.size() - 1);
					cumulative += p.get(count).getBT();
				}
				else if(p.size() > 1 && p.get(count).getAT() < p.get(count-1).getEndExTime()){
					//System.out.println(p.get(count).getPName());
					process.add(new Process(p.get(count)));
					process.get(process.size() - 1).setAT(time+1);
					p.remove(count);
					count--;
				}else{
					cumulative += p.get(count).getBT();
				}
					
				
				p.get(count).setEndExTime(p.get(count).getStartExTime() + p.get(count).getBT());
				//System.out.println(p.get(count).getPName() + " Endtime "+ p.get(count).getEndExTime());
				arrived.remove(0);
				count++;
				
			}
			time++;
		}while(time < maxTime);
		
		for(Process i: p)
			System.out.print("    " + i.getPName() + "\t"); 
		
		System.out.println();
		for(int i = 0; i < p.size(); i++){
			System.out.print(p.get(i).getStartExTime() + "\t");
		} 
		System.out.println(maxTime);
		
		double avgTurnTime = turnAroundTime(pro, p);
		double avgWaitTime = waitingTime(pro);
		
		printTable(pro);
		
		System.out.println("Avg = " + df.format(avgTurnTime));
		System.out.println("AvgWait = " + df.format(avgWaitTime));
	}
	
	
	
	public void nPreemptive(Process[] p, String nPType){
		Process[] nP = new Process[p.length];
		ArrayList<Process> arrived = new ArrayList<>(); 
		ArrayList<Process> process = new ArrayList<>(Arrays.asList(p));
		count = 0; time = 0; int maxTime = 0, cumulative = 0, minAT = 100;
		for(Process i:p){
			maxTime += i.getBT();
			if(minAT > i.getAT())
				minAT = i.getAT();
		}
		maxTime += minAT;
		cumulative += minAT;
		

		do{
			for(int j = 0; j < process.size(); j++){
				if(process.get(j).getAT() == time){
					arrived.add(process.get(j));
				}
			}
			if(arrived.size() > 1){
				if(nPType.equals("Priority"))
					Collections.sort(arrived, new ProcessComparator(new ProcessPComparator(), new ProcessBTComparator()));
				else
					Collections.sort(arrived, new ProcessComparator(new ProcessBTComparator()));
			}
			if((time == cumulative || time > cumulative) && arrived.size() >= 1){
				nP[count] = arrived.get(0);
				process.remove(nP[count]);
				nP[count].setStartExTime(time);
				arrived.remove(0);
				cumulative += nP[count].getBT();
				nP[count].setEndExTime(nP[count].getStartExTime() + nP[count].getBT());
				count++;
			}
			time++;
		}while(time < maxTime);
		
		for(Process i: nP)
			System.out.print("    " + i.getPName() + "\t"); 
		
		System.out.println();
		for(int i = 0; i < nP.length; i++){
			System.out.print(nP[i].getStartExTime() + "\t");
		} 
		System.out.println(maxTime);
		
		double avgTurnTime = turnAroundTime(p, new ArrayList<Process>(Arrays.asList(nP)));
		double avgWaitTime = waitingTime(p);
		
		printTable(p);
		
		System.out.println("Avg = " + df.format(avgTurnTime));
		System.out.println("AvgWait = " + df.format(avgWaitTime));
	}
	
	public double turnAroundTime(Process[] p, ArrayList<Process> process){
		for(int i = 0; i < p.length; i++){
			for(int j = 0; j < process.size(); j++){
				if(p[i].getPName().equals(process.get(j).getPName())){
					p[i].setTurnAround(process.get(j).getEndExTime() - p[i].getAT());
					p[i].setFinishTime(process.get(j).getEndExTime());
					//System.out.println(p[i].getPName() + ": " + process.get(j).getEndExTime() + " - " + p[i].getAT() + "= " + p[i].getTurnAround() + " or " + ta);
				}
			}
		}
		int total = 0;
		for(Process i:p){
			total += i.getTurnAround();
		}
		
		return (double)total/p.length;
	}
	
	public double waitingTime(Process[] p){
		int total = 0;
		for(Process i: p){
			i.setWaitTime(i.getTurnAround() - i.getBT());
			total += i.getWaitTime();
		}
		
		return (double)total/p.length;
	}
	
	public void printTable(Process[] p){
		System.out.println("---------------------------------------------------------------------------------------------------------");
		System.out.println("| PROCESS \t| ARRIVAL TIME \t| BURST TIME \t| FINISH TIME \t| TURNAROUND TIME \t| WAITING TIME \t|");
		System.out.println("---------------------------------------------------------------------------------------------------------");
		
		for(Process i: p){
			System.out.println(i.tostring());
			System.out.println("---------------------------------------------------------------------------------------------------------");
		}
	}
}