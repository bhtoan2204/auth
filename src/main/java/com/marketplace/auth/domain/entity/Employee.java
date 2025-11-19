package com.marketplace.auth.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Employee {

    private Integer id;
    private Integer userId;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String department;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

