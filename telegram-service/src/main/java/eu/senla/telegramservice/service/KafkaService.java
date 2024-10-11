package eu.senla.telegramservice.service;

import eu.senla.telegramservice.dto.ExmoInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final TelegramBot telegramBot;

    @KafkaListener(topics = "${spring.kafka.topics.topic-exmo-user-info}")
    public void consumeMessage(ExmoInfoDto exmoInfoDto) {
        telegramBot.sendAnswer(exmoInfoDto.getBalances().toString());
    }
}
