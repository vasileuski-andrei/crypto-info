package eu.senla.cryptoservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.cryptoservice.util.ExmoUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${coinmarketcap.api-key}")
    private String coinmarketcapUri;
    @Value("${coinmarketcap.api-key}")
    private String coinmarketcapApiKey;
    @Value("${exmo.uri}")
    private String exmoUri;
    @Value("${exmo.api-key}")
    private String exmoApiKey;
    @Value("${exmo.s-key}")
    private String exmoSecretKey;

    public CoinmarketcapCurrencyEntity getCoinmarketcapCrypto() {
        String response = webClient
                .get()
                .uri(coinmarketcapUri)
                .header("X-CMC_PRO_API_KEY", coinmarketcapApiKey)
                .retrieve().bodyToMono(String.class)
                .block();

        return extractCoinmarketcapDataFromJson(response);
    }

    public void getExmoInfo() {
        String body = ExmoUtil.getBody();
        String sign = ExmoUtil.getSign(exmoSecretKey, body);
        String response = webClient
                .post()
                .uri(exmoUri)
                .contentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .header("Key", exmoApiKey)
                .header("Sign", sign)
                .bodyValue(body)
                .retrieve().bodyToMono(String.class)
                .block();
        System.out.println();
    }

    @SneakyThrows
    private CoinmarketcapCurrencyEntity extractCoinmarketcapDataFromJson(String json) {
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode data = jsonNode.get("data").get("BTC").get(0).get("quote").get("USD");

        return CoinmarketcapCurrencyEntity.builder()
                .price(data.get("price").decimalValue())
                .volume24h(data.get("volume_change_24h").decimalValue())
                .percentChange1h(data.get("percent_change_1h").asDouble())
                .percentChange24h(data.get("percent_change_24h").asDouble())
                .percentChange7d(data.get("percent_change_7d").asDouble())
                .percentChange30d(data.get("percent_change_30d").asDouble())
                .build();
    }
}
