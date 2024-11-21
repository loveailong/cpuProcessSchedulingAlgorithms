package lsr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// 调度器GUI类
public class SchedulerGUI {
    // 设置全局字体方法
    public static void setUIFont(Font font) {
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TextField.font", font);
        UIManager.put("TextArea.font", font);
        UIManager.put("ComboBox.font", font);
        UIManager.put("Panel.font", font);
        UIManager.put("ScrollPane.font", font);
    }

    public static void main(String[] args) {
        // 设置全局字体为支持中文的字体
        setUIFont(new Font("Microsoft YaHei", Font.PLAIN, 16)); // 微软雅黑字体

        // 主窗口
        JFrame frame = new JFrame("CPU 调度算法");// 窗口标题
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗口时退出程序
        frame.setSize(600, 500);// 窗口大小

        // 主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10)); // 设置面板布局，增加间距
        frame.add(mainPanel);// 添加面板到窗口

        // 输入区域
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // 设置行列间距
        JLabel numProcessesLabel = new JLabel("进程数：");// 标签
        JTextField numProcessesField = new JTextField();
        JLabel timeQuantumLabel = new JLabel("时间片（RR）：");// 标签
        JTextField timeQuantumField = new JTextField();
        JButton generateButton = new JButton("生成进程");// 按钮

        inputPanel.add(numProcessesLabel);// 添加组件到面板
        inputPanel.add(numProcessesField);// 添加组件到面板
        inputPanel.add(timeQuantumLabel);// 添加组件到面板
        inputPanel.add(timeQuantumField);// 添加组件到面板
        inputPanel.add(new JLabel()); // 占位空白
        inputPanel.add(generateButton);// 添加组件到面板

        // 输出区域
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16)); // 设置等宽字体
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // 调度算法选择和运行按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        String[] algorithms = {"FCFS", "SJF", "优先级调度", "轮转调度"};// 调度算法
        JComboBox<String> algorithmComboBox = new JComboBox<>(algorithms);// 下拉框
        JButton runButton = new JButton("运行调度");
        buttonPanel.add(algorithmComboBox);// 添加组件到面板
        buttonPanel.add(runButton);// 添加组件到面板

        // 将各部分添加到主面板
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 设置边距
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 按钮事件处理
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numProcesses = Integer.parseInt(numProcessesField.getText());// 获取进程数
                    List<Process> processes = ProcessGenerator.generateProcesses(numProcesses);// 生成进程列表
                    outputArea.setText("生成的进程：\n");// 输出进程列表
                    for (Process process : processes) {
                        outputArea.append(process.PID + " 到达时间: " + process.arrivalTime + " 执行时间: " + process.burstTime + " 优先级: " + process.priority + "\n");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "请输入有效的进程数。");// 弹出错误提示框
                }
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// 运行按钮事件处理
                try {
                    int numProcesses = Integer.parseInt(numProcessesField.getText());// 获取进程数
                    List<Process> processes = ProcessGenerator.generateProcesses(numProcesses);// 生成进程列表
                    String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();// 获取选择的调度算法

                    switch (selectedAlgorithm) {
                        case "FCFS":
                            FCFS.schedule(processes, outputArea);// 调用 FCFS 调度算法
                            break;
                        case "SJF":
                            SJF.schedule(processes, outputArea);// 调用 SJF 调度算法
                            break;
                        case "优先级调度":
                            PriorityScheduling.schedule(processes, outputArea);// 调用优先级调度算法
                            break;
                        case "轮转调度":
                            int timeQuantum = Integer.parseInt(timeQuantumField.getText());// 获取时间片
                            RoundRobin.schedule(processes, outputArea, timeQuantum);// 调用轮转调度算法
                            break;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "请输入有效的进程数或时间片。");// 弹出错误提示框
                }
            }
        });

        // 设置窗口居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
