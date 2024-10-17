package eu.senla.cryptoservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${coinmarketcap.base-url}")
    private String coinmarketcapBaseUrl;
    @Value("${exmo.base-url}")
    private String exmoBaseUrl;

    @Bean
    public WebClient coinmarketcapWebClient() {
        return WebClient.builder()
                .baseUrl(coinmarketcapBaseUrl)
                .build();
    }

    @Bean
    public WebClient exmoWebClient() {
        return WebClient.builder()
                .baseUrl(exmoBaseUrl)
                .build();
    }
}
