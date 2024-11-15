package eu.senla.cryptoservice.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExmoUtilTest {

    private static final String SIGN = "b63a8bbb1f9d8c552a04b955ef575c0f373d9d9aedd11883392555ebe1b099ea7abd1628fca0787facb34c135fd8396194fad5d4c45e0df067e5417c18416a67";
    private static final String API_SECRET = "123";
    private static final String BODY = "body";

    @Test
    void getSign() {
        // WHEN
        String actual = ExmoUtil.getSign(API_SECRET, BODY);

        // THEN
        assertEquals(SIGN, actual);
    }
}