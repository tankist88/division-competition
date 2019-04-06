package io.github.divisioncompetition.web.rest;
import io.github.divisioncompetition.domain.BattleType;
import io.github.divisioncompetition.service.BattleTypeService;
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
 * REST controller for managing BattleType.
 */
@RestController
@RequestMapping("/api")
public class BattleTypeResource {

    private final Logger log = LoggerFactory.getLogger(BattleTypeResource.class);

    private static final String ENTITY_NAME = "battleType";

    private final BattleTypeService battleTypeService;

    public BattleTypeResource(BattleTypeService battleTypeService) {
        this.battleTypeService = battleTypeService;
    }

    /**
     * POST  /battle-types : Create a new battleType.
     *
     * @param battleType the battleType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new battleType, or with status 400 (Bad Request) if the battleType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/battle-types")
    public ResponseEntity<BattleType> createBattleType(@Valid @RequestBody BattleType battleType) throws URISyntaxException {
        log.debug("REST request to save BattleType : {}", battleType);
        if (battleType.getId() != null) {
            throw new BadRequestAlertException("A new battleType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BattleType result = battleTypeService.save(battleType);
        return ResponseEntity.created(new URI("/api/battle-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /battle-types : Updates an existing battleType.
     *
     * @param battleType the battleType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated battleType,
     * or with status 400 (Bad Request) if the battleType is not valid,
     * or with status 500 (Internal Server Error) if the battleType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/battle-types")
    public ResponseEntity<BattleType> updateBattleType(@Valid @RequestBody BattleType battleType) throws URISyntaxException {
        log.debug("REST request to update BattleType : {}", battleType);
        if (battleType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BattleType result = battleTypeService.save(battleType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, battleType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /battle-types : get all the battleTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of battleTypes in body
     */
    @GetMapping("/battle-types")
    public List<BattleType> getAllBattleTypes() {
        log.debug("REST request to get all BattleTypes");
        return battleTypeService.findAll();
    }

    /**
     * GET  /battle-types/:id : get the "id" battleType.
     *
     * @param id the id of the battleType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the battleType, or with status 404 (Not Found)
     */
    @GetMapping("/battle-types/{id}")
    public ResponseEntity<BattleType> getBattleType(@PathVariable Long id) {
        log.debug("REST request to get BattleType : {}", id);
        Optional<BattleType> battleType = battleTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(battleType);
    }

    /**
     * DELETE  /battle-types/:id : delete the "id" battleType.
     *
     * @param id the id of the battleType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/battle-types/{id}")
    public ResponseEntity<Void> deleteBattleType(@PathVariable Long id) {
        log.debug("REST request to delete BattleType : {}", id);
        battleTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
