package eu.senla.cryptoservice.service.impl;

import eu.senla.cryptoservice.dto.CoinmarketcapCurrencyDto;
import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.cryptoservice.mapper.CoinmarketcapCurrencyMapper;
import eu.senla.cryptoservice.repository.CoinmarketcapCurrencyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CoinmarketcapCurrencyServiceImplTest {

    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 1;
    private static final String CRYPTOCURRENCY = "cryptocurrency";

    @Mock
    private CoinmarketcapCurrencyRepository coinmarketcapCurrencyRepository;
    @Mock
    private CoinmarketcapCurrencyMapper coinmarketcapCurrencyMapper;

    @InjectMocks
    private CoinmarketcapCurrencyServiceImpl coinmarketcapCurrencyService;

    @Test
    void findAllBy() {
        // GIVEN
        CoinmarketcapCurrencyEntity coinmarketcapCurrencyEntity = new CoinmarketcapCurrencyEntity();
        CoinmarketcapCurrencyDto coinmarketcapCurrencyDto = new CoinmarketcapCurrencyDto();
        coinmarketcapCurrencyDto.setPrice(BigDecimal.ONE);
        Page<CoinmarketcapCurrencyEntity> page = new PageImpl<>(List.of(coinmarketcapCurrencyEntity));

        when(coinmarketcapCurrencyRepository.findAllByCryptocurrency(CRYPTOCURRENCY, PageRequest.of(PAGE_NUMBER, PAGE_SIZE)))
                .thenReturn(page);
        when(coinmarketcapCurrencyMapper.toCoinmarketcapCurrencyDto(coinmarketcapCurrencyEntity))
                .thenReturn(coinmarketcapCurrencyDto);

        // WHEN
        Page<CoinmarketcapCurrencyDto> actual =
                coinmarketcapCurrencyService.findAllBy(CRYPTOCURRENCY, PAGE_NUMBER, PAGE_SIZE);

        // THEN
        assertEquals(1, actual.getSize());
        assertEquals(BigDecimal.ONE, actual.getContent().get(0).getPrice());
    }
}