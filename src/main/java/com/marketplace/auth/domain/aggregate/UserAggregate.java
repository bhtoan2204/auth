package com.marketplace.auth.domain.aggregate;

import java.util.ArrayList;
import java.util.List;

import com.marketplace.auth.domain.entity.Customer;
import com.marketplace.auth.domain.entity.Employee;
import com.marketplace.auth.domain.entity.Role;
import com.marketplace.auth.domain.entity.User;

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
public class UserAggregate {

    private User user;
    private Customer customer;
    private Employee employee;
    @Builder.Default
    private List<Role> roles = new ArrayList<>();

    public boolean isCustomer() {
        return customer != null;
    }

    public boolean isEmployee() {
        return employee != null;
    }

    public boolean hasRole(String roleName) {
        return roles.stream()
                .anyMatch(role -> role.getName().equals(roleName));
    }

    public boolean hasAnyRole(List<String> roleNames) {
        return roles.stream()
                .anyMatch(role -> roleNames.contains(role.getName()));
    }

    public void addRole(Role role) {
        if (role != null && !hasRole(role.getName())) {
            roles.add(role);
        }
    }

    public void removeRole(Role role) {
        if (role != null) {
            roles.removeIf(r -> r.getId().equals(role.getId()));
        }
    }

    public void clearRoles() {
        roles.clear();
    }

    public boolean isActive() {
        return user != null && Boolean.TRUE.equals(user.getIsActive());
    }

    public boolean isBanned() {
        return user != null && Boolean.TRUE.equals(user.getIsBanned());
    }

    public boolean isLocked() {
        return user != null && user.getLockedUntil() != null 
                && user.getLockedUntil().isAfter(java.time.LocalDateTime.now());
    }

    public boolean canLogin() {
        return isActive() && !isBanned() && !isLocked();
    }
}
