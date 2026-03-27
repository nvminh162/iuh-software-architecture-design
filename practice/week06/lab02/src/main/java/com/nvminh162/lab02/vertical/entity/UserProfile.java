package com.nvminh162.lab02.vertical.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    private Long userId; // Chia sẻ chung ID từ Account
    
    @Lob
    private byte[] avatar;
    
    private String bio;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId 
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public byte[] getAvatar() { return avatar; }
    public void setAvatar(byte[] avatar) { this.avatar = avatar; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public UserAccount getUserAccount() { return userAccount; }
    public void setUserAccount(UserAccount userAccount) { this.userAccount = userAccount; }
}
