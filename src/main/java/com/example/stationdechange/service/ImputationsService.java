package com.example.stationdechange.service;

import com.example.stationdechange.entity.Imputations;
import com.example.stationdechange.repository.ImputationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImputationsService {
    @Autowired
    private ImputationsRepository repository;

    public List<Imputations> findAll() { return repository.findAll(); }
    public Optional<Imputations> findById(Long id) { return repository.findById(id); }
    public Imputations save(Imputations entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
