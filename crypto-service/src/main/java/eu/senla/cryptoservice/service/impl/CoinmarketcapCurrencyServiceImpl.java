package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.cryptoservice.repository.CoinmarketcapCurrencyRepository;
import eu.senla.cryptoservice.service.CoinmarketcapCurrencyService;
import eu.senla.cryptoservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoinmarketcapCurrencyServiceImpl implements CoinmarketcapCurrencyService {

    private final RequestService requestService;
    private final CoinmarketcapCurrencyRepository coinmarketcapCurrencyRepository;

    @Override
    public void getCoinmarketcapCurrency() {
        CoinmarketcapCurrencyEntity coinmarketcapCurrencyEntity = requestService.getCoinmarketcapCrypto();
        coinmarketcapCurrencyRepository.save(coinmarketcapCurrencyEntity);
    }

    @Override
    public void save(CoinmarketcapCurrencyEntity entity) {
        coinmarketcapCurrencyRepository.save(entity);
    }
}
