package com.example.BookMyMovie.service;

import com.example.BookMyMovie.repository.AdminRepository;
import com.example.BookMyMovie.table.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin addAdmin(String username, String password) {
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        adminRepository.save(admin);
        return admin;
    }

    public Iterable<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }
}
