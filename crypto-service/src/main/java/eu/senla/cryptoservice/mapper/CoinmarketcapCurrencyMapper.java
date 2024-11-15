package eu.senla.cryptoservice.mapper;

import eu.senla.cryptoservice.dto.CoinmarketcapCurrencyDto;
import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoinmarketcapCurrencyMapper {

    CoinmarketcapCurrencyDto toCoinmarketcapCurrencyDto(CoinmarketcapCurrencyEntity entity);
}
