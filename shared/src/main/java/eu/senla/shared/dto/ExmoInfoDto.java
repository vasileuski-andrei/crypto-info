package eu.senla.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExmoInfoDto {

    private List<CurrencyDto> currencyList;
    private List<String> balances;
}
