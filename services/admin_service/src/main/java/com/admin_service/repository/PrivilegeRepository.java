package com.admin_service.repository;

import com.admin_service.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    Privilege findByName (String name);

}

