package eu.senla.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TgMessageType {

    MY_INFO("my info"),
    CURRENCY_LIST("currency list"),
    CONVERSION("conversion");

    private final String messageType;
}
