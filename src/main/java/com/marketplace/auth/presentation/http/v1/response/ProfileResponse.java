package com.marketplace.auth.presentation.http.v1.response;

import com.marketplace.auth.domain.aggregate.UserAggregate;

public record ProfileResponse(
                UserAggregate userAggregate) {
}
