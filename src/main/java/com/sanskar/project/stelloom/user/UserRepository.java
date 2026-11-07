package com.sanskar.project.stelloom.user;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.OptionalInt;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByOrganizationSlugAndUsername(String slug, String username);
    Optional<User> findByOrganizationIdAndId(Long orgId, Long id);
    Optional<User> findByUsername(String username);
    boolean existsByOrganizationIdAndUsername(Long organizationId, String username);
    boolean existsByOrganizationIdAndEmail(Long organizationId, String email);
}
