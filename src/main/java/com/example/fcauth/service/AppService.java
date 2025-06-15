package com.example.fcauth.service;

import com.example.fcauth.model.App;
import com.example.fcauth.repository.AppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {
    private final AppRepository appRepository;

    public List<App> listApps() {
        return appRepository.findAll();
    }
}