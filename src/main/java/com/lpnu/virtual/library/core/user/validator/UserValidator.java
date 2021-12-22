package com.lpnu.virtual.library.core.user.validator;

import com.lpnu.virtual.library.core.user.model.UserDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

@UtilityClass
public class UserValidator {
    public static Boolean validate(UserDto dto) {
        return validateLogin(dto.getLogin())
                && validatePassword(dto.getPassword())
                && validateName(dto.getFirstName())
                && validateName(dto.getLastName());
    }

    private static boolean validateName(String name) {
        return StringUtils.isNotBlank(name);
    }

    private static boolean validatePassword(String password) {
        return StringUtils.isNotBlank(password);
    }

    private static Boolean validateLogin(String login) {
        return StringUtils.isNotBlank(login);
    }
}
