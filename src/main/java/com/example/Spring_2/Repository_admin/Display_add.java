package com.example.Spring_2.Repository_admin;

import com.example.Spring_2.Entity_admin.admin_manage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Display_add extends JpaRepository<admin_manage, Long> {


}
