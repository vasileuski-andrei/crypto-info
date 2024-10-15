package eu.senla.telegramservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExmoInfoDto {

    private List<CurrencyDto> currencyList;
    private List<String> balances;
}

