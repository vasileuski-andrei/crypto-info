package eu.senla.cryptoservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import eu.senla.cryptoservice.api.impl.ExmoRequestServiceImpl;
import eu.senla.shared.dto.CurrencyDto;
import eu.senla.shared.dto.ExmoInfoDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;
import static org.springframework.http.MediaType.valueOf;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class ExmoRequestServiceTest {

    private static final String EXMO_API_VERSION = "/1.1";
    private static final String EXMO_USER_INFO_URN = "/user_info";
    private static final String EXMO_CURRENCY_LIST_URN = "/currency/list/extended";
    private static final String EXMO_API_KEY = "exmoApiKey";
    private static final String EXMO_SECRET_KEY = "exmoSecretKey";
    private static final String SIGN = "75341472f3aff248dc245f8512c5ea2cc448457dd831c1cacee3f5abbde95148af78fe013381" +
            "e2c103c4767a01d8281803291450b7e45476b58e2a7dc2492f08";
    private static final String UID = "1234567";
    private static final String CRYPTO = "XLM";
    private static final String DESCRIPTION = "Stellar";

    @Mock
    private WebClient webClient;
    @Spy
    private ObjectMapper objectMapper;

    @InjectMocks
    private ExmoRequestServiceImpl exmoRequestService;

    @BeforeEach
    void setUp() {
        setField(exmoRequestService, "exmoApiVersion", EXMO_API_VERSION);
        setField(exmoRequestService, "exmoUserInfoUrn", EXMO_USER_INFO_URN);
        setField(exmoRequestService, "exmoCurrencyListUrn", EXMO_CURRENCY_LIST_URN);
        setField(exmoRequestService, "exmoApiKey", EXMO_API_KEY);
        setField(exmoRequestService, "exmoSecretKey", EXMO_SECRET_KEY);
    }

    @Test
    void getUserInfo() {
        // GIVEN
        String bodyValue = "nonce=1";
        String responseJsonData = getResponseJsonData();

        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        when(webClient.post()).thenReturn(requestBodyUriSpec);

        WebClient.RequestBodySpec firstRequestBodySpec = mock(WebClient.RequestBodySpec.class);
        when(requestBodyUriSpec.uri(EXMO_API_VERSION + EXMO_USER_INFO_URN)).thenReturn(firstRequestBodySpec);
        WebClient.RequestBodySpec secondRequestBodySpec = mock(WebClient.RequestBodySpec.class);
        when(firstRequestBodySpec.contentType(valueOf(APPLICATION_FORM_URLENCODED_VALUE))).thenReturn(secondRequestBodySpec);
        WebClient.RequestBodySpec thirdRequestBodySpec = mock(WebClient.RequestBodySpec.class);
        when(secondRequestBodySpec.header("Key", EXMO_API_KEY)).thenReturn(thirdRequestBodySpec);
        WebClient.RequestBodySpec fourthRequestBodySpec = mock(WebClient.RequestBodySpec.class);
        when(thirdRequestBodySpec.header("Sign", SIGN)).thenReturn(fourthRequestBodySpec);

        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        when(fourthRequestBodySpec.bodyValue(bodyValue)).thenReturn(requestHeadersSpec);

        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        Mono monoMock = mock(Mono.class);
        when(responseSpec.bodyToMono(String.class)).thenReturn(monoMock);
        when(monoMock.block()).thenReturn(responseJsonData);

        // WHEN
        ExmoInfoEntity actual = exmoRequestService.getUserInfo();

        // THEN
        assertEquals(UID, actual.getUid());
        assertEquals(2, actual.getBalances().size());
    }

    @Test
    void getCurrencyList() {
        // GIVEN
        CurrencyDto currencyDto = CurrencyDto.builder()
                .name(CRYPTO)
                .description(DESCRIPTION)
                .build();

        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);

        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        when(requestHeadersUriSpec.uri(EXMO_API_VERSION + EXMO_CURRENCY_LIST_URN)).thenReturn(requestBodySpec);

        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);

        Flux<CurrencyDto> flux = Flux.just(currencyDto);
        when(responseSpec.bodyToFlux(CurrencyDto.class)).thenReturn(flux);

        // WHEN
        ExmoInfoDto actual = exmoRequestService.getCurrencyList();

        // THEN
        assertEquals(CRYPTO, actual.getCurrencyList().get(0).getName());
        assertEquals(DESCRIPTION, actual.getCurrencyList().get(0).getDescription());
    }

    @SneakyThrows
    private String getResponseJsonData() {
        ClassPathResource resource = new ClassPathResource("json/exmo-user-info-data.json");
        return Files.readString(resource.getFile().toPath());
    }
}