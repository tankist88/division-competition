package io.github.divisioncompetition.service.impl;

import io.github.divisioncompetition.service.BattleMemberService;
import io.github.divisioncompetition.domain.BattleMember;
import io.github.divisioncompetition.repository.BattleMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing BattleMember.
 */
@Service
@Transactional
public class BattleMemberServiceImpl implements BattleMemberService {

    private final Logger log = LoggerFactory.getLogger(BattleMemberServiceImpl.class);

    private final BattleMemberRepository battleMemberRepository;

    public BattleMemberServiceImpl(BattleMemberRepository battleMemberRepository) {
        this.battleMemberRepository = battleMemberRepository;
    }

    /**
     * Save a battleMember.
     *
     * @param battleMember the entity to save
     * @return the persisted entity
     */
    @Override
    public BattleMember save(BattleMember battleMember) {
        log.debug("Request to save BattleMember : {}", battleMember);
        return battleMemberRepository.save(battleMember);
    }

    /**
     * Get all the battleMembers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BattleMember> findAll() {
        log.debug("Request to get all BattleMembers");
        return battleMemberRepository.findAll();
    }


    /**
     * Get one battleMember by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BattleMember> findOne(Long id) {
        log.debug("Request to get BattleMember : {}", id);
        return battleMemberRepository.findById(id);
    }

    /**
     * Delete the battleMember by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BattleMember : {}", id);
        battleMemberRepository.deleteById(id);
    }
}
