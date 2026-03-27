package com.nvminh162.lab02.vertical.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String password;

    // LAZY fetching is crucial for vertical partitioning
    @OneToOne(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserProfile profile;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public UserProfile getProfile() { return profile; }
    public void setProfile(UserProfile profile) { this.profile = profile; }
}
