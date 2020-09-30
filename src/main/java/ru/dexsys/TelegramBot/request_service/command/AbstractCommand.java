package ru.dexsys.TelegramBot.request_service.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dexsys.TelegramBot.model.SavedUser;
import ru.dexsys.TelegramBot.model.SavedUserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractCommand extends BotCommand {
    private final Command command;
    protected final SavedUserService savedUserService;

    public AbstractCommand(Command command, SavedUserService savedUserService) {
        super(command.getName(), command.getDescription());
        this.command = command;
        this.savedUserService = savedUserService;
    }

    public void execute(AbsSender sender, SendMessage message, User user) {
        saveUser(user, message.getChatId());
        log.info("User #" + user.getId() + " use command '/" + command.getName() + "'");
        try {
            sender.execute(message);
            log.info("SUCCESS    -> User #" + user.getUserName() + " execute command '/" + command.getName() + "'");
        } catch (TelegramApiException e) {
            log.error("EXCEPTION -> User #" + user.getUserName() + " can't execute command '/" + command.getName() + "'", e);
        }
    }

    protected List<List<InlineKeyboardButton>> createKeyboard(
            List<String> elements, String botCommand, int width, int length
    ) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                int elemNumber = j + i * length;
                if (elemNumber < elements.size()) {
                    String elementValue = elements.get(elemNumber);
                    row.add(new InlineKeyboardButton(elementValue)
                            .setCallbackData(botCommand + " " + elementValue)
                    );
                }
            }
            rows.add(row);
        }
        return rows;
    }

    private void saveUser(User user, String chatId) {
        if (!savedUserService.hasUser(user.getUserName())) {
            savedUserService.addUser(new SavedUser(Long.parseLong(chatId), user.getUserName()));
            log.info("User #{} was saved", user.getUserName());
        }
    }
}
