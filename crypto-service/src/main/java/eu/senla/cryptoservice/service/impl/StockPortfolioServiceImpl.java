package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.dto.StockPortfolioDto;
import eu.senla.cryptoservice.entity.StockPortfolioEntity;
import eu.senla.cryptoservice.mapper.StockPortfolioMapper;
import eu.senla.cryptoservice.repository.StockPortfolioRepository;
import eu.senla.cryptoservice.service.StockPortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPortfolioServiceImpl implements StockPortfolioService {

    private final StockPortfolioRepository stockPortfolioRepository;
    private final StockPortfolioMapper stockPortfolioMapper;

    @Override
    public List<StockPortfolioDto> getAllStocks() {
        List<StockPortfolioEntity> stockPortfolioEntities = stockPortfolioRepository.findAll();
        return stockPortfolioMapper.toStockPortfolioDtos(stockPortfolioEntities);
    }

    @Override
    public void save(StockPortfolioDto stockPortfolioDto) {
        StockPortfolioEntity stockPortfolioEntity = stockPortfolioMapper.toStockPortfolioEntity(stockPortfolioDto);
        stockPortfolioRepository.save(stockPortfolioEntity);
        log.info("[StockPortfolioService.save]Successfully added a new entry to the stock portfolio");
    }
}
