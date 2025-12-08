package cc.carm.study.practicum.student.manager;

import cc.carm.study.practicum.student.data.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    protected final Map<String, User> registry = new ConcurrentHashMap<>();

    public @NotNull @UnmodifiableView Set<User> list() {
        return Set.copyOf(registry.values());
    }

    public void register(User user) {
        registry.put(user.username(), user);
    }

    public void unregister(User user) {
        registry.remove(user.username());
    }

    public void unregister(String username) {
        registry.remove(username);
    }

    public @Nullable User get(@NotNull String username) {
        return registry.get(username);
    }

}
