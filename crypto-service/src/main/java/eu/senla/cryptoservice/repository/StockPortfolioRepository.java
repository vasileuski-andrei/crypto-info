package eu.senla.cryptoservice.repository;

import eu.senla.cryptoservice.entity.StockPortfolioEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockPortfolioRepository extends MongoRepository<StockPortfolioEntity, String> {
}
