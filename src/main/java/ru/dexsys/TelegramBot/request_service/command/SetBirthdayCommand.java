package ru.dexsys.TelegramBot.request_service.command;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.K;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.dexsys.TelegramBot.model.SavedUser;
import ru.dexsys.TelegramBot.model.SavedUserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class SetBirthdayCommand extends AbstractCommand {
    public SetBirthdayCommand(SavedUserService savedUserService) {
        super(Command.SAVE_BIRTHDAY, savedUserService);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = user.getUserName();
        SavedUser savedUser = savedUserService.getSavedUsers()
                .stream()
                .filter(it -> userName.equals(it.getUserName()))
                .findFirst()
                .orElseGet(() -> {
                            SavedUser newUser = new SavedUser(chat.getId(), userName);
                            savedUserService.addUser(newUser);
                            return newUser;
                        }
                );
        SendMessage message = new SendMessage().setChatId(chat.getId());
        message.setReplyMarkup(new InlineKeyboardMarkup().setKeyboard(
                List.of(
                        Stream.iterate(1, n -> n + 1)
                                .limit(LocalDate.now().lengthOfMonth())
                                .map(Object::toString)
                                .map(InlineKeyboardButton::new)
                                .collect(Collectors.toList())
                )
        ));

        log.info("Send buttons to User #{}", userName);
        try {
            message.setText("Chose the day of your birthday!");
        } catch (Exception e) {
            log.error("Problems with sending buttons...", e);
            message.setText("Oops... We have some problems with your birthday.\nPlease, chose correct date");
        }
        execute(absSender, message, user);
    }
}
