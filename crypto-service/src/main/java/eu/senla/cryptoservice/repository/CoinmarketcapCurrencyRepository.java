package eu.senla.cryptoservice.repository;

import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoinmarketcapCurrencyRepository extends MongoRepository<CoinmarketcapCurrencyEntity, String> {

    Page<CoinmarketcapCurrencyEntity> findAllByCryptocurrency(String cryptocurrency, PageRequest pageRequest);
}
