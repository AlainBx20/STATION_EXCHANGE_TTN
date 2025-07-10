package com.example.stationdechange.service;

import com.example.stationdechange.entity.Aperak0;
import com.example.stationdechange.repository.Aperak0Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Aperak0Service {
    @Autowired
    private Aperak0Repository repository;

    public List<Aperak0> findAll() { return repository.findAll(); }
    public Optional<Aperak0> findById(String id) { return repository.findById(id); }
    public Aperak0 save(Aperak0 entity) { return repository.save(entity); }
    public void deleteById(String id) { repository.deleteById(id); }
}
