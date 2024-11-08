package eu.senla.cryptoservice.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.cryptoservice.api.CoinmarketcapRequestService;
import eu.senla.shared.dto.CoinmarketcapInfoDto;
import eu.senla.shared.exception.CommonConversionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoinmarketcapRequestServiceImpl implements CoinmarketcapRequestService {

    private static final String API_KEY_HEADER = "X-CMC_PRO_API_KEY";
    private static final String DATA = "data";
    private static final String QUOTE = "quote";

    private final WebClient coinmarketcapWebClient;
    private final ObjectMapper objectMapper;

    @Value("${coinmarketcap.api-version}")
    private String coinmarketcapApiVersion;

    @Value("${coinmarketcap.cryptocurrency-latest-urn}")
    private String coinmarketcapCryptocurrencyLatestUrn;
    @Value("${coinmarketcap.price-conversion-urn}")
    private String coinmarketcapPriceConversionUrn;

    @Value("${coinmarketcap.api-key}")
    private String coinmarketcapApiKey;
    @Value("${coinmarketcap.cryptocurrency}")
    private String cryptocurrency;

    @Override
    public List<CoinmarketcapCurrencyEntity> getCoinmarketcapCrypto() {
        String response = coinmarketcapWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(coinmarketcapApiVersion + coinmarketcapCryptocurrencyLatestUrn)
                        .queryParam("symbol", cryptocurrency)
                        .build())
                .header(API_KEY_HEADER, coinmarketcapApiKey)
                .retrieve().bodyToMono(String.class)
                .block();

        return extractCryptocurrencyLatest(response);
    }

    @Override
    public CoinmarketcapInfoDto getConvertedPrice(String messageText) {
        String[] params = messageText.split("\s");
        String amount = params[0];
        String from = params[1].toUpperCase();
        String to = params[2].toUpperCase();
        String response = coinmarketcapWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(coinmarketcapApiVersion + coinmarketcapPriceConversionUrn)
                        .queryParam("amount", amount)
                        .queryParam("symbol", from)
                        .queryParam("convert", to)
                        .build())
                .header(API_KEY_HEADER, coinmarketcapApiKey)
                .retrieve().bodyToMono(String.class)
                .block();

        return extractConvertedPrice(response, to);
    }

    private CoinmarketcapInfoDto extractConvertedPrice(String response, String toCurrency) {
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new CommonConversionException("[CoinmarketcapRequestService.extractConvertedPrice]Error reading response tree");
        }
        BigDecimal convertedPrice = jsonNode.get(DATA).get(0).get(QUOTE).get(toCurrency).get("price")
                .decimalValue().setScale(2, RoundingMode.HALF_UP);

        return CoinmarketcapInfoDto.builder()
                .price(String.format("%s %s",convertedPrice, toCurrency))
                .build();
    }

    private List<CoinmarketcapCurrencyEntity> extractCryptocurrencyLatest(String json) {
        return Arrays.stream(cryptocurrency.split(","))
                .map(cryptocurrency -> {
                    JsonNode cryptoData = getCryptoData(json, cryptocurrency);
                    return buildCoinmarketcapCurrencyEntity(cryptoData, cryptocurrency);
                }).collect(Collectors.toList());
    }

    private JsonNode getCryptoData(String json, String cryptocurrency) {
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            throw new CommonConversionException("[CoinmarketcapRequestService.getCryptoData]Error reading response tree");
        }
        return jsonNode.get(DATA).get(cryptocurrency).get(0).get(QUOTE).get("USD");
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
