package com.example.demo.repo;

import com.example.demo.models.additionally.UserRole;
import com.example.demo.models.keys.UserRoleKey;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, UserRoleKey> {
}
