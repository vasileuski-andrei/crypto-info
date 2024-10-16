package eu.senla.shared.dto;

import eu.senla.shared.enums.TgMessageType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TgDataDto {

    private TgMessageType messageType;
}
