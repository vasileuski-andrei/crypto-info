package eu.senla.telegramservice.configuration;

import eu.senla.telegramservice.service.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramBotConfiguration {

    @Value("${spring.telegram.bottoken}")
    private String botToken;
    @Value("${spring.telegram.botname}")
    private String botName;

    @Bean
    public TelegramBot getTelegramBot() {
        return new TelegramBot(botToken, botName);
    }
}
