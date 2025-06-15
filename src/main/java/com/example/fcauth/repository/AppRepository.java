package com.example.fcauth.repository;

import com.example.fcauth.model.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, Long> {
}