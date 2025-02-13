package com.example.BookMyMovie.service;

import com.example.BookMyMovie.repository.AdminRepository;
import com.example.BookMyMovie.repository.MovieRepo;
import com.example.BookMyMovie.table.Admin;
import com.example.BookMyMovie.table.Movie;
import com.example.BookMyMovie.table.Seatbooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private com.example.BookMyMovie.repository.SeatbookingRepository seatbookingRepository;

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

    public boolean addMovie(String username, String password, String title, String director, String description, String genre, LocalDate date, String location, Integer totalSeats, Integer availableSeats, Integer price) {
        if (verifyAdmin(username, password)) {
            Movie movie = new Movie();
            movie.setTitle(title);
            movie.setDirector(director);
            movie.setDescription(description);
            movie.setGenre(genre);
            movie.setDate(date);
            movie.setLocation(location);
            movie.setTotalseats(totalSeats);
            movie.setAvailableseats(availableSeats);
            movie.setPrice(price);
            movieRepo.save(movie);


            for (int i = 1; i <= totalSeats; i++) {
                com.example.BookMyMovie.table.Seatbooking seat = new Seatbooking();
                seat.setMovieId(movie.getId());
                seat.setSeatNumber(i);
                seat.setBooked(false);
                seatbookingRepository.save(seat);
            }

            return true;
        }
        return false;
    }

    public boolean verifyAdmin(String username, String password) {
        return adminRepository.verify(username, password);
    }

    public Iterable<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    //movieRepo.deleteseat(id, seatNumbers.length);

}
