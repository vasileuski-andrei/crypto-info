package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.service.ExmoService;
import eu.senla.cryptoservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExmoServiceImpl implements ExmoService {

    private final RequestService requestService;

    @Override
    public void getExmoInfo() {
        requestService.getExmoInfo();
    }
}
