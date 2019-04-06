package io.github.divisioncompetition.repository;

import io.github.divisioncompetition.domain.ResourceProgress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ResourceProgress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceProgressRepository extends JpaRepository<ResourceProgress, Long> {

}
