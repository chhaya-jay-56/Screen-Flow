package com.example.Spring_2.Service_Client;

import com.example.Spring_2.Entity_client.user_manage;
import com.example.Spring_2.Repository_user.ClientRepo;
import com.example.Spring_2.Repository_user.ClientRepo2;
import com.example.Spring_2.Repository_user.Display_client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class LoginService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private ClientRepo2 clientRepo2;

    @Autowired
    private Display_client displayClient;


    public user_manage add_user(user_manage UserData) {

        return clientRepo.save(UserData);
    }


    public Optional<user_manage> authenticate(String email, String password) {
        Optional<user_manage> client = clientRepo2.findByEmail(email);

        if (client.isPresent() && client.get().getPassword().equals(password)) {
            return client;
        }
        return Optional.empty();
    }


    public List<user_manage> displayUser() {
        return displayClient.findAll();
    }

    @Transactional
    public void deleteUserbyid(Long id) {
        this.clientRepo.deleteAllById(id);
    }

    public user_manage edituserbyid(long id) {

        Optional<user_manage> optional = clientRepo.findById(id);
        user_manage UserData = null;
        if (optional.isPresent()) {
            UserData = optional.get();

        } else {
            throw new RuntimeException("empolye.found" + id);

        }

        return UserData;
    }

    private final String uploadDir = "C:\\Users\\chhaya jay\\IdeaProjects\\Spring_2\\src\\main\\resources\\static\\Upload";

    public void addUser(String name, MultipartFile prfImg) {
        // Fetch the existing user from the database by name
        user_manage existingUser = clientRepo.findByName(name); // Adjust this method based on your repository implementation

        if (existingUser != null) {
            // Update the name if it is different from the existing name
            if (name != null && !name.isEmpty()) {
                existingUser.setName(name);
            }

            if (prfImg != null && !prfImg.isEmpty()) {
                // Generate a unique filename
                String fileName = UUID.randomUUID().toString() + "-" + prfImg.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);

                try {
                    // Save the image file to the server
                    Files.write(filePath, prfImg.getBytes());
                    // Update the profile image name in the user object
                    existingUser.setPrf_img(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Failed to save image", e);
                }
            }

            // Save the updated user details
            clientRepo.save(existingUser);
        } else {
            throw new RuntimeException("User with the name " + name + " not found.");
        }
    }

    public String getUserProfileImage(String name) {
        user_manage user = clientRepo.findByName(name);
        if (user != null && user.getPrf_img() != null) {
            return "/Upload/" + user.getPrf_img(); // Return the path to the user's uploaded image
        } else {
            return "/images/default_icon.png"; // Return the path to the default profile image
        }
    }

}
