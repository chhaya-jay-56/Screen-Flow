package com.example.Spring_2.Repository_user;

import com.example.Spring_2.Entity_client.user_manage;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<user_manage,Long> {

    void deleteAllById(Long id);

    user_manage findByName(String name);

}
