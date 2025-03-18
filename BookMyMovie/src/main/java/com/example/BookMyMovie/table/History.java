package com.example.BookMyMovie.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;
@Entity
public class History {
    @Id
    private long contact;

    @Column(name = "movieid")
    private Integer movieid;

    @Column(name = "bookedseats")
    private int bookedseats;

    @Column(name = "bookingdate")
    private LocalDate bookingdate;

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public Integer getMovieid() {
        return movieid;
    }

    public void setMovieid(Integer movieid) {
        this.movieid = movieid;
    }

    public int getBookedseats() {
        return bookedseats;
    }

    public void setBookedseats(int bookedseats) {
        this.bookedseats = bookedseats;
    }

    public LocalDate getBookingdate() {
        return bookingdate;
    }

    public void setBookingdate(LocalDate bookingdate) {
        this.bookingdate = bookingdate;
    }
}
