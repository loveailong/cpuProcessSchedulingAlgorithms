package lsr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
        JFrame frame = new JFrame("CPU 调度算法");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        // 主面板
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10)); // 设置面板布局，增加间距
        frame.add(mainPanel);

        // 输入区域
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5)); // 设置行列间距
        JLabel numProcessesLabel = new JLabel("进程数：");
        JTextField numProcessesField = new JTextField();
        JLabel timeQuantumLabel = new JLabel("时间片（RR）：");
        JTextField timeQuantumField = new JTextField();
        JButton generateButton = new JButton("生成进程");

        inputPanel.add(numProcessesLabel);
        inputPanel.add(numProcessesField);
        inputPanel.add(timeQuantumLabel);
        inputPanel.add(timeQuantumField);
        inputPanel.add(new JLabel()); // 占位空白
        inputPanel.add(generateButton);

        // 输出区域
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16)); // 设置等宽字体
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // 调度算法选择和运行按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        String[] algorithms = {"FCFS", "SJF", "优先级调度", "轮转调度"};
        JComboBox<String> algorithmComboBox = new JComboBox<>(algorithms);
        JButton runButton = new JButton("运行调度");
        buttonPanel.add(algorithmComboBox);
        buttonPanel.add(runButton);

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
                    int numProcesses = Integer.parseInt(numProcessesField.getText());
                    List<Process> processes = ProcessGenerator.generateProcesses(numProcesses);
                    outputArea.setText("生成的进程：\n");
                    for (Process process : processes) {
                        outputArea.append(process.PID + " 到达时间: " + process.arrivalTime + " 执行时间: " + process.burstTime + " 优先级: " + process.priority + "\n");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "请输入有效的进程数。");
                }
            }
        });

        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numProcesses = Integer.parseInt(numProcessesField.getText());
                    List<Process> processes = ProcessGenerator.generateProcesses(numProcesses);
                    String selectedAlgorithm = (String) algorithmComboBox.getSelectedItem();

                    switch (selectedAlgorithm) {
                        case "FCFS":
                            FCFS.schedule(processes, outputArea);
                            break;
                        case "SJF":
                            SJF.schedule(processes, outputArea);
                            break;
                        case "优先级调度":
                            PriorityScheduling.schedule(processes, outputArea);
                            break;
                        case "轮转调度":
                            int timeQuantum = Integer.parseInt(timeQuantumField.getText());
                            RoundRobin.schedule(processes, outputArea, timeQuantum);
                            break;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "请输入有效的进程数或时间片。");
                }
            }
        });

        // 设置窗口居中
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
