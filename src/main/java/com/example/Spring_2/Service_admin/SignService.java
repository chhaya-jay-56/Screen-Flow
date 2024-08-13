package com.example.Spring_2.Service_admin;

import com.example.Spring_2.Entity_admin.admin_manage;
import com.example.Spring_2.Repository_admin.AdminRepo;
import com.example.Spring_2.Repository_admin.AdminRepo_2;
import com.example.Spring_2.Repository_admin.Display_add;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SignService {

    @Autowired
    private AdminRepo Admin_Repo;

    public admin_manage forms(admin_manage admin) {

        return Admin_Repo.save(admin);
    }

    @Autowired
    private AdminRepo_2 admin_repo_2;

    public Optional<admin_manage> authenticate(String email, String password) {
        Optional<admin_manage> admin = admin_repo_2.findByEmail(email);

        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return admin;
        }
        return Optional.empty();
    }

    @Autowired
    private Display_add displayAdd;

    public List<admin_manage> displayAdmin() {
        return displayAdd.findAll();
    }

    @Transactional
    public void deleteadminbyid(Long id) {
        this.Admin_Repo.deleteAllById(id);
    }

    public admin_manage editadminbyid(Long id)
    {
        Optional<admin_manage> optional= Admin_Repo.findById(id) ;
        admin_manage admin=null ;
        if (optional.isPresent()) {
            admin= optional.get();

        }
        else {
            throw new RuntimeException("empolye.found" +id);

        }

        return admin;
    }

}