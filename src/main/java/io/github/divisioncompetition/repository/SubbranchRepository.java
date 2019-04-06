package io.github.divisioncompetition.repository;

import io.github.divisioncompetition.domain.Subbranch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Subbranch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubbranchRepository extends JpaRepository<Subbranch, Long> {

}
