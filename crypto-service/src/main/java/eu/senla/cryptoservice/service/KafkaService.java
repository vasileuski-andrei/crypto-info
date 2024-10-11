package eu.senla.cryptoservice.service;

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

    private static final String MY_INFO = "my info";

    private final RequestService requestService;
    private final ExmoService exmoService;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topics.topic-exmo-user-info}")
    private String exmoUserInfoTopic;

    @KafkaListener(topics = "${spring.kafka.topics.topic-tg-message}")
    public void consumeMessage(TgDataDto tgDataDto) {
        if (tgDataDto.getMessageType().equals(MY_INFO)) {
            ExmoInfoEntity exmoInfo = requestService.getExmoInfo();
            exmoService.save(exmoInfo);
            kafkaTemplate.send(exmoUserInfoTopic, exmoInfo);
        }
    }
}
