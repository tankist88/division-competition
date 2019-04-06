package io.github.divisioncompetition.service.impl;

import io.github.divisioncompetition.service.ResourceProgressService;
import io.github.divisioncompetition.domain.ResourceProgress;
import io.github.divisioncompetition.repository.ResourceProgressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ResourceProgress.
 */
@Service
@Transactional
public class ResourceProgressServiceImpl implements ResourceProgressService {

    private final Logger log = LoggerFactory.getLogger(ResourceProgressServiceImpl.class);

    private final ResourceProgressRepository resourceProgressRepository;

    public ResourceProgressServiceImpl(ResourceProgressRepository resourceProgressRepository) {
        this.resourceProgressRepository = resourceProgressRepository;
    }

    /**
     * Save a resourceProgress.
     *
     * @param resourceProgress the entity to save
     * @return the persisted entity
     */
    @Override
    public ResourceProgress save(ResourceProgress resourceProgress) {
        log.debug("Request to save ResourceProgress : {}", resourceProgress);
        return resourceProgressRepository.save(resourceProgress);
    }

    /**
     * Get all the resourceProgresses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResourceProgress> findAll() {
        log.debug("Request to get all ResourceProgresses");
        return resourceProgressRepository.findAll();
    }


    /**
     * Get one resourceProgress by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceProgress> findOne(Long id) {
        log.debug("Request to get ResourceProgress : {}", id);
        return resourceProgressRepository.findById(id);
    }

    /**
     * Delete the resourceProgress by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResourceProgress : {}", id);
        resourceProgressRepository.deleteById(id);
    }
}
