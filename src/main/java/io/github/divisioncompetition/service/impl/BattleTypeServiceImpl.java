package io.github.divisioncompetition.service.impl;

import io.github.divisioncompetition.service.BattleTypeService;
import io.github.divisioncompetition.domain.BattleType;
import io.github.divisioncompetition.repository.BattleTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing BattleType.
 */
@Service
@Transactional
public class BattleTypeServiceImpl implements BattleTypeService {

    private final Logger log = LoggerFactory.getLogger(BattleTypeServiceImpl.class);

    private final BattleTypeRepository battleTypeRepository;

    public BattleTypeServiceImpl(BattleTypeRepository battleTypeRepository) {
        this.battleTypeRepository = battleTypeRepository;
    }

    /**
     * Save a battleType.
     *
     * @param battleType the entity to save
     * @return the persisted entity
     */
    @Override
    public BattleType save(BattleType battleType) {
        log.debug("Request to save BattleType : {}", battleType);
        return battleTypeRepository.save(battleType);
    }

    /**
     * Get all the battleTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BattleType> findAll() {
        log.debug("Request to get all BattleTypes");
        return battleTypeRepository.findAll();
    }


    /**
     * Get one battleType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BattleType> findOne(Long id) {
        log.debug("Request to get BattleType : {}", id);
        return battleTypeRepository.findById(id);
    }

    /**
     * Delete the battleType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BattleType : {}", id);
        battleTypeRepository.deleteById(id);
    }
}
