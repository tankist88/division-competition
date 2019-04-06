package io.github.divisioncompetition.service.impl;

import io.github.divisioncompetition.service.CurrentMetricService;
import io.github.divisioncompetition.domain.CurrentMetric;
import io.github.divisioncompetition.repository.CurrentMetricRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing CurrentMetric.
 */
@Service
@Transactional
public class CurrentMetricServiceImpl implements CurrentMetricService {

    private final Logger log = LoggerFactory.getLogger(CurrentMetricServiceImpl.class);

    private final CurrentMetricRepository currentMetricRepository;

    public CurrentMetricServiceImpl(CurrentMetricRepository currentMetricRepository) {
        this.currentMetricRepository = currentMetricRepository;
    }

    /**
     * Save a currentMetric.
     *
     * @param currentMetric the entity to save
     * @return the persisted entity
     */
    @Override
    public CurrentMetric save(CurrentMetric currentMetric) {
        log.debug("Request to save CurrentMetric : {}", currentMetric);
        return currentMetricRepository.save(currentMetric);
    }

    /**
     * Get all the currentMetrics.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CurrentMetric> findAll() {
        log.debug("Request to get all CurrentMetrics");
        return currentMetricRepository.findAll();
    }


    /**
     * Get one currentMetric by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurrentMetric> findOne(Long id) {
        log.debug("Request to get CurrentMetric : {}", id);
        return currentMetricRepository.findById(id);
    }

    /**
     * Delete the currentMetric by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurrentMetric : {}", id);
        currentMetricRepository.deleteById(id);
    }
}
