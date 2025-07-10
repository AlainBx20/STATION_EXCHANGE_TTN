package com.example.stationdechange.service;

import com.example.stationdechange.entity.PiecesJointes;
import com.example.stationdechange.repository.PiecesJointesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PiecesJointesService {
    @Autowired
    private PiecesJointesRepository repository;

    public List<PiecesJointes> findAll() { return repository.findAll(); }
    public Optional<PiecesJointes> findById(String id) { return repository.findById(id); }
    public PiecesJointes save(PiecesJointes entity) { return repository.save(entity); }
    public void deleteById(String id) { repository.deleteById(id); }
}
