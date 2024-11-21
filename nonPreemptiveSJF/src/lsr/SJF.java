package lsr;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJF {
    public static void schedule(List<Process> processes, JTextArea outputArea) {
        // 保存初始的进程数量
        int numProcesses = processes.size();

        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // 按到达时间排序
        List<Process> readyQueue = new ArrayList<>();
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        outputArea.append("\nSJF 调度结果：\n");
        outputArea.append("进程号\t开始时间\t结束时间\t周转时间\t等待时间\n");

        // 执行调度
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // 加入已到达的进程
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            if (readyQueue.isEmpty()) {
                currentTime = processes.get(0).arrivalTime;
                continue;
            }

            // 按作业时间排序
            readyQueue.sort(Comparator.comparingInt(p -> p.burstTime));
            Process process = readyQueue.remove(0);

            int startTime = Math.max(currentTime, process.arrivalTime);
            int finishTime = startTime + process.burstTime;
            int turnaroundTime = finishTime - process.arrivalTime;
            int waitingTime = turnaroundTime - process.burstTime;

            currentTime = finishTime;
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;

            outputArea.append(process.PID + "\t" + startTime + "\t\t" + finishTime + "\t\t" + turnaroundTime + "\t\t" + waitingTime + "\n");
        }

        // 使用保存的进程数计算平均周转时间和等待时间
        outputArea.append("平均周转时间: " + (totalTurnaroundTime / (double) numProcesses) + "\n");
        outputArea.append("平均等待时间: " + (totalWaitingTime / (double) numProcesses) + "\n");
    }
}
