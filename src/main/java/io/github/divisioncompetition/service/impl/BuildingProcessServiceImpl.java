package io.github.divisioncompetition.service.impl;

import io.github.divisioncompetition.service.BuildingProcessService;
import io.github.divisioncompetition.domain.BuildingProcess;
import io.github.divisioncompetition.repository.BuildingProcessRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing BuildingProcess.
 */
@Service
@Transactional
public class BuildingProcessServiceImpl implements BuildingProcessService {

    private final Logger log = LoggerFactory.getLogger(BuildingProcessServiceImpl.class);

    private final BuildingProcessRepository buildingProcessRepository;

    public BuildingProcessServiceImpl(BuildingProcessRepository buildingProcessRepository) {
        this.buildingProcessRepository = buildingProcessRepository;
    }

    /**
     * Save a buildingProcess.
     *
     * @param buildingProcess the entity to save
     * @return the persisted entity
     */
    @Override
    public BuildingProcess save(BuildingProcess buildingProcess) {
        log.debug("Request to save BuildingProcess : {}", buildingProcess);
        return buildingProcessRepository.save(buildingProcess);
    }

    /**
     * Get all the buildingProcesses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuildingProcess> findAll() {
        log.debug("Request to get all BuildingProcesses");
        return buildingProcessRepository.findAll();
    }


    /**
     * Get one buildingProcess by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BuildingProcess> findOne(Long id) {
        log.debug("Request to get BuildingProcess : {}", id);
        return buildingProcessRepository.findById(id);
    }

    /**
     * Delete the buildingProcess by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BuildingProcess : {}", id);
        buildingProcessRepository.deleteById(id);
    }
}
