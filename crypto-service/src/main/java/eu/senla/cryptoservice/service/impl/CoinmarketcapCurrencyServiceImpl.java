package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.cryptoservice.repository.CoinmarketcapCurrencyRepository;
import eu.senla.cryptoservice.service.CoinmarketcapCurrencyService;
import eu.senla.cryptoservice.service.CoinmarketcapRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinmarketcapCurrencyServiceImpl implements CoinmarketcapCurrencyService {

    private final CoinmarketcapRequestService requestService;
    private final CoinmarketcapCurrencyRepository coinmarketcapCurrencyRepository;

    @Override
    public Page<CoinmarketcapCurrencyEntity> findAllBy(String cryptocurrency, int page, int size) {
        return coinmarketcapCurrencyRepository.findAllByCryptocurrency(cryptocurrency, PageRequest.of(page, size));
    }

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
