package com.example.BookMyMovie.controller;

import com.example.BookMyMovie.service.UserService;
import com.example.BookMyMovie.table.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainContoller {

    @Autowired
    private UserService userService;

    @PostMapping("/addadmin")
    public ResponseEntity<Admin> addadmin(@RequestParam String username, @RequestParam String password){
        return ResponseEntity.ok(userService.addAdmin(username,password));
    }

    @GetMapping("/getalladmin")
    public ResponseEntity<Iterable<Admin>>getAllAdmin(){
        return ResponseEntity.ok(userService.getAllAdmin());
    }
}
