package com.marketplace.auth.application.user.command;

import com.marketplace.auth.domain.aggregate.UserAggregate;

public record GetProfileCommandResult(UserAggregate userAggregate) {
    public GetProfileCommandResult(UserAggregate userAggregate) {
        this.userAggregate = userAggregate;
    }
}
