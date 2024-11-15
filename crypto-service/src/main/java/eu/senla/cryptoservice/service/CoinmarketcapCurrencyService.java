package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.dto.CoinmarketcapCurrencyDto;
import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CoinmarketcapCurrencyService {

    Page<CoinmarketcapCurrencyDto> findAllBy(String cryptocurrency, int page, int size);

    void saveAll(List<CoinmarketcapCurrencyEntity> entities);
}
