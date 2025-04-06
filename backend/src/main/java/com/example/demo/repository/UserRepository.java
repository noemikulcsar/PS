package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public User save(User user) {
        users.add(user);
        return user;
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public boolean deleteById(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    public User update(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                return user;
            }
        }
        return null;
    }
}
