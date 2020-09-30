package ru.dexsys.TelegramBot.request_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class BotOptions extends DefaultBotOptions {
    @Value("${proxy.host}")
    String proxyHost;
    @Value("${proxy.port}")
    int proxyPort;
    @Value("${proxy.type}")
    ProxyType proxyType;

    @PostConstruct
    public void initializeOptions() {
        log.info("Initializing API context...");
        ApiContextInitializer.init();

        log.info("Configuring bot options...");
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost(proxyHost);
        botOptions.setProxyPort(proxyPort);
        botOptions.setProxyType(proxyType);

        botOptions.setMaxThreads(10);
    }
}
