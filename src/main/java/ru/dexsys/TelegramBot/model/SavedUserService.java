package ru.dexsys.TelegramBot.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class SavedUserService {
    private List<SavedUser> savedUsers = new ArrayList<>();

    public boolean addUser(SavedUser savedUser) {
        return savedUsers.add(savedUser);
    }

    public void setBirthday(@NonNull SavedUser savedUser, LocalDate birthday) {
        savedUsers.stream()
                .filter(user -> user.getUserName().equals(savedUser.getUserName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no saved user: " + savedUser.getUserName()))
                .setBirthDate(birthday);
    }

    public SavedUser getUser(String userName) {
        return savedUsers.stream()
                .filter(user -> userName.equals(user.getUserName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("There is no saved user: " + userName));
    }
}
