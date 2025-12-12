package cc.carm.study.practicum.student.view;

import cc.carm.study.practicum.student.Main;
import cc.carm.study.practicum.student.data.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainView extends JFrame {

    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton fuzzySearchButton;
    private JButton idSearchButton;

    public MainView() {
        setTitle("学生信息管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initComponents();
        loadStudentData();
    }

    private void initComponents() {
        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("设定");
        JMenuItem logoutItem = new JMenuItem("登出");
        logoutItem.addActionListener(e -> {
            dispose();
            Main.main(null); // Restart the application
        });
        JMenuItem exitItem = new JMenuItem("退出");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(logoutItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Table
        String[] columnNames = {"学号", "姓名", "年龄", "地址"};
        tableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel actionButtonPanel = new JPanel();
        addButton = new JButton("添加");
        editButton = new JButton("修改");
        deleteButton = new JButton("删除");
        actionButtonPanel.add(addButton);
        actionButtonPanel.add(editButton);
        actionButtonPanel.add(deleteButton);

        JPanel searchButtonPanel = new JPanel();
        fuzzySearchButton = new JButton("模糊查询");
        idSearchButton = new JButton("学号查询");
        searchButtonPanel.add(fuzzySearchButton);
        searchButtonPanel.add(idSearchButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(actionButtonPanel, BorderLayout.WEST);
        southPanel.add(searchButtonPanel, BorderLayout.EAST);

        add(southPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        fuzzySearchButton.addActionListener(e -> fuzzySearchStudent());
        idSearchButton.addActionListener(e -> idSearchStudent());
    }

    private void loadStudentData() {
        tableModel.setRowCount(0); // Clear existing data
        List<Student> students = Main.studentManager.list();
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.id(), student.name(), student.age(), student.address()});
        }
    }

    private void addStudent() {
        AddStudentDialog dialog = new AddStudentDialog(this);
        dialog.setVisible(true);
        Student newStudent = dialog.getNewStudent();
        if (newStudent != null) {


            Main.studentManager.update(newStudent);
            loadStudentData();
            JOptionPane.showMessageDialog(this, "学生添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            String studentId = (String) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            int age = (int) tableModel.getValueAt(selectedRow, 2);
            String address = (String) tableModel.getValueAt(selectedRow, 3);
            Student selectedStudent = new Student(studentId, name, age, address);

            EditStudentDialog dialog = new EditStudentDialog(this, selectedStudent);
            dialog.setVisible(true);

            Student updatedStudent = dialog.getUpdatedStudent();
            if (updatedStudent != null) {
                Main.studentManager.update(updatedStudent);
                loadStudentData();
                JOptionPane.showMessageDialog(this, "学生信息修改成功！", "成功", JOptionPane.INFORMATION_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择要修改的学生！", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow >= 0) {
            String studentId = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "您确定要删除学号为 " + studentId + " 的学生吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Main.studentManager.remove(studentId);
                loadStudentData();
                JOptionPane.showMessageDialog(this, "学生删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);

            }
        } else {
            JOptionPane.showMessageDialog(this, "请先选择要删除的学生！", "提示", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void fuzzySearchStudent() {
        String keyword = JOptionPane.showInputDialog(this, "请输入要搜索的关键字：", "模糊查询", JOptionPane.PLAIN_MESSAGE);
        if (keyword != null && !keyword.isEmpty()) {
            List<Student> students = Main.studentManager.findByName(keyword);
            if (students.isEmpty()) {
                JOptionPane.showMessageDialog(this, "未找到匹配的学生。", "查询结果", JOptionPane.INFORMATION_MESSAGE);
            } else {
                SearchResultDialog dialog = new SearchResultDialog(this, "模糊查询结果", students);
                dialog.setVisible(true);
            }
        }
    }

    private void idSearchStudent() {
        String studentId = JOptionPane.showInputDialog(this, "请输入要查询的学生学号：", "学号查询", JOptionPane.PLAIN_MESSAGE);
        if (studentId != null && !studentId.isEmpty()) {
            Student student = Main.studentManager.find(studentId);
            if (student != null) {
                SearchResultDialog dialog = new SearchResultDialog(this, "学号查询结果", List.of(student));
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "未找到学号为 " + studentId + " 的学生。", "查询结果", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }
}
