package eu.senla.cryptoservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockPortfolioDto {

    private String currency;
    private String stockExchange;
    private String amount;
}
