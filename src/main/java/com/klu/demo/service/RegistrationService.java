package com.klu.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klu.demo.entity.Registration;
    import com.klu.demo.repository.RegistrationRepository;
import java.util.List;

@Service
public class RegistrationService {
    @Autowired private RegistrationRepository repo;
    public Registration add(Registration r){ return repo.save(r); }
    public List<Registration> all(){ return repo.findAll(); }
}
