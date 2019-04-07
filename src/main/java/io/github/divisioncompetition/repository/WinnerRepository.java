package io.github.divisioncompetition.repository;

import io.github.divisioncompetition.domain.Winner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Winner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WinnerRepository extends JpaRepository<Winner, Long> {

}
