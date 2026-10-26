package com.sanskar.project.stelloom.entity;

import com.sanskar.project.stelloom.entity.enums.PostType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name="posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(name = "type",nullable = false, length = 20)
    @Builder.Default
    private PostType postType=PostType.POST;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(name = "media_key", length = 500)
    private String mediaKey;

    @Column(name="media_content_type", length = 100)
    private String mediaContentType;

    @Column(nullable = false)
    @Builder.Default
    private boolean pinned=false;

    @Column(name = "like_count", nullable = false)
    @Builder.Default
    private int likeCount=0;

    @Column(name="comment_count", nullable = false)
    @Builder.Default
    private int commentCount=0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}

