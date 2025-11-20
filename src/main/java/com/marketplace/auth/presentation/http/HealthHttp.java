package com.marketplace.auth.presentation.http;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.auth.presentation.http.v1.response.BaseResponse;

import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/health")
public class HealthHttp {
    @GetMapping
    public Mono<BaseResponse<String>> health() {
        return Mono.just(BaseResponse.success("OK"));
    }

    // TODO: tương lai chắc để cái api này trả về thông số cho grafana để monitor
    @GetMapping("/metrics")
    public Mono<BaseResponse<String>> me() {
        return Mono.just(BaseResponse.success("OK"));
    }
}
