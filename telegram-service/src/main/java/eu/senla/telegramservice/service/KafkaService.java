package eu.senla.telegramservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.shared.dto.CoinmarketcapInfoDto;
import eu.senla.shared.dto.ExmoInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final TelegramBot telegramBot;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @KafkaListener(topics = "${spring.kafka.topics.topic-exmo-user-info}")
    public void exmoUserInfoListener(String data) {
        ExmoInfoDto exmoInfoDto = objectMapper.readValue(data, ExmoInfoDto.class);
        StringBuilder response = new StringBuilder(StringUtils.EMPTY);
        for (String currency : exmoInfoDto.getBalances()) {
            String formattedCurrency = currencyFormatting(currency);
            response.append(formattedCurrency);
        }

        telegramBot.sendAnswer(response.toString());
    }

    @SneakyThrows
    @KafkaListener(topics = "${spring.kafka.topics.topic-exmo-currency-list}")
    public void exmoCurrencyListListener(String data) {
        ExmoInfoDto exmoInfoDto = objectMapper.readValue(data, ExmoInfoDto.class);
        String currencies = exmoInfoDto.getCurrencyList().stream()
                .map(currencyDto -> {
                    return String.format("%s %s", currencyDto.getName(), currencyDto.getDescription());
                })
                .collect(Collectors.joining("\n"));

        telegramBot.sendAnswer(currencies);
    }

    @SneakyThrows
    @KafkaListener(topics = "${spring.kafka.topics.topic-coinmarketcap-price-conversion}")
    public void coinmarketcapPriceConversionListener(String data) {
        CoinmarketcapInfoDto coinmarketcapInfoDto = objectMapper.readValue(data, CoinmarketcapInfoDto.class);
        telegramBot.sendAnswer(coinmarketcapInfoDto.getPrice());
    }

    private String currencyFormatting(String currency) {
        return currency.replace(":", " ").replace("\"", "") + "\n";
    }
}
