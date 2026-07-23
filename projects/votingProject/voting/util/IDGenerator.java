package util;

import java.util.UUID;

public class IDGenerator {

    public static String generateId(String prefix) {
        return prefix +
                "-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0,2)
                        .toUpperCase();
    }

}
