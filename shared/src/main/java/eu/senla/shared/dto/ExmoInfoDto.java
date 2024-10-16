package eu.senla.shared.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ExmoInfoDto {

    private List<CurrencyDto> currencyList;
    private List<String> balances;
}
