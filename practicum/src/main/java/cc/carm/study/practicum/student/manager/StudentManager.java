package cc.carm.study.practicum.student.manager;

import cc.carm.study.practicum.student.data.Student;
import cc.carm.study.practicum.student.database.DataTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentManager {

    @UnmodifiableView
    public @NotNull List<Student> list() {
        return DataTables.STUDENTS.createQuery().build().execute(query -> {
            List<Student> studentList = new ArrayList<>();
            ResultSet rs = query.getResultSet();
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                Student student = new Student(id, name, age, address);
                studentList.add(student);
            }
            return studentList;
        }, new ArrayList<>(), null);

    }

    public void add(@NotNull Student student) {
        DataTables.STUDENTS.createInsert()
                .setColumnNames("id", "name", "age", "address", "create_time")
                .setParams(student.id(), student.name(), student.age(), student.address(), new Date())
                .execute(null);
    }

    public boolean remove(@NotNull Student student) {
        return remove(student.id());
    }

    public boolean remove(@NotNull String id) {
        return DataTables.STUDENTS.createDelete()
                .addCondition("id", id)
                .setLimit(1).build()
                .execute((i -> i > 0), false, null);
    }

    public Student find(@NotNull String id) {
        return DataTables.STUDENTS.createQuery()
                .addCondition("id", id)
                .setLimit(1).build()
                .execute(query -> {
                    ResultSet rs = query.getResultSet();
                    if (rs.next()) {
                        String dataID = rs.getString("id");
                        String name = rs.getString("name");
                        int age = rs.getInt("age");
                        String address = rs.getString("address");
                        return new Student(dataID, name, age, address);
                    }
                    return null;
                }, null, null);
    }

}
