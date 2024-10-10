package eu.senla.telegramservice;

import eu.senla.telegramservice.service.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TelegramServiceApplication {

    private static TelegramBot telegramBot;

    @Autowired
    public TelegramServiceApplication(TelegramBot telegramBot) {
        TelegramServiceApplication.telegramBot = telegramBot;
    }

    public static void main(String[] args) {
        SpringApplication.run(TelegramServiceApplication.class, args);
        registerTelegramBot();
    }

    private static void registerTelegramBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}