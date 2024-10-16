package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import eu.senla.shared.dto.ExmoInfoDto;

public interface ExmoService {

    void getUserInfo();

    ExmoInfoDto getCurrencyList();

    void save(ExmoInfoEntity entity);
}
