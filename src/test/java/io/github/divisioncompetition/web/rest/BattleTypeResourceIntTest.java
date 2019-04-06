package io.github.divisioncompetition.web.rest;

import io.github.divisioncompetition.DivisionCompetitionApp;

import io.github.divisioncompetition.domain.BattleType;
import io.github.divisioncompetition.repository.BattleTypeRepository;
import io.github.divisioncompetition.service.BattleTypeService;
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
 * Test class for the BattleTypeResource REST controller.
 *
 * @see BattleTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DivisionCompetitionApp.class)
public class BattleTypeResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_TERM_IN_MONTHS = 1;
    private static final Integer UPDATED_TERM_IN_MONTHS = 2;

    @Autowired
    private BattleTypeRepository battleTypeRepository;

    @Autowired
    private BattleTypeService battleTypeService;

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

    private MockMvc restBattleTypeMockMvc;

    private BattleType battleType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BattleTypeResource battleTypeResource = new BattleTypeResource(battleTypeService);
        this.restBattleTypeMockMvc = MockMvcBuilders.standaloneSetup(battleTypeResource)
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
    public static BattleType createEntity(EntityManager em) {
        BattleType battleType = new BattleType()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .termInMonths(DEFAULT_TERM_IN_MONTHS);
        return battleType;
    }

    @Before
    public void initTest() {
        battleType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBattleType() throws Exception {
        int databaseSizeBeforeCreate = battleTypeRepository.findAll().size();

        // Create the BattleType
        restBattleTypeMockMvc.perform(post("/api/battle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleType)))
            .andExpect(status().isCreated());

        // Validate the BattleType in the database
        List<BattleType> battleTypeList = battleTypeRepository.findAll();
        assertThat(battleTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BattleType testBattleType = battleTypeList.get(battleTypeList.size() - 1);
        assertThat(testBattleType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testBattleType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBattleType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testBattleType.getTermInMonths()).isEqualTo(DEFAULT_TERM_IN_MONTHS);
    }

    @Test
    @Transactional
    public void createBattleTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = battleTypeRepository.findAll().size();

        // Create the BattleType with an existing ID
        battleType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBattleTypeMockMvc.perform(post("/api/battle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleType)))
            .andExpect(status().isBadRequest());

        // Validate the BattleType in the database
        List<BattleType> battleTypeList = battleTypeRepository.findAll();
        assertThat(battleTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = battleTypeRepository.findAll().size();
        // set the field null
        battleType.setCode(null);

        // Create the BattleType, which fails.

        restBattleTypeMockMvc.perform(post("/api/battle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleType)))
            .andExpect(status().isBadRequest());

        List<BattleType> battleTypeList = battleTypeRepository.findAll();
        assertThat(battleTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = battleTypeRepository.findAll().size();
        // set the field null
        battleType.setName(null);

        // Create the BattleType, which fails.

        restBattleTypeMockMvc.perform(post("/api/battle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleType)))
            .andExpect(status().isBadRequest());

        List<BattleType> battleTypeList = battleTypeRepository.findAll();
        assertThat(battleTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTermInMonthsIsRequired() throws Exception {
        int databaseSizeBeforeTest = battleTypeRepository.findAll().size();
        // set the field null
        battleType.setTermInMonths(null);

        // Create the BattleType, which fails.

        restBattleTypeMockMvc.perform(post("/api/battle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleType)))
            .andExpect(status().isBadRequest());

        List<BattleType> battleTypeList = battleTypeRepository.findAll();
        assertThat(battleTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBattleTypes() throws Exception {
        // Initialize the database
        battleTypeRepository.saveAndFlush(battleType);

        // Get all the battleTypeList
        restBattleTypeMockMvc.perform(get("/api/battle-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(battleType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].termInMonths").value(hasItem(DEFAULT_TERM_IN_MONTHS)));
    }
    
    @Test
    @Transactional
    public void getBattleType() throws Exception {
        // Initialize the database
        battleTypeRepository.saveAndFlush(battleType);

        // Get the battleType
        restBattleTypeMockMvc.perform(get("/api/battle-types/{id}", battleType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(battleType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.termInMonths").value(DEFAULT_TERM_IN_MONTHS));
    }

    @Test
    @Transactional
    public void getNonExistingBattleType() throws Exception {
        // Get the battleType
        restBattleTypeMockMvc.perform(get("/api/battle-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBattleType() throws Exception {
        // Initialize the database
        battleTypeService.save(battleType);

        int databaseSizeBeforeUpdate = battleTypeRepository.findAll().size();

        // Update the battleType
        BattleType updatedBattleType = battleTypeRepository.findById(battleType.getId()).get();
        // Disconnect from session so that the updates on updatedBattleType are not directly saved in db
        em.detach(updatedBattleType);
        updatedBattleType
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .termInMonths(UPDATED_TERM_IN_MONTHS);

        restBattleTypeMockMvc.perform(put("/api/battle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBattleType)))
            .andExpect(status().isOk());

        // Validate the BattleType in the database
        List<BattleType> battleTypeList = battleTypeRepository.findAll();
        assertThat(battleTypeList).hasSize(databaseSizeBeforeUpdate);
        BattleType testBattleType = battleTypeList.get(battleTypeList.size() - 1);
        assertThat(testBattleType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testBattleType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBattleType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testBattleType.getTermInMonths()).isEqualTo(UPDATED_TERM_IN_MONTHS);
    }

    @Test
    @Transactional
    public void updateNonExistingBattleType() throws Exception {
        int databaseSizeBeforeUpdate = battleTypeRepository.findAll().size();

        // Create the BattleType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBattleTypeMockMvc.perform(put("/api/battle-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleType)))
            .andExpect(status().isBadRequest());

        // Validate the BattleType in the database
        List<BattleType> battleTypeList = battleTypeRepository.findAll();
        assertThat(battleTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBattleType() throws Exception {
        // Initialize the database
        battleTypeService.save(battleType);

        int databaseSizeBeforeDelete = battleTypeRepository.findAll().size();

        // Delete the battleType
        restBattleTypeMockMvc.perform(delete("/api/battle-types/{id}", battleType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BattleType> battleTypeList = battleTypeRepository.findAll();
        assertThat(battleTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BattleType.class);
        BattleType battleType1 = new BattleType();
        battleType1.setId(1L);
        BattleType battleType2 = new BattleType();
        battleType2.setId(battleType1.getId());
        assertThat(battleType1).isEqualTo(battleType2);
        battleType2.setId(2L);
        assertThat(battleType1).isNotEqualTo(battleType2);
        battleType1.setId(null);
        assertThat(battleType1).isNotEqualTo(battleType2);
    }
}
