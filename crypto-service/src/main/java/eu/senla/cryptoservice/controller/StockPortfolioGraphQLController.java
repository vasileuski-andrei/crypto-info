package eu.senla.cryptoservice.controller;

import eu.senla.cryptoservice.dto.StockPortfolioDto;
import eu.senla.cryptoservice.service.StockPortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StockPortfolioGraphQLController {

    private final StockPortfolioService stockPortfolioService;

    @QueryMapping
    public Iterable<StockPortfolioDto> getAllStock() {
        return stockPortfolioService.getAllStocks();
    }

    @MutationMapping
    public Boolean addStock(@Argument StockPortfolioDto stockPortfolioDto) {
        stockPortfolioService.save(stockPortfolioDto);
        return true;
    }
}
