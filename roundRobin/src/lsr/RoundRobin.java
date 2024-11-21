package lsr;

import javax.swing.*;
import java.util.*;

// RoundRobin调度算法
public class RoundRobin {
    public static void schedule(List<Process> processes, JTextArea outputArea, int timeQuantum) {
        Queue<Process> readyQueue = new LinkedList<>();// 就绪队列
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));// 按到达时间排序
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        Map<String, Integer> remainingBurst = new HashMap<>();// 剩余时间

        for (Process p : processes) {
            remainingBurst.put(p.PID, p.burstTime);// 剩余时间初始化
        }

        outputArea.append("\n轮转调度结果：\n");
        outputArea.append("进程号\t结束时间\t周转时间\t等待时间\n");// 输出表头

        while (!readyQueue.isEmpty() || !processes.isEmpty()) {
            // 加入已到达的进程
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {
                readyQueue.add(processes.remove(0));// 加入就绪队列
            }

            if (readyQueue.isEmpty()) {
                currentTime = processes.get(0).arrivalTime;// 进程到达时间
                continue;
            }

            Process process = readyQueue.poll();// 取出进程
            int executionTime = Math.min(remainingBurst.get(process.PID), timeQuantum);// 执行时间
            currentTime += executionTime;// 进程执行时间
            remainingBurst.put(process.PID, remainingBurst.get(process.PID) - executionTime);// 剩余时间更新

            if (remainingBurst.get(process.PID) == 0) {
                int turnaroundTime = currentTime - process.arrivalTime;// 周转时间
                int waitingTime = turnaroundTime - process.burstTime;// 等待时间
                totalTurnaroundTime += turnaroundTime;// 总周转时间
                totalWaitingTime += waitingTime;// 总等待时间
                // 输出结果
                outputArea.append(process.PID + "\t" + currentTime + "\t\t" + turnaroundTime + "\t\t" + waitingTime + "\n");
            } else {
                readyQueue.add(process);// 进程未执行完，重新加入就绪队列
            }
        }

        int numProcesses = remainingBurst.size();// 进程数
        outputArea.append("平均周转时间: " + (totalTurnaroundTime / (double) numProcesses) + "\n");// 输出平均周转时间
        outputArea.append("平均等待时间: " + (totalWaitingTime / (double) numProcesses) + "\n");// 输出平均等待时间
    }
}
