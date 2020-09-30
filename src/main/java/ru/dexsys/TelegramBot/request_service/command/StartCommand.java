package ru.dexsys.TelegramBot.request_service.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.dexsys.TelegramBot.model.SavedUser;
import ru.dexsys.TelegramBot.model.SavedUserService;

@Slf4j
@Component
public class StartCommand extends AbstractCommand {
    public StartCommand(SavedUserService savedUserService) {
        super(Command.START, savedUserService);
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Long chatId = chat.getId();
        String userName = user.getUserName();
        savedUserService.addUser(new SavedUser(chatId, userName));

        String text = "Hello, " + user.getFirstName() + "!\n"
                + "Welcome to HappyBirthdayBot!\n";


        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(text);

        log.info("User #{} was saved", userName);

        execute(absSender, message, user);
    }
}
