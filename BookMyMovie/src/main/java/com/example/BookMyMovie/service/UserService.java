package com.example.BookMyMovie.service;

import com.example.BookMyMovie.repository.AdminRepository;
import com.example.BookMyMovie.repository.HistoryRepo;
import com.example.BookMyMovie.repository.MovieRepo;
import com.example.BookMyMovie.repository.SeatbookingRepository;
import com.example.BookMyMovie.table.Admin;
import com.example.BookMyMovie.table.History;
import com.example.BookMyMovie.table.Movie;
import com.example.BookMyMovie.table.Seatbooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private HistoryRepo historyRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private SeatbookingRepository seatbookingRepository;

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

    public Optional<Movie> getMovieById(Integer id) {
        return movieRepo.findById(id);
    }

    public boolean deleteMovie(String username, String password, Integer id) {
        Optional<Movie> movie = movieRepo.findById(id);
        if (movie.isPresent() && verifyAdmin(username, password)) {
            movieRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean bookTicket(Integer id, long contact, String mail, Integer[] seatNumbers, LocalDate date) {
        Optional<Movie> movie = movieRepo.findById(id);
        if (!movie.isPresent()) {
            throw new IllegalArgumentException("Movie not found.");
        }

        int availableSeats = movie.get().getAvailableseats();
        if (seatNumbers.length > availableSeats) {
            throw new IllegalArgumentException(availableSeats + " seats available at this time.");
        }

        for (Integer seatNumber : seatNumbers) {
            Seatbooking seat = seatbookingRepository.findByMovieIdAndSeatNumber(id, seatNumber);
            if (seat == null || seat.isBooked()) {
                throw new IllegalArgumentException("Seat number " + seatNumber + " is already booked or does not exist.");
            }
        }

        movieRepo.deleteseat(id, seatNumbers.length);

        for (Integer seatNumber : seatNumbers) {
            Seatbooking seat = seatbookingRepository.findByMovieIdAndSeatNumber(id, seatNumber);
            seat.setBooked(true);
            seat.setBookedBy(mail);
            seat.setBookingDate(LocalDateTime.now());
            seatbookingRepository.save(seat);
        }

        History history = new History();
        history.setMovieid(id);
        history.setBookedseats(seatNumbers.length);
        history.setBookingdate(date);
        history.setContact(contact);
        historyRepo.save(history);

        sendMail(mail, seatNumbers.length, date, contact);

        return true;
    }

    public String sendMail(String mail, Integer seats, LocalDate date, long contact) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(mail);
            mailMessage.setSubject("Booking Confirmation");
            mailMessage.setText("Seats: " + seats + "\nDate: " + date + "\nContact: " + contact);
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully.";
        } catch (Exception e) {
            return "Error while Sending Mail.";
        }
    }


    public List<Seatbooking> getAllSeatBookingsByMovieId(int movieId) {
        return seatbookingRepository.findByMovieId(movieId);
    }

    public List<Seatbooking> getAvailableSeats(int movieId) {
        return seatbookingRepository.findByMovieIdAndIsBooked(movieId, false);
    }

    public Seatbooking cancelSeatBooking(int movieId, int seatNumber) {
        Seatbooking seat = seatbookingRepository.findByMovieIdAndSeatNumber(movieId, seatNumber);

        if (seat != null && seat.isBooked()) {
            seat.setBooked(false);
            seat.setBookedBy(null);
            seat.setBookingDate(null);
            return seatbookingRepository.save(seat);
        }
        return null;
    }

    //movieRepo.deleteseat(id, seatNumbers.length);

}
