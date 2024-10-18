package eu.senla.telegramservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.shared.dto.CurrencyDto;
import eu.senla.shared.dto.ExmoInfoDto;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaServiceTest {

    private static final String DATA = StringUtils.EMPTY;
    private static final String CRYPTO = "XLM";
    private static final String DESCRIPTION = "Stellar";

    @Mock
    private TelegramBot telegramBot;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaService kafkaService;

    @Captor
    ArgumentCaptor<String> currenciesCaptor;

    @Test
    @SneakyThrows
    void exmoUserInfoListener() {
        // GIVEN
        ExmoInfoDto exmoInfoDto = buildExmoInfoDto();

        when(objectMapper.readValue(DATA, ExmoInfoDto.class)).thenReturn(exmoInfoDto);

        // WHEN
        kafkaService.exmoUserInfoListener(DATA);

        // THEN
        verify(telegramBot).sendAnswer(currenciesCaptor.capture());
        assertEquals(String.format("%s %s\n", "USDT", "4.26989416"), currenciesCaptor.getValue());
    }

    @Test
    @SneakyThrows
    void exmoCurrencyListListener() {
        // GIVEN
        ExmoInfoDto exmoInfoDto = buildExmoInfoDto();

        when(objectMapper.readValue(DATA, ExmoInfoDto.class)).thenReturn(exmoInfoDto);

        // WHEN
        kafkaService.exmoCurrencyListListener(DATA);

        // THEN
        verify(telegramBot).sendAnswer(currenciesCaptor.capture());
        assertEquals(String.format("%s %s", CRYPTO, DESCRIPTION), currenciesCaptor.getValue());
    }

    private ExmoInfoDto buildExmoInfoDto() {
        CurrencyDto currencyDto = CurrencyDto.builder()
                .name(CRYPTO)
                .description(DESCRIPTION)
                .build();
        return ExmoInfoDto.builder()
                .currencyList(List.of(currencyDto))
                .balances(List.of("\"USDT\":\"4.26989416\""))
                .build();
    }
}