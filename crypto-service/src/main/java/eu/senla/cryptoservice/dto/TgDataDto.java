package eu.senla.cryptoservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TgDataDto {

    private String messageType;
}
