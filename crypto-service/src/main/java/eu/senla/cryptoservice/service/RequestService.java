package eu.senla.cryptoservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${coinmarketcap.api-key}")
    private String coinmarketcapApiKey;

    public CoinmarketcapCurrencyEntity getCoinmarketcapCrypto() {
        String statusMono = webClient
                .get()
                .uri("/v2/cryptocurrency/quotes/latest?symbol=BTC")
                .header("X-CMC_PRO_API_KEY", coinmarketcapApiKey)
                .retrieve().bodyToMono(String.class)
                .block();

        return extractCoinmarketcapDataFromJson(statusMono);
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
