package com.example.BookMyMovie.repository;

import com.example.BookMyMovie.table.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,String> {
    @Query("SELECT COUNT(a) > 0 FROM Admin a WHERE a.username = :username AND a.password = :password")
    boolean verify(@Param("username") String username, @Param("password") String password) ;
}
