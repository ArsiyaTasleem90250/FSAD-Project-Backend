package com.klu.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.demo.entity.User;
import com.klu.demo.repository.UserRepository;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    // 🔢 Generate OTP
    public String generateOtp() {
        int otp = new Random().nextInt(900000) + 100000;
        return String.valueOf(otp);
    }

    // 🔍 Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // 💾 Save/Update user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // 📝 REGISTER (with OTP)
    public User register(User user) {
        String otp = generateOtp();

        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis() + (5 * 60 * 1000)); // 5 mins
        user.setVerified(false);

        // TODO: send email here
//        System.out.println("OTP: " + otp);
        emailService.sendOtpEmail(user.getEmail(), otp);

        return userRepository.save(user);
    }

    // ✅ VERIFY OTP
    public String verifyOtp(String email, String otp) {
        User user = findByEmail(email);

        if (user == null) return "User not found";

        if (user.getOtp() == null) return "No OTP found";

        if (!user.getOtp().equals(otp)) return "Invalid OTP";

        if (user.getOtpExpiry() < System.currentTimeMillis()) return "OTP expired";

        user.setVerified(true);
        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return "Verified successfully";
    }

    // 🔁 RESEND OTP
    public String resendOtp(String email) {
        User user = findByEmail(email);

        if (user == null) return "User not found";

        String otp = generateOtp();

        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis() + (5 * 60 * 1000));

        userRepository.save(user);

        // TODO: send email here
//        System.out.println("Resent OTP: " + otp);
        emailService.sendOtpEmail(user.getEmail(), otp);

        return "OTP resent successfully";
    }

    // 🔐 LOGIN
    public User login(String email, String password) {
        User user = findByEmail(email);

        if (user == null) return null;

        if (!user.isVerified()) {
            throw new RuntimeException("Please verify your email first");
        }

        if (user.getPassword().equals(password)) {
            return user;
        }

        return null;
    }
}