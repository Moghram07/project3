package com.example.project3.Service;

import com.example.project3.Model.User;
import com.example.project3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void registerUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user) {
        User u = userRepository.findUserById(id);
        if (u == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        u.setUsername(user.getUsername());
        u.setName(user.getName());
        u.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        u.setRole(user.getRole());
        userRepository.save(u);
    }

    public void deleteUser(Integer id) {
        User u = userRepository.findUserById(id);
        if (u == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        userRepository.delete(u);
    }

    public User getUserById(Integer id) {
        User u = userRepository.findUserById(id);
        if (u == null) {
            throw new IllegalArgumentException("User with id " + id + " not found");
        }
        return u;
    }

}
