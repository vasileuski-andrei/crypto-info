package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;

public interface CoinmarketcapCurrencyService {

    void getCoinmarketcapCurrency();

    void save(CoinmarketcapCurrencyEntity entity);
}
