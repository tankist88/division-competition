package io.github.divisioncompetition.web.rest;
import io.github.divisioncompetition.domain.BattleMember;
import io.github.divisioncompetition.service.BattleMemberService;
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
 * REST controller for managing BattleMember.
 */
@RestController
@RequestMapping("/api")
public class BattleMemberResource {

    private final Logger log = LoggerFactory.getLogger(BattleMemberResource.class);

    private static final String ENTITY_NAME = "battleMember";

    private final BattleMemberService battleMemberService;

    public BattleMemberResource(BattleMemberService battleMemberService) {
        this.battleMemberService = battleMemberService;
    }

    /**
     * POST  /battle-members : Create a new battleMember.
     *
     * @param battleMember the battleMember to create
     * @return the ResponseEntity with status 201 (Created) and with body the new battleMember, or with status 400 (Bad Request) if the battleMember has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/battle-members")
    public ResponseEntity<BattleMember> createBattleMember(@Valid @RequestBody BattleMember battleMember) throws URISyntaxException {
        log.debug("REST request to save BattleMember : {}", battleMember);
        if (battleMember.getId() != null) {
            throw new BadRequestAlertException("A new battleMember cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BattleMember result = battleMemberService.save(battleMember);
        return ResponseEntity.created(new URI("/api/battle-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /battle-members : Updates an existing battleMember.
     *
     * @param battleMember the battleMember to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated battleMember,
     * or with status 400 (Bad Request) if the battleMember is not valid,
     * or with status 500 (Internal Server Error) if the battleMember couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/battle-members")
    public ResponseEntity<BattleMember> updateBattleMember(@Valid @RequestBody BattleMember battleMember) throws URISyntaxException {
        log.debug("REST request to update BattleMember : {}", battleMember);
        if (battleMember.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BattleMember result = battleMemberService.save(battleMember);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, battleMember.getId().toString()))
            .body(result);
    }

    /**
     * GET  /battle-members : get all the battleMembers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of battleMembers in body
     */
    @GetMapping("/battle-members")
    public List<BattleMember> getAllBattleMembers() {
        log.debug("REST request to get all BattleMembers");
        return battleMemberService.findAll();
    }

    /**
     * GET  /battle-members/:id : get the "id" battleMember.
     *
     * @param id the id of the battleMember to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the battleMember, or with status 404 (Not Found)
     */
    @GetMapping("/battle-members/{id}")
    public ResponseEntity<BattleMember> getBattleMember(@PathVariable Long id) {
        log.debug("REST request to get BattleMember : {}", id);
        Optional<BattleMember> battleMember = battleMemberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(battleMember);
    }

    /**
     * DELETE  /battle-members/:id : delete the "id" battleMember.
     *
     * @param id the id of the battleMember to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/battle-members/{id}")
    public ResponseEntity<Void> deleteBattleMember(@PathVariable Long id) {
        log.debug("REST request to delete BattleMember : {}", id);
        battleMemberService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
