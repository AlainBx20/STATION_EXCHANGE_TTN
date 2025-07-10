package com.example.stationdechange.service;

import com.example.stationdechange.entity.Nsh;
import com.example.stationdechange.repository.NshRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NshService {
    @Autowired
    private NshRepository repository;

    public List<Nsh> findAll() { return repository.findAll(); }
    public Optional<Nsh> findById(Long id) { return repository.findById(id); }
    public Nsh save(Nsh entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
