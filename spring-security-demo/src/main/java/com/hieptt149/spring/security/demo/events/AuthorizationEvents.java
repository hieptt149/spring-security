package com.hieptt149.spring.security.demo.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthorizationEvents {

    @EventListener
    public void onDenied(AuthorizationDeniedEvent deniedEvent) {
        log.error("Authorization failed for the user: {} due to: {}", deniedEvent.getAuthentication().get().getName(), deniedEvent.getAuthorizationResult().toString());
    }
}
