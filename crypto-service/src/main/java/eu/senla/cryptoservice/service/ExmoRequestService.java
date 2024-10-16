package eu.senla.cryptoservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import eu.senla.cryptoservice.util.ExmoUtil;
import eu.senla.shared.dto.CurrencyDto;
import eu.senla.shared.dto.ExmoInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExmoRequestService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${exmo.base-url}")
    private String exmoBaseUrl;
    @Value("${exmo.api-version}")
    private String exmoApiVersion;

    @Value("${exmo.user-info-urn}")
    private String exmoUserInfoUrn;
    @Value("${exmo.currency-list-urn}")
    private String exmoCurrencyListUrn;

    @Value("${exmo.api-key}")
    private String exmoApiKey;
    @Value("${exmo.s-key}")
    private String exmoSecretKey;

    public ExmoInfoEntity getUserInfo() {
        String body = ExmoUtil.getBody();
        String sign = ExmoUtil.getSign(exmoSecretKey, body);
        String response = webClient
                .post()
                .uri(prepareUri(exmoUserInfoUrn))
                .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .header("Key", exmoApiKey)
                .header("Sign", sign)
                .bodyValue(body)
                .retrieve().bodyToMono(String.class)
                .block();

        return extractExmoDataFromJson(response);
    }

    public ExmoInfoDto getCurrencyList() {
        List<CurrencyDto> currencyDtos = webClient
                .get()
                .uri(prepareUri(exmoCurrencyListUrn))
                .retrieve().bodyToFlux(CurrencyDto.class)
                .collectList()
                .block();

        return ExmoInfoDto.builder()
                .currencyList(currencyDtos)
                .build();
    }

    private String prepareUri(String uri) {
        return exmoBaseUrl + exmoApiVersion + uri;
    }

    @SneakyThrows
    private ExmoInfoEntity extractExmoDataFromJson(String json) {
        JsonNode jsonNode = objectMapper.readTree(json);
        String[] balances = jsonNode.get("balances").toString().replace("}", "").split(",");
        List<String> notZeroBalances = Arrays.stream(balances)
                .filter(currency -> !currency.matches(".+\"0\""))
                .collect(Collectors.toList());

        return ExmoInfoEntity.builder()
                .uid(String.valueOf(jsonNode.get("uid")))
                .dateTime(LocalDateTime.now())
                .balances(notZeroBalances)
                .build();
    }
}
