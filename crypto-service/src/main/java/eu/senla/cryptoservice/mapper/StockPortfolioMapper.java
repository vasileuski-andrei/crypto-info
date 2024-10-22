package eu.senla.cryptoservice.mapper;

import eu.senla.cryptoservice.dto.StockPortfolioDto;
import eu.senla.cryptoservice.entity.StockPortfolioEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockPortfolioMapper {

    StockPortfolioEntity toStockPortfolioEntity(StockPortfolioDto stockPortfolioDto);

    List<StockPortfolioDto> toStockPortfolioDtos(List<StockPortfolioEntity> stockPortfolioEntities);
}
