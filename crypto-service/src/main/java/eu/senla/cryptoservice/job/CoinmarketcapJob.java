package eu.senla.cryptoservice.job;

import eu.senla.cryptoservice.entity.CoinmarketcapCurrencyEntity;
import eu.senla.cryptoservice.service.CoinmarketcapCurrencyService;
import eu.senla.cryptoservice.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@RequiredArgsConstructor
@DisallowConcurrentExecution
public class CoinmarketcapJob implements Job {

    private final RequestService requestService;
    private final CoinmarketcapCurrencyService coinmarketcapCurrencyService;

    @Override
    public void execute(JobExecutionContext context) {
        CoinmarketcapCurrencyEntity coinmarketcapCrypto = requestService.getCoinmarketcapCrypto();
        coinmarketcapCurrencyService.save(coinmarketcapCrypto);
    }
}
