package eu.senla.cryptoservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoinmarketcapRequestService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${coinmarketcap.uri}")
    private String coinmarketcapUri;
    @Value("${coinmarketcap.api-key}")
    private String coinmarketcapApiKey;
    @Value("${coinmarketcap.cryptocurrency}")
    private String cryptocurrency;

    public List<CoinmarketcapCurrencyEntity> getCoinmarketcapCrypto() {
        String response = webClient
                .get()
                .uri(coinmarketcapUri + cryptocurrency)
                .header("X-CMC_PRO_API_KEY", coinmarketcapApiKey)
                .retrieve().bodyToMono(String.class)
                .block();

        return extractCoinmarketcapDataFromJson(response);
    }

    private List<CoinmarketcapCurrencyEntity> extractCoinmarketcapDataFromJson(String json) {
        return Arrays.stream(cryptocurrency.split(","))
                .map(cryptocurrency -> {
                    JsonNode cryptoData = getCryptoData(json, cryptocurrency);
                    return buildCoinmarketcapCurrencyEntity(cryptoData, cryptocurrency);
                }).collect(Collectors.toList());
    }

    @SneakyThrows
    private JsonNode getCryptoData(String json, String cryptocurrency) {
        JsonNode jsonNode = objectMapper.readTree(json);
        return jsonNode.get("data").get(cryptocurrency).get(0).get("quote").get("USD");
    }

    private CoinmarketcapCurrencyEntity buildCoinmarketcapCurrencyEntity(JsonNode cryptoData, String cryptocurrency) {
        return CoinmarketcapCurrencyEntity.builder()
                .cryptocurrency(cryptocurrency)
                .price(cryptoData.get("price").decimalValue())
                .volume24h(cryptoData.get("volume_change_24h").decimalValue())
                .percentChange1h(cryptoData.get("percent_change_1h").asDouble())
                .percentChange24h(cryptoData.get("percent_change_24h").asDouble())
                .percentChange7d(cryptoData.get("percent_change_7d").asDouble())
                .percentChange30d(cryptoData.get("percent_change_30d").asDouble())
                .dateTime(LocalDateTime.now())
                .build();
    }
}
