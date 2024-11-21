package lsr;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;

class FCFS {
    public static void schedule(List<Process> processes, JTextArea outputArea) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // 按到达时间排序
        // 按照 FCFS 算法进行调度
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        outputArea.append("\nFCFS 调度结果：\n");
        outputArea.append("进程号\t开始时间\t结束时间\t周转时间\t等待时间\n");


        // 遍历所有进程
        for (Process process : processes) {// 遍历所有进程
            int startTime = Math.max(currentTime, process.arrivalTime);// 计算进程开始时间
            int finishTime = startTime + process.burstTime;// 计算进程结束时间
            int turnaroundTime = finishTime - process.arrivalTime;// 计算进程周转时间
            int waitingTime = turnaroundTime - process.burstTime;// 计算进程等待时间

            currentTime = finishTime;// 更新当前时间
            totalTurnaroundTime += turnaroundTime;// 累计周转时间
            totalWaitingTime += waitingTime;// 累计等待时间

            // 将调度结果追加到 outputArea 中
            outputArea.append(process.PID + "\t" + startTime + "\t\t" + finishTime + "\t\t" + turnaroundTime + "\t\t" + waitingTime + "\n");
        }

        // 计算平均周转时间和平均等待时间
        int numProcesses = processes.size();
        outputArea.append("平均周转时间: " + (totalTurnaroundTime / (double) numProcesses) + "\n");// 计算平均周转时间
        outputArea.append("平均等待时间: " + (totalWaitingTime / (double) numProcesses) + "\n");// 计算平均等待时间
    }
}
