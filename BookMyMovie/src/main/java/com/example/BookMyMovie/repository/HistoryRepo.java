package com.example.BookMyMovie.repository;


import com.example.BookMyMovie.table.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepo extends JpaRepository<History,Integer> {
}
