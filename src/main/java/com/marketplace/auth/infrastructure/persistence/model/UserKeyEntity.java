package com.marketplace.auth.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_key")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"userAccount", "replacedBy"})
@EqualsAndHashCode(callSuper = true, exclude = {"userAccount", "replacedBy"})
public class UserKeyEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccountEntity userAccount;

    @Column(name = "key_type", nullable = false, length = 50)
    private String keyType;

    @Column(name = "key_value", nullable = false, length = 500)
    private String keyValue;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "replaced_by")
    private UserKeyEntity replacedBy;
}

