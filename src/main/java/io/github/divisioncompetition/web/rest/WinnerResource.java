package io.github.divisioncompetition.web.rest;
import io.github.divisioncompetition.domain.Winner;
import io.github.divisioncompetition.service.WinnerService;
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
 * REST controller for managing Winner.
 */
@RestController
@RequestMapping("/api")
public class WinnerResource {

    private final Logger log = LoggerFactory.getLogger(WinnerResource.class);

    private static final String ENTITY_NAME = "winner";

    private final WinnerService winnerService;

    public WinnerResource(WinnerService winnerService) {
        this.winnerService = winnerService;
    }

    /**
     * POST  /winners : Create a new winner.
     *
     * @param winner the winner to create
     * @return the ResponseEntity with status 201 (Created) and with body the new winner, or with status 400 (Bad Request) if the winner has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/winners")
    public ResponseEntity<Winner> createWinner(@RequestBody Winner winner) throws URISyntaxException {
        log.debug("REST request to save Winner : {}", winner);
        if (winner.getId() != null) {
            throw new BadRequestAlertException("A new winner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Winner result = winnerService.save(winner);
        return ResponseEntity.created(new URI("/api/winners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /winners : Updates an existing winner.
     *
     * @param winner the winner to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated winner,
     * or with status 400 (Bad Request) if the winner is not valid,
     * or with status 500 (Internal Server Error) if the winner couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/winners")
    public ResponseEntity<Winner> updateWinner(@RequestBody Winner winner) throws URISyntaxException {
        log.debug("REST request to update Winner : {}", winner);
        if (winner.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Winner result = winnerService.save(winner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, winner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /winners : get all the winners.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of winners in body
     */
    @GetMapping("/winners")
    public List<Winner> getAllWinners() {
        log.debug("REST request to get all Winners");
        return winnerService.findAll();
    }

    /**
     * GET  /winners/:id : get the "id" winner.
     *
     * @param id the id of the winner to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the winner, or with status 404 (Not Found)
     */
    @GetMapping("/winners/{id}")
    public ResponseEntity<Winner> getWinner(@PathVariable Long id) {
        log.debug("REST request to get Winner : {}", id);
        Optional<Winner> winner = winnerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(winner);
    }

    /**
     * DELETE  /winners/:id : delete the "id" winner.
     *
     * @param id the id of the winner to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/winners/{id}")
    public ResponseEntity<Void> deleteWinner(@PathVariable Long id) {
        log.debug("REST request to delete Winner : {}", id);
        winnerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
