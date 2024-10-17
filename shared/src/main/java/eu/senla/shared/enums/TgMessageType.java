package eu.senla.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TgMessageType {

    MY_INFO("My info"),
    CURRENCY_LIST("Currency list"),
    CONVERSION("Conversion");

    private final String messageType;
}
