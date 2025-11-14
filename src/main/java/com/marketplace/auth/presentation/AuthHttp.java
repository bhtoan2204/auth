package com.marketplace.auth.presentation;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthHttp {
    @GetMapping("/health")
    public String health() throws UnknownHostException {
        return "OK - " + InetAddress.getLocalHost().getHostAddress();
    }
}
