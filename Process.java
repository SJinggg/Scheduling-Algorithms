
public class Process{
	private String processName;
	private int arrivalTime, burstTime, priority, startExTime, endExTime, turnAround, waitTime, finishTime;
	private static int processNum = 0;
	
	public Process(){}
	
	public Process(int aT, int bT, int p){
		processName = "P" + processNum++;
		arrivalTime = aT;
		burstTime = bT;
		priority = p;
	}
	
	public Process(Process p){
		this.processName = p.getPName();
		this.arrivalTime = p.getAT();
		this.burstTime = p.getBT();
		this.priority = p.getPriority();
	}
	
	public String getPName(){
		return processName;
	}
	
	public int getAT(){
		return arrivalTime;
	}
	
	public void setAT(int arrivalTime){
		this.arrivalTime = arrivalTime;
	}
	
	public int getBT(){
		return burstTime;
	}
	
	public void setBT(int burstTime){
		this.burstTime = burstTime;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public int getStartExTime(){
		return startExTime;
	}
	
	public void setStartExTime(int startExTime){
		this.startExTime = startExTime;
	}
	
	public int getEndExTime(){
		return endExTime;
	}
	
	public void setEndExTime(int endExTime){
		this.endExTime = endExTime;
	}
	
	public int getTurnAround(){
		return turnAround;
	}
	
	public void setTurnAround(int turnAround){
		this.turnAround = turnAround;
	}
	
	public int getWaitTime(){
		return waitTime;
	}
	
	public void setWaitTime(int waitTime){
		this.waitTime = waitTime;
	}
	
	public int getFinishTime(){
		return finishTime;
	}
	
	public void setFinishTime(int finishTime){
		this.finishTime = finishTime;
	}
	
	public String tostring(){
		return "| " + processName + "\t\t| " + arrivalTime + "\t\t| " + burstTime +  "\t\t| " + finishTime + "\t\t| " + turnAround + "\t\t\t| " + waitTime + "\t\t| ";
	}
	
	@Override
	public String toString(){
		return "| " + processName + "\t\t| " + arrivalTime + "\t\t| " + burstTime +  "\t\t| " + priority + "\t\t|";
	}

}

