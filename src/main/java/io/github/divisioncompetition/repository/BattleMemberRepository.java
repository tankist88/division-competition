package io.github.divisioncompetition.repository;

import io.github.divisioncompetition.domain.BattleMember;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BattleMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BattleMemberRepository extends JpaRepository<BattleMember, Long> {

}
