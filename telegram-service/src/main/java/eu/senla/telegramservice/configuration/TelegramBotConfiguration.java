package eu.senla.telegramservice.configuration;

import eu.senla.telegramservice.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
public class TelegramBotConfiguration {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value(value = "${spring.telegram.bottoken}")
    private String botToken;
    @Value("${spring.telegram.botname}")
    private String botName;

    @Bean
    public TelegramBot getTelegramBot() {
        return new TelegramBot(botToken, botName, kafkaTemplate);
    }
}
