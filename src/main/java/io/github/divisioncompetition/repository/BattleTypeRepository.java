package io.github.divisioncompetition.repository;

import io.github.divisioncompetition.domain.BattleType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BattleType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BattleTypeRepository extends JpaRepository<BattleType, Long> {

}
