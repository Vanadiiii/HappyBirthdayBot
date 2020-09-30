package ru.dexsys.TelegramBot.request_service.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.dexsys.TelegramBot.model.SavedUser;
import ru.dexsys.TelegramBot.model.SavedUserService;

import java.util.EnumSet;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HelpCommand extends AbstractCommand {
    public HelpCommand(SavedUserService savedUserService) {
        super(Command.HELP, savedUserService);
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String text = EnumSet.allOf(Command.class)
                .stream()
                .map(Enum::name)
                .map(name -> "/" + name.toLowerCase())
                .map(StringBuilder::new)
                .collect(Collectors.joining("\n", "<b>Available commands:</b>\n", ""));

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(text);

        execute(absSender, helpMessage, user);
    }
}
