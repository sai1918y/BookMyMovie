package com.example.BookMyMovie.repository;

import com.example.BookMyMovie.table.Movie;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends JpaRepository<Movie,Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Movie m WHERE m.id = :id")
    void deleteMovieById(Integer id);
    @Modifying
    @Transactional
    @Query("update Movie m set m.availableseats=m.availableseats-:seats where m.id=:id")
    void deleteseat(Integer id,Integer seats);
}
