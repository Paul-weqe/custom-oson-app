package org.weqe.app;

import java.time.Instant;
import java.time.LocalDateTime;

public class Trial {
    public static void main(String[] args) {
        long millis = Instant.now().toEpochMilli();
        System.out.println(millis);
    }
}
