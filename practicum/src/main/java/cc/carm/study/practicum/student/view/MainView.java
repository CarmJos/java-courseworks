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
    private JButton prevPageButton;
    private JButton nextPageButton;
    private JLabel pageInfoLabel;
    private int currentPage = 0;
    private int pageSize = 20; // 可变的每页大小；当为 0 时表示全部
    private JComboBox<String> pageSizeCombo;

    public MainView() {
        setTitle("学生信息管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        initComponents();
        loadStudentData();
    }

    private void initComponents() {
        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("文件");
        JMenuItem exportCsvItem = new JMenuItem("导出 CSV");
        exportCsvItem.addActionListener(e -> exportToCsv());
        fileMenu.add(exportCsvItem);
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
        JButton addButton = new JButton("添加");
        JButton editButton = new JButton("修改");
        JButton deleteButton = new JButton("删除");
        actionButtonPanel.add(addButton);
        actionButtonPanel.add(editButton);
        actionButtonPanel.add(deleteButton);

        JPanel searchButtonPanel = new JPanel();
        JButton fuzzySearchButton = new JButton("模糊查询");
        JButton idSearchButton = new JButton("学号查询");
        searchButtonPanel.add(fuzzySearchButton);
        searchButtonPanel.add(idSearchButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(actionButtonPanel, BorderLayout.WEST);
        southPanel.add(searchButtonPanel, BorderLayout.EAST);

        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // 每页大小选择
        JLabel pageSizeLabel = new JLabel("每页:");
        pageSizeCombo = new JComboBox<>(new String[]{"10", "20", "50", "100", "全部"});
        pageSizeCombo.setSelectedItem(String.valueOf(pageSize));
        pageSizeCombo.addActionListener(e -> {
            String sel = (String) pageSizeCombo.getSelectedItem();
            if ("全部".equals(sel)) {
                pageSize = 0; // 0 表示全部
            } else {
                try {
                    pageSize = Integer.parseInt(sel);
                } catch (NumberFormatException ex) {
                    pageSize = 20;
                }
            }
            currentPage = 0;
            loadStudentData();
        });

        prevPageButton = new JButton("上一页");
        nextPageButton = new JButton("下一页");
        pageInfoLabel = new JLabel();
        // add page size selector before paging controls
        navigationPanel.add(pageSizeLabel);
        navigationPanel.add(pageSizeCombo);
        navigationPanel.add(prevPageButton);
        navigationPanel.add(pageInfoLabel);
        navigationPanel.add(nextPageButton);

        southPanel.add(navigationPanel, BorderLayout.CENTER);

        add(southPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> addStudent());
        editButton.addActionListener(e -> editStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        fuzzySearchButton.addActionListener(e -> fuzzySearchStudent());
        idSearchButton.addActionListener(e -> idSearchStudent());
        prevPageButton.addActionListener(e -> prevPage());
        nextPageButton.addActionListener(e -> nextPage());
    }

    private void loadStudentData() {
        tableModel.setRowCount(0); // Clear existing data
        int totalStudents = Main.studentController.count();
        int totalPages;
        List<Student> students;
        if (pageSize == 0) { // 全部
            students = Main.studentController.list();
            totalPages = 1;
            currentPage = 0;
        } else {
            totalPages = (int) Math.ceil((double) totalStudents / pageSize);
            students = Main.studentController.list(currentPage * pageSize, pageSize);
        }
        for (Student student : students) {
            tableModel.addRow(new Object[]{student.id(), student.name(), student.age(), student.address()});
        }

        pageInfoLabel.setText("第 " + (currentPage + 1) + " / " + totalPages + " 页 (共 " + totalStudents + " 条记录)");
        boolean pagingEnabled = (pageSize != 0 && totalPages > 1);
        prevPageButton.setEnabled(pagingEnabled && currentPage > 0);
        nextPageButton.setEnabled(pagingEnabled && currentPage < totalPages - 1);
    }

    private void prevPage() {
        if (currentPage > 0) {
            currentPage--;
            loadStudentData();
        }
    }

    private void nextPage() {
        int totalStudents = Main.studentController.count();
        int totalPages = pageSize == 0 ? 1 : (int) Math.ceil((double) totalStudents / pageSize);
        if (pageSize != 0 && currentPage < totalPages - 1) {
            currentPage++;
            loadStudentData();
        }
    }

    private void exportToCsv() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("导出为 CSV");
        fileChooser.setSelectedFile(new java.io.File("students.csv"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            try (java.io.FileWriter writer = new java.io.FileWriter(fileToSave)) {
                // Write header
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    writer.append(tableModel.getColumnName(i));
                    if (i < tableModel.getColumnCount() - 1) {
                        writer.append(',');
                    }
                }
                writer.append('\n');

                // Write data
                List<Student> allStudents = Main.studentController.list();
                for (Student student : allStudents) {
                    writer.append(student.id()).append(',');
                    writer.append(student.name()).append(',');
                    writer.append(String.valueOf(student.age())).append(',');
                    writer.append(student.address()).append('\n');
                }

                JOptionPane.showMessageDialog(this, "成功导出到 " + fileToSave.getAbsolutePath(), "导出成功", JOptionPane.INFORMATION_MESSAGE);
            } catch (java.io.IOException e) {
                JOptionPane.showMessageDialog(this, "导出失败: " + e.getMessage(), "导出错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addStudent() {
        AddStudentDialog dialog = new AddStudentDialog(this);
        dialog.setVisible(true);
        Student newStudent = dialog.getNewStudent();
        if (newStudent != null) {


            Main.studentController.update(newStudent);
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
                Main.studentController.update(updatedStudent);
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
                Main.studentController.remove(studentId);
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
            List<Student> students = Main.studentController.findByName(keyword);
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
            Student student = Main.studentController.find(studentId);
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
