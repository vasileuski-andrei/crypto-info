package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CoinmarketcapCurrencyService {

    Page<CoinmarketcapCurrencyEntity> findAllBy(String cryptocurrency, int page, int size);

    List<CoinmarketcapCurrencyEntity> getCoinmarketcapCurrency();

    void saveAll(List<CoinmarketcapCurrencyEntity> entities);
}
