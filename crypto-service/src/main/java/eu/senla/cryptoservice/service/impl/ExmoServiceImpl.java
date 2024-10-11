package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.entity.ExmoInfoEntity;
import eu.senla.cryptoservice.repository.ExmoInfoRepository;
import eu.senla.cryptoservice.service.ExmoService;
import eu.senla.cryptoservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExmoServiceImpl implements ExmoService {

    private final RequestService requestService;
    private final ExmoInfoRepository exmoInfoRepository;

    @Override
    public void getExmoInfo() {
        requestService.getExmoInfo();
    }

    @Override
    public void save(ExmoInfoEntity entity) {
        exmoInfoRepository.save(entity);
    }
}
