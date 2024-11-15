package eu.senla.telegramservice.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.shared.dto.CoinmarketcapInfoDto;
import eu.senla.shared.dto.ExmoInfoDto;
import eu.senla.shared.exception.CommonConversionException;
import eu.senla.telegramservice.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final TelegramBot telegramBot;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topics.topic-exmo-user-info}")
    public void exmoUserInfoListener(String data) {
        ExmoInfoDto exmoInfoDto;
        try {
            exmoInfoDto = objectMapper.readValue(data, ExmoInfoDto.class);
        } catch (JsonProcessingException e) {
            throw new CommonConversionException("[KafkaConsumer.exmoUserInfoListener]Error conversion from String to ExmoInfoDto");
        }
        StringBuilder response = new StringBuilder();
        for (String currency : exmoInfoDto.getBalances()) {
            String formattedCurrency = currencyFormatting(currency);
            response.append(formattedCurrency);
        }

        telegramBot.sendAnswer(response.toString());
    }

    @KafkaListener(topics = "${spring.kafka.topics.topic-exmo-currency-list}")
    public void exmoCurrencyListListener(String data) {
        ExmoInfoDto exmoInfoDto;
        try {
            exmoInfoDto = objectMapper.readValue(data, ExmoInfoDto.class);
        } catch (JsonProcessingException e) {
            throw new CommonConversionException("[KafkaConsumer.exmoCurrencyListListener]Error conversion from String to ExmoInfoDto");
        }
        String currencies = exmoInfoDto.getCurrencyList().stream()
                .map(currencyDto -> String.format("%s %s", currencyDto.getName(), currencyDto.getDescription()))
                .collect(Collectors.joining("\n"));

        telegramBot.sendAnswer(currencies);
    }

    @KafkaListener(topics = "${spring.kafka.topics.topic-coinmarketcap-price-conversion}")
    public void coinmarketcapPriceConversionListener(String data) {
        CoinmarketcapInfoDto coinmarketcapInfoDto = null;
        try {
            coinmarketcapInfoDto = objectMapper.readValue(data, CoinmarketcapInfoDto.class);
        } catch (JsonProcessingException e) {
            throw new CommonConversionException("[KafkaConsumer.coinmarketcapPriceConversionListener]" +
                    "Error conversion from String to CoinmarketcapInfoDto");
        }
        telegramBot.sendAnswer(coinmarketcapInfoDto.getPrice());
    }

    private String currencyFormatting(String currency) {
        return currency.replace(":", " ").replace("\"", "") + "\n";
    }
}
