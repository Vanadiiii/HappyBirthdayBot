package ru.dexsys.TelegramBot.request_service.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.dexsys.TelegramBot.model.SavedUser;
import ru.dexsys.TelegramBot.model.SavedUserService;

import java.util.stream.Collectors;

@Slf4j
@Component
public class PrintAllUsersCommand extends AbstractCommand {
    public PrintAllUsersCommand(SavedUserService savedUserService) {
        super(Command.PRINT, savedUserService);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Long chatId = chat.getId();

        String text = "There are all saved users:\n" +
                savedUserService.getSavedUsers()
                        .stream()
                        .map(SavedUser::toString)
                        .collect(Collectors.joining(",\n", "\t", ""));

        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(text);

        execute(absSender, message, user);
    }
}
