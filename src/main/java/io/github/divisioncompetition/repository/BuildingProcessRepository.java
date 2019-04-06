package io.github.divisioncompetition.repository;

import io.github.divisioncompetition.domain.BuildingProcess;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BuildingProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuildingProcessRepository extends JpaRepository<BuildingProcess, Long> {

}
