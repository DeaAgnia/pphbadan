package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PPhBadanApp;

import com.mycompany.myapp.domain.Jenisharta;
import com.mycompany.myapp.repository.JenishartaRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the JenishartaResource REST controller.
 *
 * @see JenishartaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PPhBadanApp.class)
public class JenishartaResourceIntTest {

    private static final String DEFAULT_JENIS = "AAAAAAAAAA";
    private static final String UPDATED_JENIS = "BBBBBBBBBB";

    @Autowired
    private JenishartaRepository jenishartaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJenishartaMockMvc;

    private Jenisharta jenisharta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JenishartaResource jenishartaResource = new JenishartaResource(jenishartaRepository);
        this.restJenishartaMockMvc = MockMvcBuilders.standaloneSetup(jenishartaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Jenisharta createEntity(EntityManager em) {
        Jenisharta jenisharta = new Jenisharta()
            .jenis(DEFAULT_JENIS);
        return jenisharta;
    }

    @Before
    public void initTest() {
        jenisharta = createEntity(em);
    }

    @Test
    @Transactional
    public void createJenisharta() throws Exception {
        int databaseSizeBeforeCreate = jenishartaRepository.findAll().size();

        // Create the Jenisharta
        restJenishartaMockMvc.perform(post("/api/jenishartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jenisharta)))
            .andExpect(status().isCreated());

        // Validate the Jenisharta in the database
        List<Jenisharta> jenishartaList = jenishartaRepository.findAll();
        assertThat(jenishartaList).hasSize(databaseSizeBeforeCreate + 1);
        Jenisharta testJenisharta = jenishartaList.get(jenishartaList.size() - 1);
        assertThat(testJenisharta.getJenis()).isEqualTo(DEFAULT_JENIS);
    }

    @Test
    @Transactional
    public void createJenishartaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jenishartaRepository.findAll().size();

        // Create the Jenisharta with an existing ID
        jenisharta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJenishartaMockMvc.perform(post("/api/jenishartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jenisharta)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Jenisharta> jenishartaList = jenishartaRepository.findAll();
        assertThat(jenishartaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkJenisIsRequired() throws Exception {
        int databaseSizeBeforeTest = jenishartaRepository.findAll().size();
        // set the field null
        jenisharta.setJenis(null);

        // Create the Jenisharta, which fails.

        restJenishartaMockMvc.perform(post("/api/jenishartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jenisharta)))
            .andExpect(status().isBadRequest());

        List<Jenisharta> jenishartaList = jenishartaRepository.findAll();
        assertThat(jenishartaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllJenishartas() throws Exception {
        // Initialize the database
        jenishartaRepository.saveAndFlush(jenisharta);

        // Get all the jenishartaList
        restJenishartaMockMvc.perform(get("/api/jenishartas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jenisharta.getId().intValue())))
            .andExpect(jsonPath("$.[*].jenis").value(hasItem(DEFAULT_JENIS.toString())));
    }

    @Test
    @Transactional
    public void getJenisharta() throws Exception {
        // Initialize the database
        jenishartaRepository.saveAndFlush(jenisharta);

        // Get the jenisharta
        restJenishartaMockMvc.perform(get("/api/jenishartas/{id}", jenisharta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jenisharta.getId().intValue()))
            .andExpect(jsonPath("$.jenis").value(DEFAULT_JENIS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJenisharta() throws Exception {
        // Get the jenisharta
        restJenishartaMockMvc.perform(get("/api/jenishartas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJenisharta() throws Exception {
        // Initialize the database
        jenishartaRepository.saveAndFlush(jenisharta);
        int databaseSizeBeforeUpdate = jenishartaRepository.findAll().size();

        // Update the jenisharta
        Jenisharta updatedJenisharta = jenishartaRepository.findOne(jenisharta.getId());
        updatedJenisharta
            .jenis(UPDATED_JENIS);

        restJenishartaMockMvc.perform(put("/api/jenishartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedJenisharta)))
            .andExpect(status().isOk());

        // Validate the Jenisharta in the database
        List<Jenisharta> jenishartaList = jenishartaRepository.findAll();
        assertThat(jenishartaList).hasSize(databaseSizeBeforeUpdate);
        Jenisharta testJenisharta = jenishartaList.get(jenishartaList.size() - 1);
        assertThat(testJenisharta.getJenis()).isEqualTo(UPDATED_JENIS);
    }

    @Test
    @Transactional
    public void updateNonExistingJenisharta() throws Exception {
        int databaseSizeBeforeUpdate = jenishartaRepository.findAll().size();

        // Create the Jenisharta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJenishartaMockMvc.perform(put("/api/jenishartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jenisharta)))
            .andExpect(status().isCreated());

        // Validate the Jenisharta in the database
        List<Jenisharta> jenishartaList = jenishartaRepository.findAll();
        assertThat(jenishartaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJenisharta() throws Exception {
        // Initialize the database
        jenishartaRepository.saveAndFlush(jenisharta);
        int databaseSizeBeforeDelete = jenishartaRepository.findAll().size();

        // Get the jenisharta
        restJenishartaMockMvc.perform(delete("/api/jenishartas/{id}", jenisharta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Jenisharta> jenishartaList = jenishartaRepository.findAll();
        assertThat(jenishartaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jenisharta.class);
        Jenisharta jenisharta1 = new Jenisharta();
        jenisharta1.setId(1L);
        Jenisharta jenisharta2 = new Jenisharta();
        jenisharta2.setId(jenisharta1.getId());
        assertThat(jenisharta1).isEqualTo(jenisharta2);
        jenisharta2.setId(2L);
        assertThat(jenisharta1).isNotEqualTo(jenisharta2);
        jenisharta1.setId(null);
        assertThat(jenisharta1).isNotEqualTo(jenisharta2);
    }
}
