package com.example.Spring_2.Repository_user;

import com.example.Spring_2.Entity_admin.admin_manage;
import com.example.Spring_2.Entity_client.user_manage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo2 extends JpaRepository<user_manage, Long> {

    Optional<user_manage> findByEmail(String email);
}
