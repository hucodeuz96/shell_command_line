package com.example.shell_command_line;

import lombok.*;

import java.math.BigInteger;

/**
 * @author "Husniddin Ulachov"
 * @created 9:56 PM on 9/8/2022
 * @project shell_command_line
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthUser {
    private String username;
    private String password;
    private BigInteger balance;
}
