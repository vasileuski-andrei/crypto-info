package eu.senla.cryptoservice.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@Document("STOCK_PORTFOLIO")
public class StockPortfolioEntity {

    @Id
    private String id;
    private String currency;
    private String stockExchange;
    private BigDecimal amount;
    private LocalDateTime dateTime;
}

