package com.example.stationdechange.service;

import com.example.stationdechange.entity.Titres;
import com.example.stationdechange.repository.TitresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TitresService {
    @Autowired
    private TitresRepository repository;

    public List<Titres> findAll() { return repository.findAll(); }
    public Optional<Titres> findById(String id) { return repository.findById(id); }
    public Titres save(Titres entity) { return repository.save(entity); }
    public void deleteById(String id) { repository.deleteById(id); }
}
