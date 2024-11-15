package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.dto.CoinmarketcapCurrencyDto;
import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.cryptoservice.mapper.CoinmarketcapCurrencyMapper;
import eu.senla.cryptoservice.repository.CoinmarketcapCurrencyRepository;
import eu.senla.cryptoservice.service.CoinmarketcapCurrencyService;
import eu.senla.cryptoservice.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CoinmarketcapCurrencyServiceImpl implements CoinmarketcapCurrencyService {

    private final CoinmarketcapCurrencyRepository coinmarketcapCurrencyRepository;
    private final CoinmarketcapCurrencyMapper coinmarketcapCurrencyMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<CoinmarketcapCurrencyDto> findAllBy(String cryptocurrency, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CoinmarketcapCurrencyDto> coinmarketcapCurrencyDtoList =
                coinmarketcapCurrencyRepository.findAllByCryptocurrency(cryptocurrency, PageRequest.of(page, size)).stream()
                .map(coinmarketcapCurrencyMapper::toCoinmarketcapCurrencyDto)
                .collect(Collectors.toList());

        return PaginationUtil.convertListToPage(coinmarketcapCurrencyDtoList, pageable);
    }

    @Override
    public void saveAll(List<CoinmarketcapCurrencyEntity> entities) {
        coinmarketcapCurrencyRepository.saveAll(entities);
    }
}
