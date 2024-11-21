package lsr;

import javax.swing.*;
import java.util.*;

public class RoundRobin {
    public static void schedule(List<Process> processes, JTextArea outputArea, int timeQuantum) {
        Queue<Process> readyQueue = new LinkedList<>();
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        Map<String, Integer> remainingBurst = new HashMap<>();

        for (Process p : processes) {
            remainingBurst.put(p.PID, p.burstTime);
        }

        outputArea.append("\n轮转调度结果：\n");
        outputArea.append("进程号\t结束时间\t周转时间\t等待时间\n");

        while (!readyQueue.isEmpty() || !processes.isEmpty()) {
            // 加入已到达的进程
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            if (readyQueue.isEmpty()) {
                currentTime = processes.get(0).arrivalTime;
                continue;
            }

            Process process = readyQueue.poll();
            int executionTime = Math.min(remainingBurst.get(process.PID), timeQuantum);
            currentTime += executionTime;
            remainingBurst.put(process.PID, remainingBurst.get(process.PID) - executionTime);

            if (remainingBurst.get(process.PID) == 0) {
                int turnaroundTime = currentTime - process.arrivalTime;
                int waitingTime = turnaroundTime - process.burstTime;
                totalTurnaroundTime += turnaroundTime;
                totalWaitingTime += waitingTime;
                outputArea.append(process.PID + "\t" + currentTime + "\t\t" + turnaroundTime + "\t\t" + waitingTime + "\n");
            } else {
                readyQueue.add(process);
            }
        }

        int numProcesses = remainingBurst.size();
        outputArea.append("平均周转时间: " + (totalTurnaroundTime / (double) numProcesses) + "\n");
        outputArea.append("平均等待时间: " + (totalWaitingTime / (double) numProcesses) + "\n");
    }
}
