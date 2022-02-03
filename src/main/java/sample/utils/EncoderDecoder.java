package sample.utils;

import java.nio.charset.StandardCharsets;

import org.apache.axis.encoding.Base64;

public class EncoderDecoder {
    public static String encode(String text) {
        return Base64.encode(text.getBytes(StandardCharsets.UTF_8));
    }
}
