package com.example.BookMyMovie.repository;
import com.example.BookMyMovie.table.Seatbooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface SeatbookingRepository extends JpaRepository<Seatbooking, Integer> {

    List<Seatbooking> findByMovieId(int movieId);

    List<Seatbooking> findByMovieIdAndIsBooked(int movieId, boolean isBooked);

    Seatbooking findByMovieIdAndSeatNumber(int movieId, int seatNumber);
}
