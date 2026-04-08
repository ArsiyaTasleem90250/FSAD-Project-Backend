package com.klu.demo.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.klu.demo.dto.RegisterRequest;
import com.klu.demo.entity.User;
import com.klu.demo.repository.UserRepository;
import com.klu.demo.entity.Registration;
import com.klu.demo.repository.RegistrationRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegistrationRepository registrationRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String generateOtp() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    @Transactional
    public User register(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        String otp = generateOtp();
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setRole(req.getRole());
        user.setDepartment(req.getDepartment());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis() + 5 * 60 * 1000);
        user.setVerified(false);
        emailService.sendOtpEmail(user.getEmail(), otp);
        User saved = userRepository.save(user);

        // also store in registration table (keep a single source for roster/reporting)
        // store every signup in registration table for reporting (role distinguishes)
        Registration reg = new Registration();
        reg.setEmail(saved.getEmail());
        reg.setRole(saved.getRole());
        reg.setDepartment(saved.getDepartment());
        reg.setExperience(req.getExperience() != null ? req.getExperience() : 0);
        registrationRepository.save(reg);

        return saved;
    }

    public String verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "User not found";
        if (user.isVerified()) return "Already verified";
        if (user.getOtp() == null || user.getOtpExpiry() == null) return "No OTP found";
        if (user.getOtpExpiry() < System.currentTimeMillis()) return "OTP expired";
        if (!user.getOtp().equals(otp)) return "Invalid OTP";
        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);
        userRepository.save(user);
        return "Verified successfully";
    }

    public String resendOtp(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return "User not found";
        if (user.isVerified()) return "Already verified";
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis() + 5 * 60 * 1000);
        userRepository.save(user);
        emailService.sendOtpEmail(user.getEmail(), otp);
        return "OTP resent successfully";
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) return null;
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        return user;
    }
}
