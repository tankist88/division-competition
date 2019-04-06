package io.github.divisioncompetition.web.rest;

import io.github.divisioncompetition.DivisionCompetitionApp;

import io.github.divisioncompetition.domain.Subbranch;
import io.github.divisioncompetition.repository.SubbranchRepository;
import io.github.divisioncompetition.service.SubbranchService;
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
 * Test class for the SubbranchResource REST controller.
 *
 * @see SubbranchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DivisionCompetitionApp.class)
public class SubbranchResourceIntTest {

    private static final Integer DEFAULT_TB = 1;
    private static final Integer UPDATED_TB = 2;

    private static final Integer DEFAULT_BRANCH = 1;
    private static final Integer UPDATED_BRANCH = 2;

    private static final Integer DEFAULT_SUBBRANCH = 1;
    private static final Integer UPDATED_SUBBRANCH = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private SubbranchRepository subbranchRepository;

    @Autowired
    private SubbranchService subbranchService;

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

    private MockMvc restSubbranchMockMvc;

    private Subbranch subbranch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SubbranchResource subbranchResource = new SubbranchResource(subbranchService);
        this.restSubbranchMockMvc = MockMvcBuilders.standaloneSetup(subbranchResource)
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
    public static Subbranch createEntity(EntityManager em) {
        Subbranch subbranch = new Subbranch()
            .tb(DEFAULT_TB)
            .branch(DEFAULT_BRANCH)
            .subbranch(DEFAULT_SUBBRANCH)
            .name(DEFAULT_NAME);
        return subbranch;
    }

    @Before
    public void initTest() {
        subbranch = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubbranch() throws Exception {
        int databaseSizeBeforeCreate = subbranchRepository.findAll().size();

        // Create the Subbranch
        restSubbranchMockMvc.perform(post("/api/subbranches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subbranch)))
            .andExpect(status().isCreated());

        // Validate the Subbranch in the database
        List<Subbranch> subbranchList = subbranchRepository.findAll();
        assertThat(subbranchList).hasSize(databaseSizeBeforeCreate + 1);
        Subbranch testSubbranch = subbranchList.get(subbranchList.size() - 1);
        assertThat(testSubbranch.getTb()).isEqualTo(DEFAULT_TB);
        assertThat(testSubbranch.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testSubbranch.getSubbranch()).isEqualTo(DEFAULT_SUBBRANCH);
        assertThat(testSubbranch.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createSubbranchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subbranchRepository.findAll().size();

        // Create the Subbranch with an existing ID
        subbranch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubbranchMockMvc.perform(post("/api/subbranches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subbranch)))
            .andExpect(status().isBadRequest());

        // Validate the Subbranch in the database
        List<Subbranch> subbranchList = subbranchRepository.findAll();
        assertThat(subbranchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTbIsRequired() throws Exception {
        int databaseSizeBeforeTest = subbranchRepository.findAll().size();
        // set the field null
        subbranch.setTb(null);

        // Create the Subbranch, which fails.

        restSubbranchMockMvc.perform(post("/api/subbranches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subbranch)))
            .andExpect(status().isBadRequest());

        List<Subbranch> subbranchList = subbranchRepository.findAll();
        assertThat(subbranchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubbranches() throws Exception {
        // Initialize the database
        subbranchRepository.saveAndFlush(subbranch);

        // Get all the subbranchList
        restSubbranchMockMvc.perform(get("/api/subbranches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subbranch.getId().intValue())))
            .andExpect(jsonPath("$.[*].tb").value(hasItem(DEFAULT_TB)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].subbranch").value(hasItem(DEFAULT_SUBBRANCH)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getSubbranch() throws Exception {
        // Initialize the database
        subbranchRepository.saveAndFlush(subbranch);

        // Get the subbranch
        restSubbranchMockMvc.perform(get("/api/subbranches/{id}", subbranch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(subbranch.getId().intValue()))
            .andExpect(jsonPath("$.tb").value(DEFAULT_TB))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.subbranch").value(DEFAULT_SUBBRANCH))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubbranch() throws Exception {
        // Get the subbranch
        restSubbranchMockMvc.perform(get("/api/subbranches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubbranch() throws Exception {
        // Initialize the database
        subbranchService.save(subbranch);

        int databaseSizeBeforeUpdate = subbranchRepository.findAll().size();

        // Update the subbranch
        Subbranch updatedSubbranch = subbranchRepository.findById(subbranch.getId()).get();
        // Disconnect from session so that the updates on updatedSubbranch are not directly saved in db
        em.detach(updatedSubbranch);
        updatedSubbranch
            .tb(UPDATED_TB)
            .branch(UPDATED_BRANCH)
            .subbranch(UPDATED_SUBBRANCH)
            .name(UPDATED_NAME);

        restSubbranchMockMvc.perform(put("/api/subbranches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSubbranch)))
            .andExpect(status().isOk());

        // Validate the Subbranch in the database
        List<Subbranch> subbranchList = subbranchRepository.findAll();
        assertThat(subbranchList).hasSize(databaseSizeBeforeUpdate);
        Subbranch testSubbranch = subbranchList.get(subbranchList.size() - 1);
        assertThat(testSubbranch.getTb()).isEqualTo(UPDATED_TB);
        assertThat(testSubbranch.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testSubbranch.getSubbranch()).isEqualTo(UPDATED_SUBBRANCH);
        assertThat(testSubbranch.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSubbranch() throws Exception {
        int databaseSizeBeforeUpdate = subbranchRepository.findAll().size();

        // Create the Subbranch

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubbranchMockMvc.perform(put("/api/subbranches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(subbranch)))
            .andExpect(status().isBadRequest());

        // Validate the Subbranch in the database
        List<Subbranch> subbranchList = subbranchRepository.findAll();
        assertThat(subbranchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubbranch() throws Exception {
        // Initialize the database
        subbranchService.save(subbranch);

        int databaseSizeBeforeDelete = subbranchRepository.findAll().size();

        // Delete the subbranch
        restSubbranchMockMvc.perform(delete("/api/subbranches/{id}", subbranch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Subbranch> subbranchList = subbranchRepository.findAll();
        assertThat(subbranchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subbranch.class);
        Subbranch subbranch1 = new Subbranch();
        subbranch1.setId(1L);
        Subbranch subbranch2 = new Subbranch();
        subbranch2.setId(subbranch1.getId());
        assertThat(subbranch1).isEqualTo(subbranch2);
        subbranch2.setId(2L);
        assertThat(subbranch1).isNotEqualTo(subbranch2);
        subbranch1.setId(null);
        assertThat(subbranch1).isNotEqualTo(subbranch2);
    }
}
