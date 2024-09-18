package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserDTO;
import com.ecommerce.user.model.User;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.util.MapperUtil;
import com.ecommerce.user.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final MapperUtil mapperUtil;

    public UserDTO retrieveUserById(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found exception")
        );
        return this.mapperUtil.map(user, UserDTO.class);
    }

    public List<UserDTO> retrieveAllUsers() {
        List<User> users = this.userRepository.findAll();
        return this.mapperUtil.map(users, UserDTO.class);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = this.userRepository.findByMail(userDTO.getMail()).orElse(null);
        if(user == null) {
            User mappedUser = this.mapperUtil.map(userDTO, User.class);
            mappedUser.setPassword(PasswordUtil.hashPassword(userDTO.getPassword()));
            User savedUser = this.userRepository.save(mappedUser);
            return this.mapperUtil.map(savedUser, UserDTO.class);
        }
        throw new RuntimeException("User already exist!");
    }

    public UserDTO updateUser(String userId, UserDTO userDTO) {
        User userFromDb = this.userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found exception")
        );

        boolean hasChanged = mergeUserData(userDTO, userFromDb);
        if(hasChanged) {
            User user = this.mapperUtil.map(userDTO, User.class);
            this.userRepository.save(user);
            return userDTO;
        }

        return this.mapperUtil.map(userFromDb, UserDTO.class);
    }

    public void deleteUser(String userId) {
        this.userRepository.deleteById(userId);
    }

    private static boolean mergeUserData(UserDTO userDTO, User userFromDb) {
        boolean userChanged = false;

        if(modifying(userDTO.getUsername(), userFromDb.getUsername())) {
            userChanged = true;
            userFromDb.setUsername(userDTO.getUsername());
        }

        return userChanged;
    }

    private static <T> boolean modifying(T newVal, T oldVal) {
        return newVal != null && !Objects.equals(newVal, oldVal);
    }

    public Boolean validateUser(String mail, String password) {
        User user = this.userRepository.findByMail(mail).orElse(null);
        if(user != null) {
            return PasswordUtil.verifyPassword(password, user.getPassword());
        }
        return null;
    }
}
