package lsr;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PriorityScheduling {
    public static void schedule(List<Process> processes, JTextArea outputArea) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // 按到达时间排序
        List<Process> readyQueue = new ArrayList<>();
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int numScheduledProcesses = 0;  // 记录已调度的进程数

        outputArea.append("\n优先级调度结果：\n");
        outputArea.append("进程号\t开始时间\t结束时间\t周转时间\t等待时间\n");

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // 加入已到达的进程
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));
            }

            // 如果队列为空且进程也未到达，跳过循环
            if (readyQueue.isEmpty()) {
                if (!processes.isEmpty()) {
                    currentTime = processes.get(0).arrivalTime;
                }
                continue;
            }

            // 按优先级排序（较低的优先级值表示较高优先级）
            readyQueue.sort(Comparator.comparingInt(p -> p.priority));
            Process process = readyQueue.remove(0);

            int startTime = Math.max(currentTime, process.arrivalTime);
            int finishTime = startTime + process.burstTime;
            int turnaroundTime = finishTime - process.arrivalTime;
            int waitingTime = turnaroundTime - process.burstTime;

            currentTime = finishTime;
            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;
            numScheduledProcesses++;

            outputArea.append(process.PID + "\t" + startTime + "\t\t" + finishTime + "\t\t" + turnaroundTime + "\t\t" + waitingTime + "\n");
        }

        // 计算平均周转时间和平均等待时间
        if (numScheduledProcesses > 0) {
            outputArea.append("平均周转时间: " + (totalTurnaroundTime / (double) numScheduledProcesses) + "\n");
            outputArea.append("平均等待时间: " + (totalWaitingTime / (double) numScheduledProcesses) + "\n");
        } else {
            outputArea.append("无进程可调度。\n");
        }
    }
}
