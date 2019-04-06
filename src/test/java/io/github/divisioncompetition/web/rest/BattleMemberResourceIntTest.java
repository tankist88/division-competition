package io.github.divisioncompetition.web.rest;

import io.github.divisioncompetition.DivisionCompetitionApp;

import io.github.divisioncompetition.domain.BattleMember;
import io.github.divisioncompetition.repository.BattleMemberRepository;
import io.github.divisioncompetition.service.BattleMemberService;
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
 * Test class for the BattleMemberResource REST controller.
 *
 * @see BattleMemberResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DivisionCompetitionApp.class)
public class BattleMemberResourceIntTest {

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private BattleMemberRepository battleMemberRepository;

    @Autowired
    private BattleMemberService battleMemberService;

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

    private MockMvc restBattleMemberMockMvc;

    private BattleMember battleMember;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BattleMemberResource battleMemberResource = new BattleMemberResource(battleMemberService);
        this.restBattleMemberMockMvc = MockMvcBuilders.standaloneSetup(battleMemberResource)
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
    public static BattleMember createEntity(EntityManager em) {
        BattleMember battleMember = new BattleMember()
            .status(DEFAULT_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED);
        return battleMember;
    }

    @Before
    public void initTest() {
        battleMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createBattleMember() throws Exception {
        int databaseSizeBeforeCreate = battleMemberRepository.findAll().size();

        // Create the BattleMember
        restBattleMemberMockMvc.perform(post("/api/battle-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleMember)))
            .andExpect(status().isCreated());

        // Validate the BattleMember in the database
        List<BattleMember> battleMemberList = battleMemberRepository.findAll();
        assertThat(battleMemberList).hasSize(databaseSizeBeforeCreate + 1);
        BattleMember testBattleMember = battleMemberList.get(battleMemberList.size() - 1);
        assertThat(testBattleMember.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBattleMember.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void createBattleMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = battleMemberRepository.findAll().size();

        // Create the BattleMember with an existing ID
        battleMember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBattleMemberMockMvc.perform(post("/api/battle-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleMember)))
            .andExpect(status().isBadRequest());

        // Validate the BattleMember in the database
        List<BattleMember> battleMemberList = battleMemberRepository.findAll();
        assertThat(battleMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = battleMemberRepository.findAll().size();
        // set the field null
        battleMember.setStatus(null);

        // Create the BattleMember, which fails.

        restBattleMemberMockMvc.perform(post("/api/battle-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleMember)))
            .andExpect(status().isBadRequest());

        List<BattleMember> battleMemberList = battleMemberRepository.findAll();
        assertThat(battleMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = battleMemberRepository.findAll().size();
        // set the field null
        battleMember.setLastModified(null);

        // Create the BattleMember, which fails.

        restBattleMemberMockMvc.perform(post("/api/battle-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleMember)))
            .andExpect(status().isBadRequest());

        List<BattleMember> battleMemberList = battleMemberRepository.findAll();
        assertThat(battleMemberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBattleMembers() throws Exception {
        // Initialize the database
        battleMemberRepository.saveAndFlush(battleMember);

        // Get all the battleMemberList
        restBattleMemberMockMvc.perform(get("/api/battle-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(battleMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())));
    }
    
    @Test
    @Transactional
    public void getBattleMember() throws Exception {
        // Initialize the database
        battleMemberRepository.saveAndFlush(battleMember);

        // Get the battleMember
        restBattleMemberMockMvc.perform(get("/api/battle-members/{id}", battleMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(battleMember.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBattleMember() throws Exception {
        // Get the battleMember
        restBattleMemberMockMvc.perform(get("/api/battle-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBattleMember() throws Exception {
        // Initialize the database
        battleMemberService.save(battleMember);

        int databaseSizeBeforeUpdate = battleMemberRepository.findAll().size();

        // Update the battleMember
        BattleMember updatedBattleMember = battleMemberRepository.findById(battleMember.getId()).get();
        // Disconnect from session so that the updates on updatedBattleMember are not directly saved in db
        em.detach(updatedBattleMember);
        updatedBattleMember
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED);

        restBattleMemberMockMvc.perform(put("/api/battle-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBattleMember)))
            .andExpect(status().isOk());

        // Validate the BattleMember in the database
        List<BattleMember> battleMemberList = battleMemberRepository.findAll();
        assertThat(battleMemberList).hasSize(databaseSizeBeforeUpdate);
        BattleMember testBattleMember = battleMemberList.get(battleMemberList.size() - 1);
        assertThat(testBattleMember.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBattleMember.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingBattleMember() throws Exception {
        int databaseSizeBeforeUpdate = battleMemberRepository.findAll().size();

        // Create the BattleMember

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBattleMemberMockMvc.perform(put("/api/battle-members")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(battleMember)))
            .andExpect(status().isBadRequest());

        // Validate the BattleMember in the database
        List<BattleMember> battleMemberList = battleMemberRepository.findAll();
        assertThat(battleMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBattleMember() throws Exception {
        // Initialize the database
        battleMemberService.save(battleMember);

        int databaseSizeBeforeDelete = battleMemberRepository.findAll().size();

        // Delete the battleMember
        restBattleMemberMockMvc.perform(delete("/api/battle-members/{id}", battleMember.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BattleMember> battleMemberList = battleMemberRepository.findAll();
        assertThat(battleMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BattleMember.class);
        BattleMember battleMember1 = new BattleMember();
        battleMember1.setId(1L);
        BattleMember battleMember2 = new BattleMember();
        battleMember2.setId(battleMember1.getId());
        assertThat(battleMember1).isEqualTo(battleMember2);
        battleMember2.setId(2L);
        assertThat(battleMember1).isNotEqualTo(battleMember2);
        battleMember1.setId(null);
        assertThat(battleMember1).isNotEqualTo(battleMember2);
    }
}
