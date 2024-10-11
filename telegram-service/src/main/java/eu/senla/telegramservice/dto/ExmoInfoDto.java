package eu.senla.telegramservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExmoInfoDto {

    private String id;
    private String uid;
    private LocalDateTime dateTime;
    private List<String> balances;
}

