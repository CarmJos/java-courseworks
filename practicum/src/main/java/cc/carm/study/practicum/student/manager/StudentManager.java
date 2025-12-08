package cc.carm.study.practicum.student.manager;

import cc.carm.study.practicum.student.data.Student;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    protected final List<Student> students = new ArrayList<>();

    @NotNull
    public @UnmodifiableView List<Student> list() {
        return List.copyOf(students);
    }

    public void add(@NotNull Student student) {
        students.add(student);
    }

    public void remove(@NotNull Student student) {
        students.remove(student);
    }

    public Student remove(@NotNull String id) {
        for (Student student : students) {
            if (student.id().equals(id)) {
                students.remove(student);
                return student;
            }
        }
        return null;
    }

    public Student find(@NotNull String id) {
        for (Student student : students) {
            if (student.id().equals(id)) {
                return student;
            }
        }
        return null;
    }

}
