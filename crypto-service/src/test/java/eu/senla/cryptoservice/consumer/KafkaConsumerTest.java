package eu.senla.cryptoservice.consumer;

import eu.senla.cryptoservice.api.CoinmarketcapRequestService;
import eu.senla.cryptoservice.api.ExmoRequestService;
import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import eu.senla.cryptoservice.service.ExmoService;
import eu.senla.shared.dto.CoinmarketcapInfoDto;
import eu.senla.shared.dto.ExmoInfoDto;
import eu.senla.shared.dto.TgDataDto;
import eu.senla.shared.enums.TgMessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;

import static eu.senla.shared.enums.TgMessageType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerTest {

    private static final String EXMO_USER_INFO_TOPIC = "exmoUserInfoTopic";
    private static final String EXMO_CURRENCY_LIST_TOPIC = "exmoCurrencyListTopic";
    private static final String COINMARKETCAP_PRICE_CONVERSION_TOPIC = "coinmarketcapPriceConversionTopic";
    private static final String UID = "uid";
    private static final String BTC = "btc";
    private static final String MESSAGE_TEXT = "text";
    private static final String PRICE = "price";

    @Mock
    private CoinmarketcapRequestService coinmarketcapRequestService;
    @Mock
    private ExmoRequestService exmoRequestService;
    @Mock
    private ExmoService exmoService;
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @Captor
    private ArgumentCaptor<ExmoInfoEntity> exmoInfoEntityArgumentCaptor;
    @Captor
    private ArgumentCaptor<ExmoInfoDto> exmoInfoDtoArgumentCaptor;
    @Captor
    private ArgumentCaptor<CoinmarketcapInfoDto> coinmarketcapInfoDtoArgumentCaptor;

    @BeforeEach
    void setUp() {
        setField(kafkaConsumer, EXMO_USER_INFO_TOPIC, EXMO_USER_INFO_TOPIC);
        setField(kafkaConsumer, EXMO_CURRENCY_LIST_TOPIC, EXMO_CURRENCY_LIST_TOPIC);
        setField(kafkaConsumer, COINMARKETCAP_PRICE_CONVERSION_TOPIC, COINMARKETCAP_PRICE_CONVERSION_TOPIC);
    }

    @Test
    void consumeMessageWhenTgMessageTypeMyInfo() {
        // GIVEN
        ExmoInfoEntity exmoInfoEntity = ExmoInfoEntity.builder()
                .uid(UID)
                .build();

        when(exmoRequestService.getUserInfo()).thenReturn(exmoInfoEntity);

        // WHEN
        kafkaConsumer.consumeMessage(buildTgDataDto(MY_INFO));

        // THEN
        verify(exmoService).save(exmoInfoEntity);
        verify(kafkaTemplate).send(eq(EXMO_USER_INFO_TOPIC), exmoInfoEntityArgumentCaptor.capture());
        assertEquals(UID, exmoInfoEntityArgumentCaptor.getValue().getUid());
    }

    @Test
    void consumeMessageWhenTgMessageTypeCurrencyList() {
        // GIVEN
        ExmoInfoDto exmoInfoDto = ExmoInfoDto.builder()
                .balances(List.of(BTC))
                .build();

        when(exmoRequestService.getCurrencyList()).thenReturn(exmoInfoDto);

        // WHEN
        kafkaConsumer.consumeMessage(buildTgDataDto(CURRENCY_LIST));

        // THEN
        verify(kafkaTemplate).send(eq(EXMO_CURRENCY_LIST_TOPIC), exmoInfoDtoArgumentCaptor.capture());
        assertEquals(BTC, exmoInfoDtoArgumentCaptor.getValue().getBalances().get(0));
    }

    @Test
    void consumeMessageWhenTgMessageTypeConversion() {
        // GIVEN
        CoinmarketcapInfoDto coinmarketcapInfoDto = CoinmarketcapInfoDto.builder()
                .price(PRICE)
                .build();

        when(coinmarketcapRequestService.getConvertedPrice(MESSAGE_TEXT)).thenReturn(coinmarketcapInfoDto);

        // WHEN
        kafkaConsumer.consumeMessage(buildTgDataDto(CONVERSION));

        // THEN
        verify(kafkaTemplate).send(eq(COINMARKETCAP_PRICE_CONVERSION_TOPIC), coinmarketcapInfoDtoArgumentCaptor.capture());
        assertEquals(PRICE, coinmarketcapInfoDtoArgumentCaptor.getValue().getPrice());
    }

    private TgDataDto buildTgDataDto(TgMessageType messageType) {
        return TgDataDto.builder()
                .messageType(messageType)
                .messageText(MESSAGE_TEXT)
                .build();
    }
}