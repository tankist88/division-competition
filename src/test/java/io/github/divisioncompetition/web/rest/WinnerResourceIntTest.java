package io.github.divisioncompetition.web.rest;

import io.github.divisioncompetition.DivisionCompetitionApp;

import io.github.divisioncompetition.domain.Winner;
import io.github.divisioncompetition.repository.WinnerRepository;
import io.github.divisioncompetition.service.WinnerService;
import io.github.divisioncompetition.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.divisioncompetition.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the WinnerResource REST controller.
 *
 * @see WinnerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DivisionCompetitionApp.class)
public class WinnerResourceIntTest {

    @Autowired
    private WinnerRepository winnerRepository;

    @Autowired
    private WinnerService winnerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restWinnerMockMvc;

    private Winner winner;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WinnerResource winnerResource = new WinnerResource(winnerService);
        this.restWinnerMockMvc = MockMvcBuilders.standaloneSetup(winnerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Winner createEntity(EntityManager em) {
        Winner winner = new Winner();
        return winner;
    }

    @Before
    public void initTest() {
        winner = createEntity(em);
    }

    @Test
    @Transactional
    public void createWinner() throws Exception {
        int databaseSizeBeforeCreate = winnerRepository.findAll().size();

        // Create the Winner
        restWinnerMockMvc.perform(post("/api/winners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(winner)))
            .andExpect(status().isCreated());

        // Validate the Winner in the database
        List<Winner> winnerList = winnerRepository.findAll();
        assertThat(winnerList).hasSize(databaseSizeBeforeCreate + 1);
        Winner testWinner = winnerList.get(winnerList.size() - 1);
    }

    @Test
    @Transactional
    public void createWinnerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = winnerRepository.findAll().size();

        // Create the Winner with an existing ID
        winner.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWinnerMockMvc.perform(post("/api/winners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(winner)))
            .andExpect(status().isBadRequest());

        // Validate the Winner in the database
        List<Winner> winnerList = winnerRepository.findAll();
        assertThat(winnerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllWinners() throws Exception {
        // Initialize the database
        winnerRepository.saveAndFlush(winner);

        // Get all the winnerList
        restWinnerMockMvc.perform(get("/api/winners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(winner.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getWinner() throws Exception {
        // Initialize the database
        winnerRepository.saveAndFlush(winner);

        // Get the winner
        restWinnerMockMvc.perform(get("/api/winners/{id}", winner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(winner.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingWinner() throws Exception {
        // Get the winner
        restWinnerMockMvc.perform(get("/api/winners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWinner() throws Exception {
        // Initialize the database
        winnerService.save(winner);

        int databaseSizeBeforeUpdate = winnerRepository.findAll().size();

        // Update the winner
        Winner updatedWinner = winnerRepository.findById(winner.getId()).get();
        // Disconnect from session so that the updates on updatedWinner are not directly saved in db
        em.detach(updatedWinner);

        restWinnerMockMvc.perform(put("/api/winners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWinner)))
            .andExpect(status().isOk());

        // Validate the Winner in the database
        List<Winner> winnerList = winnerRepository.findAll();
        assertThat(winnerList).hasSize(databaseSizeBeforeUpdate);
        Winner testWinner = winnerList.get(winnerList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingWinner() throws Exception {
        int databaseSizeBeforeUpdate = winnerRepository.findAll().size();

        // Create the Winner

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWinnerMockMvc.perform(put("/api/winners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(winner)))
            .andExpect(status().isBadRequest());

        // Validate the Winner in the database
        List<Winner> winnerList = winnerRepository.findAll();
        assertThat(winnerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWinner() throws Exception {
        // Initialize the database
        winnerService.save(winner);

        int databaseSizeBeforeDelete = winnerRepository.findAll().size();

        // Delete the winner
        restWinnerMockMvc.perform(delete("/api/winners/{id}", winner.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Winner> winnerList = winnerRepository.findAll();
        assertThat(winnerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Winner.class);
        Winner winner1 = new Winner();
        winner1.setId(1L);
        Winner winner2 = new Winner();
        winner2.setId(winner1.getId());
        assertThat(winner1).isEqualTo(winner2);
        winner2.setId(2L);
        assertThat(winner1).isNotEqualTo(winner2);
        winner1.setId(null);
        assertThat(winner1).isNotEqualTo(winner2);
    }
}
