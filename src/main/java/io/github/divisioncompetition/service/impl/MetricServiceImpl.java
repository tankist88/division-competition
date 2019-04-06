package io.github.divisioncompetition.service.impl;

import io.github.divisioncompetition.service.MetricService;
import io.github.divisioncompetition.domain.Metric;
import io.github.divisioncompetition.repository.MetricRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Metric.
 */
@Service
@Transactional
public class MetricServiceImpl implements MetricService {

    private final Logger log = LoggerFactory.getLogger(MetricServiceImpl.class);

    private final MetricRepository metricRepository;

    public MetricServiceImpl(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    /**
     * Save a metric.
     *
     * @param metric the entity to save
     * @return the persisted entity
     */
    @Override
    public Metric save(Metric metric) {
        log.debug("Request to save Metric : {}", metric);
        return metricRepository.save(metric);
    }

    /**
     * Get all the metrics.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Metric> findAll() {
        log.debug("Request to get all Metrics");
        return metricRepository.findAll();
    }


    /**
     * Get one metric by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Metric> findOne(Long id) {
        log.debug("Request to get Metric : {}", id);
        return metricRepository.findById(id);
    }

    /**
     * Delete the metric by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Metric : {}", id);
        metricRepository.deleteById(id);
    }
}
