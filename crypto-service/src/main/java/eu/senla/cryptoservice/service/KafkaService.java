package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.dto.ExmoInfoDto;
import eu.senla.cryptoservice.dto.TgDataDto;
import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private static final String MY_INFO = "My info";
    private static final String CURRENCY_LIST = "Currency list";

    private final ExmoRequestService exmoRequestService;
    private final ExmoService exmoService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topics.topic-exmo-user-info}")
    private String exmoUserInfoTopic;
    @Value("${spring.kafka.topics.topic-exmo-currency-list}")
    private String exmoCurrencyListTopic;

    @KafkaListener(topics = "${spring.kafka.topics.topic-tg-message}")
    public void consumeMessage(TgDataDto tgDataDto) {
        if (tgDataDto.getMessageType().equals(MY_INFO)) {
            ExmoInfoEntity exmoInfo = exmoRequestService.getUserInfo();
            exmoService.save(exmoInfo);
            kafkaTemplate.send(exmoUserInfoTopic, exmoInfo);
        } else if (tgDataDto.getMessageType().equals(CURRENCY_LIST)) {
            ExmoInfoDto exmoInfoDto = exmoRequestService.getCurrencyList();
            kafkaTemplate.send(exmoCurrencyListTopic, exmoInfoDto);
        }

    }
}
