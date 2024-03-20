package com.artgallery.db.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 63, unique = true)
    private String nickname;

    @Column
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(length = 127)
    private String country;

    @Column(length = 1023)
    private String about;

    @Column
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public User(String nickname, Date birthDate, String country, String about) {
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.country = country;
        this.about = about;
        this.creationDate = new Date();
    }

    protected User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", birthDate=" + birthDate +
                ", country='" + country + '\'' +
                ", about='" + about + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getCountry() {
        return country;
    }

    public String getAbout() {
        return about;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
