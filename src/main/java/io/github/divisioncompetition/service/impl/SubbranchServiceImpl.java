package io.github.divisioncompetition.service.impl;

import io.github.divisioncompetition.service.SubbranchService;
import io.github.divisioncompetition.domain.Subbranch;
import io.github.divisioncompetition.repository.SubbranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Subbranch.
 */
@Service
@Transactional
public class SubbranchServiceImpl implements SubbranchService {

    private final Logger log = LoggerFactory.getLogger(SubbranchServiceImpl.class);

    private final SubbranchRepository subbranchRepository;

    public SubbranchServiceImpl(SubbranchRepository subbranchRepository) {
        this.subbranchRepository = subbranchRepository;
    }

    /**
     * Save a subbranch.
     *
     * @param subbranch the entity to save
     * @return the persisted entity
     */
    @Override
    public Subbranch save(Subbranch subbranch) {
        log.debug("Request to save Subbranch : {}", subbranch);
        return subbranchRepository.save(subbranch);
    }

    /**
     * Get all the subbranches.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Subbranch> findAll() {
        log.debug("Request to get all Subbranches");
        return subbranchRepository.findAll();
    }


    /**
     * Get one subbranch by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Subbranch> findOne(Long id) {
        log.debug("Request to get Subbranch : {}", id);
        return subbranchRepository.findById(id);
    }

    /**
     * Delete the subbranch by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Subbranch : {}", id);
        subbranchRepository.deleteById(id);
    }
}
