package com.lalit.securityDemo.repository;

import com.lalit.securityDemo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users , Long> {
    Users findByUserName(String username);
}
