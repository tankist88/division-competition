package io.github.divisioncompetition.web.rest;
import io.github.divisioncompetition.domain.Building;
import io.github.divisioncompetition.service.BuildingService;
import io.github.divisioncompetition.web.rest.errors.BadRequestAlertException;
import io.github.divisioncompetition.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Building.
 */
@RestController
@RequestMapping("/api")
public class BuildingResource {

    private final Logger log = LoggerFactory.getLogger(BuildingResource.class);

    private static final String ENTITY_NAME = "building";

    private final BuildingService buildingService;

    public BuildingResource(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    /**
     * POST  /buildings : Create a new building.
     *
     * @param building the building to create
     * @return the ResponseEntity with status 201 (Created) and with body the new building, or with status 400 (Bad Request) if the building has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/buildings")
    public ResponseEntity<Building> createBuilding(@Valid @RequestBody Building building) throws URISyntaxException {
        log.debug("REST request to save Building : {}", building);
        if (building.getId() != null) {
            throw new BadRequestAlertException("A new building cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Building result = buildingService.save(building);
        return ResponseEntity.created(new URI("/api/buildings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buildings : Updates an existing building.
     *
     * @param building the building to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated building,
     * or with status 400 (Bad Request) if the building is not valid,
     * or with status 500 (Internal Server Error) if the building couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/buildings")
    public ResponseEntity<Building> updateBuilding(@Valid @RequestBody Building building) throws URISyntaxException {
        log.debug("REST request to update Building : {}", building);
        if (building.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Building result = buildingService.save(building);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, building.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buildings : get all the buildings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buildings in body
     */
    @GetMapping("/buildings")
    public List<Building> getAllBuildings() {
        log.debug("REST request to get all Buildings");
        return buildingService.findAll();
    }

    /**
     * GET  /buildings/:id : get the "id" building.
     *
     * @param id the id of the building to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the building, or with status 404 (Not Found)
     */
    @GetMapping("/buildings/{id}")
    public ResponseEntity<Building> getBuilding(@PathVariable Long id) {
        log.debug("REST request to get Building : {}", id);
        Optional<Building> building = buildingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(building);
    }

    /**
     * DELETE  /buildings/:id : delete the "id" building.
     *
     * @param id the id of the building to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/buildings/{id}")
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        log.debug("REST request to delete Building : {}", id);
        buildingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
