package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.api.ExmoRequestService;
import eu.senla.cryptoservice.api.impl.ExmoRequestServiceImpl;
import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import eu.senla.cryptoservice.repository.ExmoRepository;
import eu.senla.cryptoservice.service.ExmoService;
import eu.senla.shared.dto.ExmoInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void save(ExmoInfoEntity entity) {
        exmoRepository.save(entity);
    }
}
