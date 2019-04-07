package io.github.divisioncompetition.service.impl;

import io.github.divisioncompetition.service.WinnerService;
import io.github.divisioncompetition.domain.Winner;
import io.github.divisioncompetition.repository.WinnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Winner.
 */
@Service
@Transactional
public class WinnerServiceImpl implements WinnerService {

    private final Logger log = LoggerFactory.getLogger(WinnerServiceImpl.class);

    private final WinnerRepository winnerRepository;

    public WinnerServiceImpl(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    /**
     * Save a winner.
     *
     * @param winner the entity to save
     * @return the persisted entity
     */
    @Override
    public Winner save(Winner winner) {
        log.debug("Request to save Winner : {}", winner);
        return winnerRepository.save(winner);
    }

    /**
     * Get all the winners.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Winner> findAll() {
        log.debug("Request to get all Winners");
        return winnerRepository.findAll();
    }


    /**
     * Get one winner by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Winner> findOne(Long id) {
        log.debug("Request to get Winner : {}", id);
        return winnerRepository.findById(id);
    }

    /**
     * Delete the winner by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Winner : {}", id);
        winnerRepository.deleteById(id);
    }
}
