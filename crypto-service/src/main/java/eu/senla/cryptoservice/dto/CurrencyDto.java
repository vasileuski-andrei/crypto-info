package eu.senla.cryptoservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDto {

    private String name;
    private String description;
}

