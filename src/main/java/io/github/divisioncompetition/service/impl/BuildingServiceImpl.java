package io.github.divisioncompetition.service.impl;

import io.github.divisioncompetition.service.BuildingService;
import io.github.divisioncompetition.domain.Building;
import io.github.divisioncompetition.repository.BuildingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Building.
 */
@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

    private final Logger log = LoggerFactory.getLogger(BuildingServiceImpl.class);

    private final BuildingRepository buildingRepository;

    public BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    /**
     * Save a building.
     *
     * @param building the entity to save
     * @return the persisted entity
     */
    @Override
    public Building save(Building building) {
        log.debug("Request to save Building : {}", building);
        return buildingRepository.save(building);
    }

    /**
     * Get all the buildings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Building> findAll() {
        log.debug("Request to get all Buildings");
        return buildingRepository.findAll();
    }


    /**
     * Get one building by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Building> findOne(Long id) {
        log.debug("Request to get Building : {}", id);
        return buildingRepository.findById(id);
    }

    /**
     * Delete the building by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Building : {}", id);
        buildingRepository.deleteById(id);
    }
}
