package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.cryptoservice.repository.CoinmarketcapCurrencyRepository;
import eu.senla.cryptoservice.service.CoinmarketcapCurrencyService;
import eu.senla.cryptoservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinmarketcapCurrencyServiceImpl implements CoinmarketcapCurrencyService {

    private final RequestService requestService;
    private final CoinmarketcapCurrencyRepository coinmarketcapCurrencyRepository;

    @Override
    public List<CoinmarketcapCurrencyEntity> getCoinmarketcapCurrency() {
        List<CoinmarketcapCurrencyEntity> coinmarketcapCurrencyEntities = requestService.getCoinmarketcapCrypto();
        saveAll(coinmarketcapCurrencyEntities);
        return coinmarketcapCurrencyEntities;
    }

    @Override
    public void saveAll(List<CoinmarketcapCurrencyEntity> entities) {
        coinmarketcapCurrencyRepository.saveAll(entities);
    }
}
