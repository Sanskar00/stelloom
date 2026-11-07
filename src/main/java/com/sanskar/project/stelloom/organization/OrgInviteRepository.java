package com.sanskar.project.stelloom.organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OrgInviteRepository extends JpaRepository<OrgInvite, Long> {
    Optional<OrgInvite> findByCodeHash(String codeHash);

    Optional<OrgInvite> findByIdAndOrganizationId(Long id, Long organizationId);

    List<OrgInvite> findTop50ByOrganizationIdOrderByCreatedAtDesc(Long organizationId);

    // Atomic: returns 1 if the use was counted, 0 if the invite is no longer valid.
    @Modifying
    @Query("""
        update OrgInvite i set i.useCount = i.useCount + 1
        where i.id = :id
          and i.revoked = false
          and i.expiresAt > :now
          and (i.maxUses is null or i.useCount < i.maxUses)
        """)
    int redeem(@Param("id") Long id, @Param("now") Instant now);
}
