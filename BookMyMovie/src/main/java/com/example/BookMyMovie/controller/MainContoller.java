package com.example.BookMyMovie.controller;

import com.example.BookMyMovie.service.UserService;
import com.example.BookMyMovie.table.Admin;
import com.example.BookMyMovie.table.Movie;
import com.example.BookMyMovie.table.Seatbooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class MainContoller {

    @Autowired
    private UserService userService;

    @PostMapping("/addAdmin")
    public ResponseEntity<Admin> addAdmin(@RequestParam String username, @RequestParam String password){
        return ResponseEntity.ok(userService.addAdmin(username,password));
    }

    @GetMapping("/getAllAdmin")
    public ResponseEntity<Iterable<Admin>>getAllAdmin(){
        return ResponseEntity.ok(userService.getAllAdmin());
    }



    @PostMapping("/addMovie")
    public ResponseEntity<Boolean> addMovie(@RequestParam String username, @RequestParam String password, @RequestParam String title, @RequestParam String director, @RequestParam String description, @RequestParam String genre, @RequestParam LocalDate date, @RequestParam String location, @RequestParam Integer totalSeats, @RequestParam Integer availableSeats, @RequestParam Integer price){
        boolean isMovie=userService.addMovie(username,password,title, director, description,genre,date,location,totalSeats,availableSeats,price);
        return ResponseEntity.ok(isMovie);
    }



    @GetMapping("/getAllMovies")
    public ResponseEntity<Iterable<Movie>> getAllMovies(){
        return ResponseEntity.ok(userService.getAllMovies());
    }

    @GetMapping("/getMovie")
    public ResponseEntity<?> getMovie(@RequestParam Integer id){
        Optional<Movie> movie= userService.getMovieById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deleteMovie")
    public ResponseEntity<Boolean> deleteMovie(@RequestParam String username, @RequestParam String password,@RequestParam Integer id){
        boolean isDelete=userService.deleteMovie(username,password,id);
        return ResponseEntity.ok(isDelete);
    }

    @PostMapping("/bookTicket")
    public ResponseEntity<?> bookTicket(
            @RequestParam Integer id,
            @RequestParam long contact,
            @RequestParam String mail,
            @RequestParam Integer[] seatNumbers,
            @RequestParam LocalDate date) {
        try {
            boolean result = userService.bookTicket(id, contact, mail, seatNumbers, date);
            return ResponseEntity.ok("Ticket booking successful!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }


    @GetMapping("/seatbookings/{movieId}")
    public ResponseEntity<List<Seatbooking>> getAllSeatBookingsByMovieId(@PathVariable int movieId) {
        List<Seatbooking> seatBookings = userService.getAllSeatBookingsByMovieId(movieId);
        if (seatBookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(seatBookings);
    }

    @GetMapping("/available-seats/{movieId}")
    public ResponseEntity<List<Seatbooking>> getAvailableSeats(@PathVariable int movieId) {
        List<Seatbooking> availableSeats = userService.getAvailableSeats(movieId);
        if (availableSeats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(availableSeats);
    }

    @PostMapping("/cancel-seat-booking")
    public ResponseEntity<String> cancelSeatBooking(
            @RequestParam int movieId,
            @RequestParam int seatNumber) {
        Seatbooking canceledSeat = userService.cancelSeatBooking(movieId, seatNumber);
        if (canceledSeat != null) {
            return ResponseEntity.ok("Seat booking canceled successfully.");
        } else {
            return ResponseEntity.badRequest().body("Seat not found or not booked.");
        }
    }

}
