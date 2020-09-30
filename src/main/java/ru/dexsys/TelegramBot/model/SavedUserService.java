package ru.dexsys.TelegramBot.model;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
@Slf4j
public class SavedUserService {
    private List<SavedUser> savedUsers = new ArrayList<>();

    public boolean addUser(SavedUser savedUser) {
        return savedUsers.add(savedUser);
    }

    public void setBirthMonth(@NonNull SavedUser savedUser, Month month) {
        savedUsers.stream()
                .filter(user -> user.getUserName().equals(savedUser.getUserName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no saved user: " + savedUser.getUserName()))
                .getBirthday()
                .setMonth(month);
        log.info("User #{} save their birth month - {}", savedUser.getUserName(), month);
    }

    public void setBirthDay(@NonNull SavedUser savedUser, int day) {
        savedUsers.stream()
                .filter(user -> user.getUserName().equals(savedUser.getUserName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no saved user: " + savedUser.getUserName()))
                .getBirthday()
                .setDay(day);
        log.info("User #{} save their birth day - {}", savedUser.getUserName(), day);
    }

    public SavedUser getUser(String userName) {
        return savedUsers.stream()
                .filter(user -> userName.equals(user.getUserName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no saved user: " + userName));
    }

    public boolean hasUser(String userName) {
        return savedUsers.stream()
                .anyMatch(user -> userName.equals(user.getUserName()));
    }
}
