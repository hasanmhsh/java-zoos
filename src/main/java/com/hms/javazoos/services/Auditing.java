package com.hms.javazoos.services;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Auditing implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor()
    {
        String uname;
//        Authentication authentication = SecurityContextHolder.getContext()
//                .getAuthentication();
//        if (authentication != null)
//        {
//            uname = authentication.getName();
//        } else
//        {
            uname = "SYSTEM";
//        }
        return Optional.of(uname);
    }
}
