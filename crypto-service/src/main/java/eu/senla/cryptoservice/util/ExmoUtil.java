package eu.senla.cryptoservice.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@UtilityClass
public class ExmoUtil {

    private static final String HMAC_SHA512 = "HmacSHA512";
    private static long nonce;

    public String getBody() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("nonce", "" + ++nonce);

        String postData = "";
        for (Map.Entry<String, String> argumentsEntry : arguments.entrySet()) {
            Map.Entry argument = (Map.Entry) argumentsEntry;

            if (postData.length() > 0) {
                postData += "&";
            }
            postData += argument.getKey() + "=" + argument.getValue();
        }

        return postData;
    }

    public String getSign(String apiSecret, String body) {
        String sign = null;
        try {
            SecretKeySpec key = new SecretKeySpec(apiSecret.getBytes("UTF-8"), HMAC_SHA512);
            Mac mac = Mac.getInstance(HMAC_SHA512);
            mac.init(key);
            sign = Hex.encodeHexString(mac.doFinal(body.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            log.error("Unsupported encoding exception: " + e);
        } catch (NoSuchAlgorithmException e) {
            log.error("No such algorithm exception: " + e);
        } catch (InvalidKeyException e) {
            log.error("Invalid key exception: " + e);
        }

        return sign;
    }
}