package com.example.BookMyMovie.controller;

import com.example.BookMyMovie.service.UserService;
import com.example.BookMyMovie.table.Admin;
import com.example.BookMyMovie.table.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class MainContoller {

    @Autowired
    private UserService userService;

    @PostMapping("/addAdmin")
    public ResponseEntity<Admin> addadmin(@RequestParam String username, @RequestParam String password){
        return ResponseEntity.ok(userService.addAdmin(username,password));
    }

    @GetMapping("/getalladmin")
    public ResponseEntity<Iterable<Admin>>getAllAdmin(){
        return ResponseEntity.ok(userService.getAllAdmin());
    }

    @PostMapping("/addmovie")
    public ResponseEntity<Boolean> addmovie(@RequestParam String username, @RequestParam String password, @RequestParam String title, @RequestParam String director, @RequestParam String description, @RequestParam String genre, @RequestParam LocalDate date, @RequestParam String location, @RequestParam Integer totalSeats, @RequestParam Integer availableSeats, @RequestParam Integer price){
        boolean isMovie=userService.addMovie(username,password,title, director, description,genre,date,location,totalSeats,availableSeats,price);
        return ResponseEntity.ok(isMovie);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Movie>> getAllMovies(){
        return ResponseEntity.ok(userService.getAllMovies());
    }
}
