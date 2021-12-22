package com.lpnu.virtual.library.core.user.model;

import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}
