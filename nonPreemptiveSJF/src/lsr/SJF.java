package lsr;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// SJF 调度算法
public class SJF {
    public static void schedule(List<Process> processes, JTextArea outputArea) {
        // 保存初始的进程数量
        int numProcesses = processes.size();

        // 按到达时间排序
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // 按到达时间排序
        List<Process> readyQueue = new ArrayList<>();// 就绪队列
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        outputArea.append("\nSJF 调度结果：\n");// 输出调度算法名称
        outputArea.append("进程号\t开始时间\t结束时间\t周转时间\t等待时间\n");// 输出表头

        // 执行调度
        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // 加入已到达的进程
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));// 加入就绪队列
            }

            if (readyQueue.isEmpty()) {
                currentTime = processes.get(0).arrivalTime;//进程到达时间
                continue;
            }

            // 按作业时间排序
            readyQueue.sort(Comparator.comparingInt(p -> p.burstTime));
            Process process = readyQueue.remove(0);// 取出第一个进程

            int startTime = Math.max(currentTime, process.arrivalTime);// 开始时间
            int finishTime = startTime + process.burstTime;// 结束时间
            int turnaroundTime = finishTime - process.arrivalTime;// 周转时间
            int waitingTime = turnaroundTime - process.burstTime;// 等待时间

            currentTime = finishTime;// 更新当前时间
            totalTurnaroundTime += turnaroundTime;// 累计周转时间
            totalWaitingTime += waitingTime;//   累计等待时间

            // 输出进程信息
            outputArea.append(process.PID + "\t" + startTime + "\t\t" + finishTime + "\t\t" + turnaroundTime + "\t\t" + waitingTime + "\n");
        }

        // 使用保存的进程数计算平均周转时间和等待时间
        outputArea.append("平均周转时间: " + (totalTurnaroundTime / (double) numProcesses) + "\n");
        outputArea.append("平均等待时间: " + (totalWaitingTime / (double) numProcesses) + "\n");
    }
}
