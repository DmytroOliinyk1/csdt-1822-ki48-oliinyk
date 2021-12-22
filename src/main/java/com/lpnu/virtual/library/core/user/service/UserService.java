package com.lpnu.virtual.library.core.user.service;

import com.lpnu.virtual.library.core.user.mapper.UserMapper;
import com.lpnu.virtual.library.core.user.model.Role;
import com.lpnu.virtual.library.core.user.model.Roles;
import com.lpnu.virtual.library.core.user.model.User;
import com.lpnu.virtual.library.core.user.model.UserDto;
import com.lpnu.virtual.library.core.user.repository.RoleRepository;
import com.lpnu.virtual.library.core.user.repository.UserRepository;
import com.lpnu.virtual.library.core.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public Boolean createUser(UserDto userDto) {
        if (!UserValidator.validate(userDto)) {
            return Boolean.FALSE;
        }

        User user = UserMapper.from(userDto);
        user.setEnabled(Boolean.TRUE);
        user = userRepo.save(user);
        if (user.getId() != null) {
            roleRepo.save(getDefaultRole(user));
        }

        return Boolean.TRUE;
    }

    private Role getDefaultRole(User user) {
        Role role = new Role();
        role.setRole(Roles.USER);
        role.setUser(user);
        return role;
    }
}
