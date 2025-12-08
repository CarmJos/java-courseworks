package cc.carm.study.practicum.student.data;

import java.util.Objects;

public record Student(
        String id,
        String name,
        int age,
        String address
) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student student)) return false;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
