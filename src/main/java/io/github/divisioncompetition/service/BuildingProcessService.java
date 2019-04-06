package io.github.divisioncompetition.service;

import io.github.divisioncompetition.domain.BuildingProcess;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing BuildingProcess.
 */
public interface BuildingProcessService {

    /**
     * Save a buildingProcess.
     *
     * @param buildingProcess the entity to save
     * @return the persisted entity
     */
    BuildingProcess save(BuildingProcess buildingProcess);

    /**
     * Get all the buildingProcesses.
     *
     * @return the list of entities
     */
    List<BuildingProcess> findAll();


    /**
     * Get the "id" buildingProcess.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<BuildingProcess> findOne(Long id);

    /**
     * Delete the "id" buildingProcess.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
