package eu.senla.cryptoservice.controller;

import eu.senla.cryptoservice.dto.CoinmarketcapCurrencyDto;
import eu.senla.cryptoservice.service.CoinmarketcapCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CoinmarketcapGraphQLController {

    private final CoinmarketcapCurrencyService coinmarketcapCurrencyService;

    @QueryMapping
    public Iterable<CoinmarketcapCurrencyDto> getAllBy(@Argument String cryptocurrency,
                                                       @Argument int page,
                                                       @Argument int size) {
        return coinmarketcapCurrencyService.findAllBy(cryptocurrency, page, size);
    }
}
