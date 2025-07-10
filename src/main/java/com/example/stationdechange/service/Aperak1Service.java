package com.example.stationdechange.service;

import com.example.stationdechange.entity.Aperak1;
import com.example.stationdechange.repository.Aperak1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Aperak1Service {
    @Autowired
    private Aperak1Repository repository;

    public List<Aperak1> findAll() { return repository.findAll(); }
    public Optional<Aperak1> findById(String id) { return repository.findById(id); }
    public Aperak1 save(Aperak1 entity) { return repository.save(entity); }
    public void deleteById(String id) { repository.deleteById(id); }
}
