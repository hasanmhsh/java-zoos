package com.example.javazoos.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HasanAuditing implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String uname = "SYSTEM";
        return Optional.of(uname);
    }
}
