package lsr;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// 优先级调度算法
public class PriorityScheduling {
    public static void schedule(List<Process> processes, JTextArea outputArea) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // 按到达时间排序
        List<Process> readyQueue = new ArrayList<>();// 就绪队列
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int numScheduledProcesses = 0;  // 记录已调度的进程数

        outputArea.append("\n优先级调度结果：\n");
        outputArea.append("进程号\t开始时间\t结束时间\t周转时间\t等待时间\n");// 输出表头

        while (!processes.isEmpty() || !readyQueue.isEmpty()) {
            // 加入已到达的进程
            while (!processes.isEmpty() && processes.get(0).arrivalTime <= currentTime) {// 按到达时间排序
                readyQueue.add(processes.remove(0));// 加入就绪队列
            }

            // 如果队列为空且进程也未到达，跳过循环
            if (readyQueue.isEmpty()) {
                if (!processes.isEmpty()) {
                    currentTime = processes.get(0).arrivalTime;// 进程到达时间即为下一轮循环时间
                }
                continue;
            }

            // 按优先级排序（较低的优先级值表示较高优先级）
            readyQueue.sort(Comparator.comparingInt(p -> p.priority));// 按优先级排序
            Process process = readyQueue.remove(0);// 取出第一个进程

            int startTime = Math.max(currentTime, process.arrivalTime);// 开始时间取两者的最大值
            int finishTime = startTime + process.burstTime;// 结束时间为开始时间加进程执行时间
            int turnaroundTime = finishTime - process.arrivalTime;//     周转时间为结束时间减去到达时间
            int waitingTime = turnaroundTime - process.burstTime;// 等待时间为周转时间减去执行时间

            currentTime = finishTime;// 时间更新为结束时间
            totalTurnaroundTime += turnaroundTime;// 周转时间累加
            totalWaitingTime += waitingTime;// 等待时间累加
            numScheduledProcesses++;//     已调度进程数+1

            // 输出进程信息
            outputArea.append(process.PID + "\t" + startTime + "\t\t" + finishTime + "\t\t" + turnaroundTime + "\t\t" + waitingTime + "\n");
        }

        // 计算平均周转时间和平均等待时间
        if (numScheduledProcesses > 0) {
            outputArea.append("平均周转时间: " + (totalTurnaroundTime / (double) numScheduledProcesses) + "\n");// 周转时间除以已调度进程数
            outputArea.append("平均等待时间: " + (totalWaitingTime / (double) numScheduledProcesses) + "\n");// 等待时间除以已调度进程数
        } else {
            outputArea.append("无进程可调度。\n");// 无进程可调度
        }
    }
}
