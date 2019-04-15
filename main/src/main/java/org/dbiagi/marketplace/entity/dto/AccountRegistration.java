package org.dbiagi.marketplace.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.dbiagi.marketplace.entity.Account;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRegistration {
    public String name;
    public String username;
    public String email;
    public String plainPassword;
    public Account.Type type;
}
