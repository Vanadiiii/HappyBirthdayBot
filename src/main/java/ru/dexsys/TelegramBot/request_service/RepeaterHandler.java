package ru.dexsys.TelegramBot.request_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class RepeaterHandler {
    @Value("${proxy.host}")
    String proxyHost;
    @Value("${proxy.port}")
    int proxyPort;

    private final HappyBirthdayBot bot;
    private final TelegramBotsApi botsApi = new TelegramBotsApi();


    @PostConstruct
    public void initializeBot() {
        System.getProperty("proxySet", "true");
        System.getProperty("socksProxyHost", proxyHost);
        System.getProperty("socksProxyPort", String.valueOf(proxyPort));

        try {
            ApiContextInitializer.init();
            log.info("Registering " + bot.getBotUsername() + "...");
            botsApi.registerBot(bot);
            log.info(bot.getBotUsername() + " is ready for work!");
        } catch (TelegramApiRequestException e) {
            log.error("Error while initializing bot!", e);
        }
    }
}
