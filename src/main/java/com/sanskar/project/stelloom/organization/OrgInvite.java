package com.sanskar.project.stelloom.organization;

import com.sanskar.project.stelloom.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity @Table(name = "org_invites") @Setter @Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OrgInvite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organizationId;

    @Column(name = "code_hash", length = 64, nullable = false)
    private String codeHash;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="created_by", nullable = false)
    private User createdBy;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "max_uses")
    private Integer maxUses;

    @Column(name="use_count", nullable = false)
    private Integer useCount;

    @CreationTimestamp
    @Column(name="created_at",nullable = false,updatable = false)
    private Instant createdAt;

    @Builder.Default
    @Column(nullable = false)
    private boolean revoked=false;
}
