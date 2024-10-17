package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import eu.senla.shared.dto.CoinmarketcapInfoDto;
import eu.senla.shared.dto.ExmoInfoDto;
import eu.senla.shared.dto.TgDataDto;
import eu.senla.shared.enums.TgMessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static eu.senla.shared.enums.TgMessageType.*;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private final CoinmarketcapRequestService coinmarketcapRequestService;
    private final ExmoRequestService exmoRequestService;
    private final ExmoService exmoService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topics.topic-exmo-user-info}")
    private String exmoUserInfoTopic;
    @Value("${spring.kafka.topics.topic-exmo-currency-list}")
    private String exmoCurrencyListTopic;
    @Value("${spring.kafka.topics.topic-coinmarketcap-price-conversion}")
    private String coinmarketcapPriceConversionTopic;

    @KafkaListener(topics = "${spring.kafka.topics.topic-tg-message}")
    public void consumeMessage(TgDataDto tgDataDto) {
        TgMessageType tgMessageType = tgDataDto.getMessageType();
        if (tgMessageType == MY_INFO) {
            ExmoInfoEntity exmoInfo = exmoRequestService.getUserInfo();
            exmoService.save(exmoInfo);
            kafkaTemplate.send(exmoUserInfoTopic, exmoInfo);
        } else if (tgMessageType == CURRENCY_LIST) {
            ExmoInfoDto exmoInfoDto = exmoRequestService.getCurrencyList();
            kafkaTemplate.send(exmoCurrencyListTopic, exmoInfoDto);
        } else if (tgMessageType == CONVERSION) {
            CoinmarketcapInfoDto coinmarketcapInfoDto =
                    coinmarketcapRequestService.getConvertedPrice(tgDataDto.getMessageText());
            kafkaTemplate.send(coinmarketcapPriceConversionTopic, coinmarketcapInfoDto);
        }
    }
}
