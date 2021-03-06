package ru.kpfu.itis.rodsher.guessbot.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ClientType clientType;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ChannelType type;

    @Column(name = "created_at")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Timestamp createdAt;
}