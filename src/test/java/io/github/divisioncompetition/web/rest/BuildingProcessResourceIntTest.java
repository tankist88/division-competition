package io.github.divisioncompetition.web.rest;

import io.github.divisioncompetition.DivisionCompetitionApp;

import io.github.divisioncompetition.domain.BuildingProcess;
import io.github.divisioncompetition.repository.BuildingProcessRepository;
import io.github.divisioncompetition.service.BuildingProcessService;
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
 * Test class for the BuildingProcessResource REST controller.
 *
 * @see BuildingProcessResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DivisionCompetitionApp.class)
public class BuildingProcessResourceIntTest {

    @Autowired
    private BuildingProcessRepository buildingProcessRepository;

    @Autowired
    private BuildingProcessService buildingProcessService;

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

    private MockMvc restBuildingProcessMockMvc;

    private BuildingProcess buildingProcess;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuildingProcessResource buildingProcessResource = new BuildingProcessResource(buildingProcessService);
        this.restBuildingProcessMockMvc = MockMvcBuilders.standaloneSetup(buildingProcessResource)
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
    public static BuildingProcess createEntity(EntityManager em) {
        BuildingProcess buildingProcess = new BuildingProcess();
        return buildingProcess;
    }

    @Before
    public void initTest() {
        buildingProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuildingProcess() throws Exception {
        int databaseSizeBeforeCreate = buildingProcessRepository.findAll().size();

        // Create the BuildingProcess
        restBuildingProcessMockMvc.perform(post("/api/building-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingProcess)))
            .andExpect(status().isCreated());

        // Validate the BuildingProcess in the database
        List<BuildingProcess> buildingProcessList = buildingProcessRepository.findAll();
        assertThat(buildingProcessList).hasSize(databaseSizeBeforeCreate + 1);
        BuildingProcess testBuildingProcess = buildingProcessList.get(buildingProcessList.size() - 1);
    }

    @Test
    @Transactional
    public void createBuildingProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingProcessRepository.findAll().size();

        // Create the BuildingProcess with an existing ID
        buildingProcess.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingProcessMockMvc.perform(post("/api/building-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingProcess)))
            .andExpect(status().isBadRequest());

        // Validate the BuildingProcess in the database
        List<BuildingProcess> buildingProcessList = buildingProcessRepository.findAll();
        assertThat(buildingProcessList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBuildingProcesses() throws Exception {
        // Initialize the database
        buildingProcessRepository.saveAndFlush(buildingProcess);

        // Get all the buildingProcessList
        restBuildingProcessMockMvc.perform(get("/api/building-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingProcess.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBuildingProcess() throws Exception {
        // Initialize the database
        buildingProcessRepository.saveAndFlush(buildingProcess);

        // Get the buildingProcess
        restBuildingProcessMockMvc.perform(get("/api/building-processes/{id}", buildingProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(buildingProcess.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBuildingProcess() throws Exception {
        // Get the buildingProcess
        restBuildingProcessMockMvc.perform(get("/api/building-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuildingProcess() throws Exception {
        // Initialize the database
        buildingProcessService.save(buildingProcess);

        int databaseSizeBeforeUpdate = buildingProcessRepository.findAll().size();

        // Update the buildingProcess
        BuildingProcess updatedBuildingProcess = buildingProcessRepository.findById(buildingProcess.getId()).get();
        // Disconnect from session so that the updates on updatedBuildingProcess are not directly saved in db
        em.detach(updatedBuildingProcess);

        restBuildingProcessMockMvc.perform(put("/api/building-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBuildingProcess)))
            .andExpect(status().isOk());

        // Validate the BuildingProcess in the database
        List<BuildingProcess> buildingProcessList = buildingProcessRepository.findAll();
        assertThat(buildingProcessList).hasSize(databaseSizeBeforeUpdate);
        BuildingProcess testBuildingProcess = buildingProcessList.get(buildingProcessList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBuildingProcess() throws Exception {
        int databaseSizeBeforeUpdate = buildingProcessRepository.findAll().size();

        // Create the BuildingProcess

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuildingProcessMockMvc.perform(put("/api/building-processes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingProcess)))
            .andExpect(status().isBadRequest());

        // Validate the BuildingProcess in the database
        List<BuildingProcess> buildingProcessList = buildingProcessRepository.findAll();
        assertThat(buildingProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBuildingProcess() throws Exception {
        // Initialize the database
        buildingProcessService.save(buildingProcess);

        int databaseSizeBeforeDelete = buildingProcessRepository.findAll().size();

        // Delete the buildingProcess
        restBuildingProcessMockMvc.perform(delete("/api/building-processes/{id}", buildingProcess.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BuildingProcess> buildingProcessList = buildingProcessRepository.findAll();
        assertThat(buildingProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuildingProcess.class);
        BuildingProcess buildingProcess1 = new BuildingProcess();
        buildingProcess1.setId(1L);
        BuildingProcess buildingProcess2 = new BuildingProcess();
        buildingProcess2.setId(buildingProcess1.getId());
        assertThat(buildingProcess1).isEqualTo(buildingProcess2);
        buildingProcess2.setId(2L);
        assertThat(buildingProcess1).isNotEqualTo(buildingProcess2);
        buildingProcess1.setId(null);
        assertThat(buildingProcess1).isNotEqualTo(buildingProcess2);
    }
}
