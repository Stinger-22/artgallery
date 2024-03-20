package com.artgallery.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 63, unique = true)
    private String nickname;

    @Column
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @Column(length = 127)
    private String country;

    @Column(length = 1023)
    private String about;

    @Column(updatable = false)
    @CreatedDate
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    public User(String nickname, Date birthDate, String country, String about) {
        this.nickname = nickname;
        this.birthDate = birthDate;
        this.country = country;
        this.about = about;
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
