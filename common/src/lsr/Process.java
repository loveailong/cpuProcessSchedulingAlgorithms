package lsr;


public class Process {
    String PID;
    int arrivalTime;
    int burstTime;
    int priority;

    Process(String PID, int arrivalTime, int burstTime, int priority) {
        this.PID = PID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}
