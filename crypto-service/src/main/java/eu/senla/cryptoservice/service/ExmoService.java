package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.dto.ExmoInfoDto;
import eu.senla.cryptoservice.entity.ExmoInfoEntity;

public interface ExmoService {

    void getUserInfo();

    ExmoInfoDto getCurrencyList();

    void save(ExmoInfoEntity entity);
}
