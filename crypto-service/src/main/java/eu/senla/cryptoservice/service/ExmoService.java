package eu.senla.cryptoservice.service;

import eu.senla.cryptoservice.entity.ExmoInfoEntity;

public interface ExmoService {

    void getExmoInfo();

    void save(ExmoInfoEntity entity);
}
