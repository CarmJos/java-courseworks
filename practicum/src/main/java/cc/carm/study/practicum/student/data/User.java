package cc.carm.study.practicum.student.data;

import cc.carm.study.practicum.student.utils.BCrypt;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record User(@NotNull String username, @NotNull String hashedPassword,
                   @NotNull String cid, @NotNull String phone) {

    public boolean validatePassword(@NotNull String plain) {
        return BCrypt.comparePassword(plain, this.hashedPassword);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

}
