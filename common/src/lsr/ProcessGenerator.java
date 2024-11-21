package lsr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessGenerator {
    public static List<Process> generateProcesses(int numProcesses) {
        List<Process> processes = new ArrayList<>();
        Random rand = new Random();

        for (int i = 1; i <= numProcesses; i++) {
            String PID = "P" + i;
            int arrivalTime = rand.nextInt(10); // 随机到达时间 0-9
            int burstTime = rand.nextInt(10) + 1; // 随机运行时间 1-10
            int priority = rand.nextInt(10) + 1; // 随机优先级 1-10
            processes.add(new Process(PID, arrivalTime, burstTime, priority));
        }
        return processes;
    }
}

