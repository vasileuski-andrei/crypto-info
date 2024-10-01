package eu.senla.cryptoservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
@Document("COINMARKETCAP_CURRENCY")
public class CoinmarketcapCurrencyEntity {

    @Id
    private String id;
    private BigDecimal price;
    private BigDecimal volume24h;
    private Double percentChange1h;
    private Double percentChange24h;
    private Double percentChange7d;
    private Double percentChange30d;
}
