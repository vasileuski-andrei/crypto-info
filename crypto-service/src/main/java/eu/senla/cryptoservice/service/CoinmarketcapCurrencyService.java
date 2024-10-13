package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;

import java.util.List;

public interface CoinmarketcapCurrencyService {

    List<CoinmarketcapCurrencyEntity> getCoinmarketcapCurrency();

    void saveAll(List<CoinmarketcapCurrencyEntity> entities);
}
