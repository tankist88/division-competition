package io.github.divisioncompetition.web.rest;

import io.github.divisioncompetition.DivisionCompetitionApp;

import io.github.divisioncompetition.domain.ResourceProgress;
import io.github.divisioncompetition.repository.ResourceProgressRepository;
import io.github.divisioncompetition.service.ResourceProgressService;
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
 * Test class for the ResourceProgressResource REST controller.
 *
 * @see ResourceProgressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DivisionCompetitionApp.class)
public class ResourceProgressResourceIntTest {

    private static final Double DEFAULT_PROGRESS = 1D;
    private static final Double UPDATED_PROGRESS = 2D;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ResourceProgressRepository resourceProgressRepository;

    @Autowired
    private ResourceProgressService resourceProgressService;

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

    private MockMvc restResourceProgressMockMvc;

    private ResourceProgress resourceProgress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourceProgressResource resourceProgressResource = new ResourceProgressResource(resourceProgressService);
        this.restResourceProgressMockMvc = MockMvcBuilders.standaloneSetup(resourceProgressResource)
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
    public static ResourceProgress createEntity(EntityManager em) {
        ResourceProgress resourceProgress = new ResourceProgress()
            .progress(DEFAULT_PROGRESS)
            .lastModified(DEFAULT_LAST_MODIFIED);
        return resourceProgress;
    }

    @Before
    public void initTest() {
        resourceProgress = createEntity(em);
    }

    @Test
    @Transactional
    public void createResourceProgress() throws Exception {
        int databaseSizeBeforeCreate = resourceProgressRepository.findAll().size();

        // Create the ResourceProgress
        restResourceProgressMockMvc.perform(post("/api/resource-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceProgress)))
            .andExpect(status().isCreated());

        // Validate the ResourceProgress in the database
        List<ResourceProgress> resourceProgressList = resourceProgressRepository.findAll();
        assertThat(resourceProgressList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceProgress testResourceProgress = resourceProgressList.get(resourceProgressList.size() - 1);
        assertThat(testResourceProgress.getProgress()).isEqualTo(DEFAULT_PROGRESS);
        assertThat(testResourceProgress.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void createResourceProgressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceProgressRepository.findAll().size();

        // Create the ResourceProgress with an existing ID
        resourceProgress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceProgressMockMvc.perform(post("/api/resource-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceProgress)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceProgress in the database
        List<ResourceProgress> resourceProgressList = resourceProgressRepository.findAll();
        assertThat(resourceProgressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceProgressRepository.findAll().size();
        // set the field null
        resourceProgress.setLastModified(null);

        // Create the ResourceProgress, which fails.

        restResourceProgressMockMvc.perform(post("/api/resource-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceProgress)))
            .andExpect(status().isBadRequest());

        List<ResourceProgress> resourceProgressList = resourceProgressRepository.findAll();
        assertThat(resourceProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResourceProgresses() throws Exception {
        // Initialize the database
        resourceProgressRepository.saveAndFlush(resourceProgress);

        // Get all the resourceProgressList
        restResourceProgressMockMvc.perform(get("/api/resource-progresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].progress").value(hasItem(DEFAULT_PROGRESS.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }
    
    @Test
    @Transactional
    public void getResourceProgress() throws Exception {
        // Initialize the database
        resourceProgressRepository.saveAndFlush(resourceProgress);

        // Get the resourceProgress
        restResourceProgressMockMvc.perform(get("/api/resource-progresses/{id}", resourceProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resourceProgress.getId().intValue()))
            .andExpect(jsonPath("$.progress").value(DEFAULT_PROGRESS.doubleValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingResourceProgress() throws Exception {
        // Get the resourceProgress
        restResourceProgressMockMvc.perform(get("/api/resource-progresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResourceProgress() throws Exception {
        // Initialize the database
        resourceProgressService.save(resourceProgress);

        int databaseSizeBeforeUpdate = resourceProgressRepository.findAll().size();

        // Update the resourceProgress
        ResourceProgress updatedResourceProgress = resourceProgressRepository.findById(resourceProgress.getId()).get();
        // Disconnect from session so that the updates on updatedResourceProgress are not directly saved in db
        em.detach(updatedResourceProgress);
        updatedResourceProgress
            .progress(UPDATED_PROGRESS)
            .lastModified(UPDATED_LAST_MODIFIED);

        restResourceProgressMockMvc.perform(put("/api/resource-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResourceProgress)))
            .andExpect(status().isOk());

        // Validate the ResourceProgress in the database
        List<ResourceProgress> resourceProgressList = resourceProgressRepository.findAll();
        assertThat(resourceProgressList).hasSize(databaseSizeBeforeUpdate);
        ResourceProgress testResourceProgress = resourceProgressList.get(resourceProgressList.size() - 1);
        assertThat(testResourceProgress.getProgress()).isEqualTo(UPDATED_PROGRESS);
        assertThat(testResourceProgress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingResourceProgress() throws Exception {
        int databaseSizeBeforeUpdate = resourceProgressRepository.findAll().size();

        // Create the ResourceProgress

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceProgressMockMvc.perform(put("/api/resource-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceProgress)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceProgress in the database
        List<ResourceProgress> resourceProgressList = resourceProgressRepository.findAll();
        assertThat(resourceProgressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResourceProgress() throws Exception {
        // Initialize the database
        resourceProgressService.save(resourceProgress);

        int databaseSizeBeforeDelete = resourceProgressRepository.findAll().size();

        // Delete the resourceProgress
        restResourceProgressMockMvc.perform(delete("/api/resource-progresses/{id}", resourceProgress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ResourceProgress> resourceProgressList = resourceProgressRepository.findAll();
        assertThat(resourceProgressList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceProgress.class);
        ResourceProgress resourceProgress1 = new ResourceProgress();
        resourceProgress1.setId(1L);
        ResourceProgress resourceProgress2 = new ResourceProgress();
        resourceProgress2.setId(resourceProgress1.getId());
        assertThat(resourceProgress1).isEqualTo(resourceProgress2);
        resourceProgress2.setId(2L);
        assertThat(resourceProgress1).isNotEqualTo(resourceProgress2);
        resourceProgress1.setId(null);
        assertThat(resourceProgress1).isNotEqualTo(resourceProgress2);
    }
}
