package com.lpnu.virtual.library.core.user.repository;

import com.lpnu.virtual.library.core.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
