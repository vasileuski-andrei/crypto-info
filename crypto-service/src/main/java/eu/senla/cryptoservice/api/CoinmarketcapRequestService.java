package eu.senla.cryptoservice.api;

import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.shared.dto.CoinmarketcapInfoDto;

import java.util.List;

public interface CoinmarketcapRequestService {

    List<CoinmarketcapCurrencyEntity> getCoinmarketcapCrypto();

    CoinmarketcapInfoDto getConvertedPrice(String messageText);
}
