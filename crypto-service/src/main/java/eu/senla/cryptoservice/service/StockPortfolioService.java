package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.dto.StockPortfolioDto;

import java.util.List;

public interface StockPortfolioService {

    List<StockPortfolioDto> getAllStocks();

    void save(StockPortfolioDto stockPortfolioDto);
}
