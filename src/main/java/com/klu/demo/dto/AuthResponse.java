package com.klu.demo.dto;

public class AuthResponse {

    private Long id;
    private String name;
    private String role;
    private boolean verified;
    private String token;

    public AuthResponse() {}

    // Constructor WITHOUT token (for register)
    public AuthResponse(Long id, String name, String role, boolean verified) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.verified = verified;
    }

    // Constructor WITH token (for login)
    public AuthResponse(Long id, String name, String role, boolean verified, String token) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.verified = verified;
        this.token = token;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isVerified() { return verified; }
    public void setVerified(boolean verified) { this.verified = verified; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}