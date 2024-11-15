package eu.senla.cryptoservice.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CoinmarketcapCurrencyDto {

    private String cryptocurrency;
    private BigDecimal price;
    private BigDecimal volume24h;
    private Double percentChange1h;
    private Double percentChange24h;
    private Double percentChange7d;
    private Double percentChange30d;
    private LocalDateTime dateTime;
}
