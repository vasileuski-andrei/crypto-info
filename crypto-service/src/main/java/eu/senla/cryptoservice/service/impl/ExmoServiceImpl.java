package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import eu.senla.cryptoservice.repository.ExmoRepository;
import eu.senla.cryptoservice.service.ExmoRequestService;
import eu.senla.cryptoservice.service.ExmoService;
import eu.senla.shared.dto.ExmoInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExmoServiceImpl implements ExmoService {

    private final ExmoRequestService exmoRequestService;
    private final ExmoRepository exmoRepository;

    @Override
    public void getUserInfo() {
        exmoRequestService.getUserInfo();
    }

    @Override
    public ExmoInfoDto getCurrencyList() {
        return exmoRequestService.getCurrencyList();
    }

    @Override
    public void save(ExmoInfoEntity entity) {
        exmoRepository.save(entity);
    }
}
