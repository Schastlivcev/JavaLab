package ru.kpfu.itis.rodsher.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 70, nullable = false, unique = true)
    private String email;
    @Column(length = 60, nullable = false)
    private String password;
    @Column(length = 30, nullable = false)
    private String name;
    @Column(length = 60, nullable = false)
    private String surname;
    @Column(name = "is_man", nullable = false)
    private Boolean isMan;
    @Column(nullable = false)
    private Date birthday;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    @Column(length = 60)
    private String status;
    @Column
    private String bio;
    @Column(columnDefinition = "text")
    private String image;
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private Boolean verified;
    @Column(name = "created_at")//, columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdAt;
}
