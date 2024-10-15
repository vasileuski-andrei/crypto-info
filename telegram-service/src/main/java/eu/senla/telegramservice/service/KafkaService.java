package eu.senla.telegramservice.service;

import eu.senla.telegramservice.dto.ExmoInfoDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final TelegramBot telegramBot;

    @KafkaListener(topics = "${spring.kafka.topics.topic-exmo-user-info}")
    public void exmoUserInfoListener(ExmoInfoDto exmoInfoDto) {
        StringBuilder response = new StringBuilder(StringUtils.EMPTY);
        for (String currency : exmoInfoDto.getBalances()) {
            response.append(currency.replace("\"", "")).append("\n");
        }

        telegramBot.sendAnswer(response.toString());
    }

    @KafkaListener(topics = "${spring.kafka.topics.topic-exmo-currency-list}")
    public void exmoCurrencyListListener(ExmoInfoDto exmoInfoDto) {
        String currencies = exmoInfoDto.getCurrencyList().stream()
                .map(currencyDto -> {
                    return String.format("%s %s", currencyDto.getName(), currencyDto.getDescription());
                })
                .collect(Collectors.joining("\n"));

        telegramBot.sendAnswer(currencies);
    }
}
