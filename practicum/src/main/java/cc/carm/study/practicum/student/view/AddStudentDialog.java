package cc.carm.study.practicum.student.view;

import cc.carm.study.practicum.student.data.Student;

import javax.swing.*;
import java.awt.*;

public class AddStudentDialog extends JDialog {

    private JTextField idField, nameField, ageField, addressField;
    private Student newStudent = null;

    public AddStudentDialog(Frame owner) {
        super(owner, "添加学生", true);
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("学号:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("姓名:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("年龄:"));
        ageField = new JTextField();
        formPanel.add(ageField);

        formPanel.add(new JLabel("地址:"));
        addressField = new JTextField();
        formPanel.add(addressField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("确定");
        okButton.addActionListener(e -> onOK());
        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> onCancel());

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onOK() {
        // Basic validation
        if (idField.getText().isEmpty() || nameField.getText().isEmpty() || ageField.getText().isEmpty() || addressField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String id = idField.getText();
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String address = addressField.getText();
            newStudent = new Student(id, name, age, address);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "年龄必须是有效的整数！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        newStudent = null;
        dispose();
    }

    public Student getNewStudent() {
        return newStudent;
    }
}

