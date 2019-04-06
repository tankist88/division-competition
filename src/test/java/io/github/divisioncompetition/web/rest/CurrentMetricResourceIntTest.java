package io.github.divisioncompetition.web.rest;

import io.github.divisioncompetition.DivisionCompetitionApp;

import io.github.divisioncompetition.domain.CurrentMetric;
import io.github.divisioncompetition.repository.CurrentMetricRepository;
import io.github.divisioncompetition.service.CurrentMetricService;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.github.divisioncompetition.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CurrentMetricResource REST controller.
 *
 * @see CurrentMetricResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DivisionCompetitionApp.class)
public class CurrentMetricResourceIntTest {

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final Integer DEFAULT_FINALIZED_COUNT = 1;
    private static final Integer UPDATED_FINALIZED_COUNT = 2;

    private static final Instant DEFAULT_FINALIZE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINALIZE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CurrentMetricRepository currentMetricRepository;

    @Autowired
    private CurrentMetricService currentMetricService;

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

    private MockMvc restCurrentMetricMockMvc;

    private CurrentMetric currentMetric;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrentMetricResource currentMetricResource = new CurrentMetricResource(currentMetricService);
        this.restCurrentMetricMockMvc = MockMvcBuilders.standaloneSetup(currentMetricResource)
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
    public static CurrentMetric createEntity(EntityManager em) {
        CurrentMetric currentMetric = new CurrentMetric()
            .count(DEFAULT_COUNT)
            .finalizedCount(DEFAULT_FINALIZED_COUNT)
            .finalizeDate(DEFAULT_FINALIZE_DATE)
            .lastModified(DEFAULT_LAST_MODIFIED);
        return currentMetric;
    }

    @Before
    public void initTest() {
        currentMetric = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrentMetric() throws Exception {
        int databaseSizeBeforeCreate = currentMetricRepository.findAll().size();

        // Create the CurrentMetric
        restCurrentMetricMockMvc.perform(post("/api/current-metrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentMetric)))
            .andExpect(status().isCreated());

        // Validate the CurrentMetric in the database
        List<CurrentMetric> currentMetricList = currentMetricRepository.findAll();
        assertThat(currentMetricList).hasSize(databaseSizeBeforeCreate + 1);
        CurrentMetric testCurrentMetric = currentMetricList.get(currentMetricList.size() - 1);
        assertThat(testCurrentMetric.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testCurrentMetric.getFinalizedCount()).isEqualTo(DEFAULT_FINALIZED_COUNT);
        assertThat(testCurrentMetric.getFinalizeDate()).isEqualTo(DEFAULT_FINALIZE_DATE);
        assertThat(testCurrentMetric.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void createCurrentMetricWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currentMetricRepository.findAll().size();

        // Create the CurrentMetric with an existing ID
        currentMetric.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrentMetricMockMvc.perform(post("/api/current-metrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentMetric)))
            .andExpect(status().isBadRequest());

        // Validate the CurrentMetric in the database
        List<CurrentMetric> currentMetricList = currentMetricRepository.findAll();
        assertThat(currentMetricList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = currentMetricRepository.findAll().size();
        // set the field null
        currentMetric.setLastModified(null);

        // Create the CurrentMetric, which fails.

        restCurrentMetricMockMvc.perform(post("/api/current-metrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentMetric)))
            .andExpect(status().isBadRequest());

        List<CurrentMetric> currentMetricList = currentMetricRepository.findAll();
        assertThat(currentMetricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrentMetrics() throws Exception {
        // Initialize the database
        currentMetricRepository.saveAndFlush(currentMetric);

        // Get all the currentMetricList
        restCurrentMetricMockMvc.perform(get("/api/current-metrics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currentMetric.getId().intValue())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].finalizedCount").value(hasItem(DEFAULT_FINALIZED_COUNT)))
            .andExpect(jsonPath("$.[*].finalizeDate").value(hasItem(DEFAULT_FINALIZE_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }
    
    @Test
    @Transactional
    public void getCurrentMetric() throws Exception {
        // Initialize the database
        currentMetricRepository.saveAndFlush(currentMetric);

        // Get the currentMetric
        restCurrentMetricMockMvc.perform(get("/api/current-metrics/{id}", currentMetric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currentMetric.getId().intValue()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.finalizedCount").value(DEFAULT_FINALIZED_COUNT))
            .andExpect(jsonPath("$.finalizeDate").value(DEFAULT_FINALIZE_DATE.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurrentMetric() throws Exception {
        // Get the currentMetric
        restCurrentMetricMockMvc.perform(get("/api/current-metrics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrentMetric() throws Exception {
        // Initialize the database
        currentMetricService.save(currentMetric);

        int databaseSizeBeforeUpdate = currentMetricRepository.findAll().size();

        // Update the currentMetric
        CurrentMetric updatedCurrentMetric = currentMetricRepository.findById(currentMetric.getId()).get();
        // Disconnect from session so that the updates on updatedCurrentMetric are not directly saved in db
        em.detach(updatedCurrentMetric);
        updatedCurrentMetric
            .count(UPDATED_COUNT)
            .finalizedCount(UPDATED_FINALIZED_COUNT)
            .finalizeDate(UPDATED_FINALIZE_DATE)
            .lastModified(UPDATED_LAST_MODIFIED);

        restCurrentMetricMockMvc.perform(put("/api/current-metrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCurrentMetric)))
            .andExpect(status().isOk());

        // Validate the CurrentMetric in the database
        List<CurrentMetric> currentMetricList = currentMetricRepository.findAll();
        assertThat(currentMetricList).hasSize(databaseSizeBeforeUpdate);
        CurrentMetric testCurrentMetric = currentMetricList.get(currentMetricList.size() - 1);
        assertThat(testCurrentMetric.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testCurrentMetric.getFinalizedCount()).isEqualTo(UPDATED_FINALIZED_COUNT);
        assertThat(testCurrentMetric.getFinalizeDate()).isEqualTo(UPDATED_FINALIZE_DATE);
        assertThat(testCurrentMetric.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrentMetric() throws Exception {
        int databaseSizeBeforeUpdate = currentMetricRepository.findAll().size();

        // Create the CurrentMetric

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrentMetricMockMvc.perform(put("/api/current-metrics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currentMetric)))
            .andExpect(status().isBadRequest());

        // Validate the CurrentMetric in the database
        List<CurrentMetric> currentMetricList = currentMetricRepository.findAll();
        assertThat(currentMetricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrentMetric() throws Exception {
        // Initialize the database
        currentMetricService.save(currentMetric);

        int databaseSizeBeforeDelete = currentMetricRepository.findAll().size();

        // Delete the currentMetric
        restCurrentMetricMockMvc.perform(delete("/api/current-metrics/{id}", currentMetric.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CurrentMetric> currentMetricList = currentMetricRepository.findAll();
        assertThat(currentMetricList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrentMetric.class);
        CurrentMetric currentMetric1 = new CurrentMetric();
        currentMetric1.setId(1L);
        CurrentMetric currentMetric2 = new CurrentMetric();
        currentMetric2.setId(currentMetric1.getId());
        assertThat(currentMetric1).isEqualTo(currentMetric2);
        currentMetric2.setId(2L);
        assertThat(currentMetric1).isNotEqualTo(currentMetric2);
        currentMetric1.setId(null);
        assertThat(currentMetric1).isNotEqualTo(currentMetric2);
    }
}
