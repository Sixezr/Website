package ru.sixzr.module.managers;

import java.util.UUID;

public class TokenManager {
    public String generateToken() {
        return String.valueOf(UUID.randomUUID());
    }
}
