package eu.senla.telegramservice.dto;

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

