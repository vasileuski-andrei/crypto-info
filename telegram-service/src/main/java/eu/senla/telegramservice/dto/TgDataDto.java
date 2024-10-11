package eu.senla.telegramservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TgDataDto {

    private String messageType;
}
