package eu.senla.cryptoservice.job;

import eu.senla.cryptoservice.api.CoinmarketcapRequestService;
import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.cryptoservice.service.CoinmarketcapCurrencyService;
import eu.senla.cryptoservice.api.impl.CoinmarketcapRequestServiceImpl;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.List;

@RequiredArgsConstructor
@DisallowConcurrentExecution
public class CoinmarketcapJob implements Job {

    private final CoinmarketcapRequestService requestService;
    private final CoinmarketcapCurrencyService coinmarketcapCurrencyService;

    @Override
    public void execute(JobExecutionContext context) {
        List<CoinmarketcapCurrencyEntity> coinmarketcapCrypto = requestService.getCoinmarketcapCrypto();
        coinmarketcapCurrencyService.saveAll(coinmarketcapCrypto);
    }
}
