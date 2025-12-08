package cc.carm.study.practicum.student.manager;

import cc.carm.study.practicum.student.data.User;
import cc.carm.study.practicum.student.database.DataTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.sql.ResultSet;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class UserManager {


    public @NotNull @UnmodifiableView Set<User> list() {
        return DataTables.USERS.createQuery().build().execute(query -> {
            Set<User> list = new HashSet<>();
            ResultSet rs = query.getResultSet();
            while (rs.next()) {
                String uname = rs.getString("username");
                String hashedPassword = rs.getString("hashed_password");
                String cid = rs.getString("cid");
                String phone = rs.getString("phone");
                list.add(new User(uname, hashedPassword, cid, phone));
            }
            return list;
        }, new HashSet<>(), null);
    }

    public void register(User user) {
        DataTables.USERS.createInsert()
                .setColumnNames("username", "hashed_password", "cid", "phone", "create_time")
                .setParams(user.username(), user.hashedPassword(), user.cid(), user.phone(), new Date())
                .execute(null);
    }

    public boolean unregister(User user) {
        return unregister(user.username());
    }

    public boolean unregister(String username) {
        return DataTables.USERS.createDelete()
                .addCondition("username", username)
                .setLimit(1).build()
                .execute((i -> i > 0), false, null);
    }

    public @Nullable User get(@NotNull String username) {
        return DataTables.USERS.createQuery()
                .addCondition("username", username)
                .setLimit(1).build()
                .execute(query -> {
                    ResultSet rs = query.getResultSet();
                    if (rs.next()) {
                        String uname = rs.getString("username");
                        String hashedPassword = rs.getString("hashed_password");
                        String cid = rs.getString("cid");
                        String phone = rs.getString("phone");
                        return new User(uname, hashedPassword, cid, phone);
                    }
                    return null;
                }, null, null);
    }

}
