package io.github.divisioncompetition.repository;

import io.github.divisioncompetition.domain.CurrentMetric;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CurrentMetric entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CurrentMetricRepository extends JpaRepository<CurrentMetric, Long> {

}
