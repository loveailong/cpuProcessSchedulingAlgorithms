package lsr;

// 进程类
public class Process {
    String PID;// process ID
    int arrivalTime;//展示到CPU的时刻
    int burstTime;//进程需要运行的时间
    int priority;//进程优先级

    // 构造函数
    Process(String PID, int arrivalTime, int burstTime, int priority) {
        this.PID = PID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}
