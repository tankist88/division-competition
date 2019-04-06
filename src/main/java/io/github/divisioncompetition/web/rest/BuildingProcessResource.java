package io.github.divisioncompetition.web.rest;
import io.github.divisioncompetition.domain.BuildingProcess;
import io.github.divisioncompetition.service.BuildingProcessService;
import io.github.divisioncompetition.web.rest.errors.BadRequestAlertException;
import io.github.divisioncompetition.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BuildingProcess.
 */
@RestController
@RequestMapping("/api")
public class BuildingProcessResource {

    private final Logger log = LoggerFactory.getLogger(BuildingProcessResource.class);

    private static final String ENTITY_NAME = "buildingProcess";

    private final BuildingProcessService buildingProcessService;

    public BuildingProcessResource(BuildingProcessService buildingProcessService) {
        this.buildingProcessService = buildingProcessService;
    }

    /**
     * POST  /building-processes : Create a new buildingProcess.
     *
     * @param buildingProcess the buildingProcess to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingProcess, or with status 400 (Bad Request) if the buildingProcess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/building-processes")
    public ResponseEntity<BuildingProcess> createBuildingProcess(@RequestBody BuildingProcess buildingProcess) throws URISyntaxException {
        log.debug("REST request to save BuildingProcess : {}", buildingProcess);
        if (buildingProcess.getId() != null) {
            throw new BadRequestAlertException("A new buildingProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildingProcess result = buildingProcessService.save(buildingProcess);
        return ResponseEntity.created(new URI("/api/building-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /building-processes : Updates an existing buildingProcess.
     *
     * @param buildingProcess the buildingProcess to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingProcess,
     * or with status 400 (Bad Request) if the buildingProcess is not valid,
     * or with status 500 (Internal Server Error) if the buildingProcess couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/building-processes")
    public ResponseEntity<BuildingProcess> updateBuildingProcess(@RequestBody BuildingProcess buildingProcess) throws URISyntaxException {
        log.debug("REST request to update BuildingProcess : {}", buildingProcess);
        if (buildingProcess.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BuildingProcess result = buildingProcessService.save(buildingProcess);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, buildingProcess.getId().toString()))
            .body(result);
    }

    /**
     * GET  /building-processes : get all the buildingProcesses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buildingProcesses in body
     */
    @GetMapping("/building-processes")
    public List<BuildingProcess> getAllBuildingProcesses() {
        log.debug("REST request to get all BuildingProcesses");
        return buildingProcessService.findAll();
    }

    /**
     * GET  /building-processes/:id : get the "id" buildingProcess.
     *
     * @param id the id of the buildingProcess to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingProcess, or with status 404 (Not Found)
     */
    @GetMapping("/building-processes/{id}")
    public ResponseEntity<BuildingProcess> getBuildingProcess(@PathVariable Long id) {
        log.debug("REST request to get BuildingProcess : {}", id);
        Optional<BuildingProcess> buildingProcess = buildingProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buildingProcess);
    }

    /**
     * DELETE  /building-processes/:id : delete the "id" buildingProcess.
     *
     * @param id the id of the buildingProcess to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/building-processes/{id}")
    public ResponseEntity<Void> deleteBuildingProcess(@PathVariable Long id) {
        log.debug("REST request to delete BuildingProcess : {}", id);
        buildingProcessService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
