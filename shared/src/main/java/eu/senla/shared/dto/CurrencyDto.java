package eu.senla.shared.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CurrencyDto {

    private String name;
    private String description;
}
