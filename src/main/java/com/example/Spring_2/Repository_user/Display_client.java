package com.example.Spring_2.Repository_user;

import com.example.Spring_2.Entity_client.user_manage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Display_client extends JpaRepository<user_manage , Long> {
}
