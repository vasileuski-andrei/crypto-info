package eu.senla.cryptoservice.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExmoInfoDto {

    private List<CurrencyDto> currencyList;
    private List<String> balances;
}

