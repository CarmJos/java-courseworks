package cc.carm.study.practicum.student.view;

import cc.carm.study.practicum.student.data.Student;

import javax.swing.*;
import java.awt.*;

public class EditStudentDialog extends JDialog {

    private JTextField nameField, ageField, addressField;
    private Student updatedStudent = null;
    private final String studentId;

    public EditStudentDialog(Frame owner, Student student) {
        super(owner, "修改学生信息", true);
        this.studentId = student.id();
        initComponents(student);
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents(Student student) {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("学号:"));
        JTextField idField = new JTextField(student.id());
        idField.setEditable(false);
        formPanel.add(idField);

        formPanel.add(new JLabel("姓名:"));
        nameField = new JTextField(student.name());
        formPanel.add(nameField);

        formPanel.add(new JLabel("年龄:"));
        ageField = new JTextField(String.valueOf(student.age()));
        formPanel.add(ageField);

        formPanel.add(new JLabel("地址:"));
        addressField = new JTextField(student.address());
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
        if (nameField.getText().isEmpty() || ageField.getText().isEmpty() || addressField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "所有字段都不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String address = addressField.getText();
            updatedStudent = new Student(studentId, name, age, address);
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "年龄必须是有效的整数！", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        updatedStudent = null;
        dispose();
    }

    public Student getUpdatedStudent() {
        return updatedStudent;
    }
}

