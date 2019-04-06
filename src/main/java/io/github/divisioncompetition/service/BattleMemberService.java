package io.github.divisioncompetition.service;

import io.github.divisioncompetition.domain.BattleMember;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BattleMember.
 */
public interface BattleMemberService {

    /**
     * Save a battleMember.
     *
     * @param battleMember the entity to save
     * @return the persisted entity
     */
    BattleMember save(BattleMember battleMember);

    /**
     * Get all the battleMembers.
     *
     * @return the list of entities
     */
    List<BattleMember> findAll();


    /**
     * Get the "id" battleMember.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BattleMember> findOne(Long id);

    /**
     * Delete the "id" battleMember.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
