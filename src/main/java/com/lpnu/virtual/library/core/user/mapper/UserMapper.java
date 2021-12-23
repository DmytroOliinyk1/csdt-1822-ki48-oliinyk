package com.lpnu.virtual.library.core.user.mapper;

import com.lpnu.virtual.library.core.user.model.User;
import com.lpnu.virtual.library.core.user.model.UserDto;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class UserMapper {
    public static User from(UserDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setLogin(dto.getLogin());
        user.setPassword(
                new BCryptPasswordEncoder().encode(dto.getPassword()));

        return user;
    }
}
