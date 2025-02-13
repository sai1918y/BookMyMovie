package com.example.BookMyMovie.table;
import jakarta.persistence.*;

import java.time.LocalDateTime;
@Entity
public class Seatbooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "movie_id", nullable = false)
    private int movieId;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "is_booked", nullable = false)
    private boolean isBooked = false;

    @Column(name = "booked_by")
    private String bookedBy;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public String getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(String bookedBy) {
        this.bookedBy = bookedBy;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
}
