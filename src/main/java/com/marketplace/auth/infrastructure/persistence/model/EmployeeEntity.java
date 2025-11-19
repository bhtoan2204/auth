package com.marketplace.auth.infrastructure.persistence.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "userAccount")
@EqualsAndHashCode(callSuper = true, exclude = "userAccount")
public class EmployeeEntity extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserAccountEntity userAccount;

    @Column(name = "employee_code", unique = true, length = 100)
    private String employeeCode;

    @Column(name = "first_name", length = 255)
    private String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Column(name = "department", length = 255)
    private String department;
}

