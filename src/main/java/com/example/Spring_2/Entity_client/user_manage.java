package com.example.Spring_2.Entity_client;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@Table(name = "users_data")
@NoArgsConstructor
@AllArgsConstructor
public class user_manage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String email;

    private String password;

    private long age;

    public String getPrf_img() {
        return prf_img;
    }

    public void setPrf_img(String prf_img) {
        this.prf_img = prf_img;
    }

    public String prf_img;

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    private Boolean verified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

}
