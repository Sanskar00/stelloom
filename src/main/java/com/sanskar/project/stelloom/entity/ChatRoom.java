package com.sanskar.project.stelloom.entity;

import com.sanskar.project.stelloom.entity.enums.RoomType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "chat_rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 10)
    private RoomType roomType;

    @Column(length = 100)
    private String name;

    @Column(name = "direct_key", length = 50)
    private String directKey;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    @NotNull
    private Instant createdAt;

}
