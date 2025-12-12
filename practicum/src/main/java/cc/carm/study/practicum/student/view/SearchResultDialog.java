package cc.carm.study.practicum.student.view;

import cc.carm.study.practicum.student.data.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SearchResultDialog extends JDialog {

    public SearchResultDialog(Frame owner, String title, List<Student> students) {
        super(owner, title, true);
        initComponents(students);
        pack();
        setSize(600, 400);
        setLocationRelativeTo(owner);
    }

    private void initComponents(List<Student> students) {
        setLayout(new BorderLayout());

        String[] columnNames = {"学号", "姓名", "年龄", "地址"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);

        if (students != null) {
            for (Student student : students) {
                tableModel.addRow(new Object[]{student.id(), student.name(), student.age(), student.address()});
            }
        }

        add(new JScrollPane(resultTable), BorderLayout.CENTER);

        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}

