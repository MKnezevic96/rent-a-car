package com.admin_service.repository;

import com.admin_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName (String name);
}
