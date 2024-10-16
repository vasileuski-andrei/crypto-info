package eu.senla.shared.dto;

import eu.senla.shared.enums.TgMessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TgDataDto {

    private TgMessageType messageType;
}
