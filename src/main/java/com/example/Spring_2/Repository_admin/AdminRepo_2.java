package com.example.Spring_2.Repository_admin;
import com.example.Spring_2.Entity_admin.admin_manage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo_2 extends JpaRepository<admin_manage, Long> {

    Optional<admin_manage> findByEmail(String email);


}
