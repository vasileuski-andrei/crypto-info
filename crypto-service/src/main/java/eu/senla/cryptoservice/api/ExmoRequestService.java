package eu.senla.cryptoservice.api;

import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import eu.senla.shared.dto.ExmoInfoDto;

public interface ExmoRequestService {

    ExmoInfoEntity getUserInfo();

    ExmoInfoDto getCurrencyList();
}
