package com.mc.simply.messaging.impl;

import com.mc.simply.messaging.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SenderWithException implements Sender {

    @Override
    public void send(String message) throws IllegalStateException {
        throw new IllegalStateException();
    }
}
