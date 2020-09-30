package ru.dexsys.TelegramBot.request_service.command;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.dexsys.TelegramBot.model.SavedUserService;

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
        log.info("User #" + user.getId() + " use command '/" + command.getName() + "'");
        try {
            sender.execute(message);
            log.info("SUCCESS    -> User #" + user.getUserName() + " execute command '/" + command.getName() + "'");
        } catch (TelegramApiException e) {
            log.error("EXCEPTION -> User #" + user.getUserName() + " can't execute command '/" + command.getName() + "'");
        }
    }
}
