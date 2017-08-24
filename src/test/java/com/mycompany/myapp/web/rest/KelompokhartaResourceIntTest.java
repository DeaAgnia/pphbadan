package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PPhBadanApp;

import com.mycompany.myapp.domain.Kelompokharta;
import com.mycompany.myapp.domain.Jenisharta;
import com.mycompany.myapp.repository.KelompokhartaRepository;
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
 * Test class for the KelompokhartaResource REST controller.
 *
 * @see KelompokhartaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PPhBadanApp.class)
public class KelompokhartaResourceIntTest {

    private static final String DEFAULT_NAMA = "AAAAAAAAAA";
    private static final String UPDATED_NAMA = "BBBBBBBBBB";

    @Autowired
    private KelompokhartaRepository kelompokhartaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restKelompokhartaMockMvc;

    private Kelompokharta kelompokharta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        KelompokhartaResource kelompokhartaResource = new KelompokhartaResource(kelompokhartaRepository);
        this.restKelompokhartaMockMvc = MockMvcBuilders.standaloneSetup(kelompokhartaResource)
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
    public static Kelompokharta createEntity(EntityManager em) {
        Kelompokharta kelompokharta = new Kelompokharta()
            .nama(DEFAULT_NAMA);
        // Add required entity
        Jenisharta jenisharta = JenishartaResourceIntTest.createEntity(em);
        em.persist(jenisharta);
        em.flush();
        kelompokharta.setJenisharta(jenisharta);
        return kelompokharta;
    }

    @Before
    public void initTest() {
        kelompokharta = createEntity(em);
    }

    @Test
    @Transactional
    public void createKelompokharta() throws Exception {
        int databaseSizeBeforeCreate = kelompokhartaRepository.findAll().size();

        // Create the Kelompokharta
        restKelompokhartaMockMvc.perform(post("/api/kelompokhartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kelompokharta)))
            .andExpect(status().isCreated());

        // Validate the Kelompokharta in the database
        List<Kelompokharta> kelompokhartaList = kelompokhartaRepository.findAll();
        assertThat(kelompokhartaList).hasSize(databaseSizeBeforeCreate + 1);
        Kelompokharta testKelompokharta = kelompokhartaList.get(kelompokhartaList.size() - 1);
        assertThat(testKelompokharta.getNama()).isEqualTo(DEFAULT_NAMA);
    }

    @Test
    @Transactional
    public void createKelompokhartaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kelompokhartaRepository.findAll().size();

        // Create the Kelompokharta with an existing ID
        kelompokharta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKelompokhartaMockMvc.perform(post("/api/kelompokhartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kelompokharta)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Kelompokharta> kelompokhartaList = kelompokhartaRepository.findAll();
        assertThat(kelompokhartaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNamaIsRequired() throws Exception {
        int databaseSizeBeforeTest = kelompokhartaRepository.findAll().size();
        // set the field null
        kelompokharta.setNama(null);

        // Create the Kelompokharta, which fails.

        restKelompokhartaMockMvc.perform(post("/api/kelompokhartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kelompokharta)))
            .andExpect(status().isBadRequest());

        List<Kelompokharta> kelompokhartaList = kelompokhartaRepository.findAll();
        assertThat(kelompokhartaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKelompokhartas() throws Exception {
        // Initialize the database
        kelompokhartaRepository.saveAndFlush(kelompokharta);

        // Get all the kelompokhartaList
        restKelompokhartaMockMvc.perform(get("/api/kelompokhartas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kelompokharta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nama").value(hasItem(DEFAULT_NAMA.toString())));
    }

    @Test
    @Transactional
    public void getKelompokharta() throws Exception {
        // Initialize the database
        kelompokhartaRepository.saveAndFlush(kelompokharta);

        // Get the kelompokharta
        restKelompokhartaMockMvc.perform(get("/api/kelompokhartas/{id}", kelompokharta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kelompokharta.getId().intValue()))
            .andExpect(jsonPath("$.nama").value(DEFAULT_NAMA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKelompokharta() throws Exception {
        // Get the kelompokharta
        restKelompokhartaMockMvc.perform(get("/api/kelompokhartas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKelompokharta() throws Exception {
        // Initialize the database
        kelompokhartaRepository.saveAndFlush(kelompokharta);
        int databaseSizeBeforeUpdate = kelompokhartaRepository.findAll().size();

        // Update the kelompokharta
        Kelompokharta updatedKelompokharta = kelompokhartaRepository.findOne(kelompokharta.getId());
        updatedKelompokharta
            .nama(UPDATED_NAMA);

        restKelompokhartaMockMvc.perform(put("/api/kelompokhartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKelompokharta)))
            .andExpect(status().isOk());

        // Validate the Kelompokharta in the database
        List<Kelompokharta> kelompokhartaList = kelompokhartaRepository.findAll();
        assertThat(kelompokhartaList).hasSize(databaseSizeBeforeUpdate);
        Kelompokharta testKelompokharta = kelompokhartaList.get(kelompokhartaList.size() - 1);
        assertThat(testKelompokharta.getNama()).isEqualTo(UPDATED_NAMA);
    }

    @Test
    @Transactional
    public void updateNonExistingKelompokharta() throws Exception {
        int databaseSizeBeforeUpdate = kelompokhartaRepository.findAll().size();

        // Create the Kelompokharta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restKelompokhartaMockMvc.perform(put("/api/kelompokhartas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kelompokharta)))
            .andExpect(status().isCreated());

        // Validate the Kelompokharta in the database
        List<Kelompokharta> kelompokhartaList = kelompokhartaRepository.findAll();
        assertThat(kelompokhartaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteKelompokharta() throws Exception {
        // Initialize the database
        kelompokhartaRepository.saveAndFlush(kelompokharta);
        int databaseSizeBeforeDelete = kelompokhartaRepository.findAll().size();

        // Get the kelompokharta
        restKelompokhartaMockMvc.perform(delete("/api/kelompokhartas/{id}", kelompokharta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Kelompokharta> kelompokhartaList = kelompokhartaRepository.findAll();
        assertThat(kelompokhartaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kelompokharta.class);
        Kelompokharta kelompokharta1 = new Kelompokharta();
        kelompokharta1.setId(1L);
        Kelompokharta kelompokharta2 = new Kelompokharta();
        kelompokharta2.setId(kelompokharta1.getId());
        assertThat(kelompokharta1).isEqualTo(kelompokharta2);
        kelompokharta2.setId(2L);
        assertThat(kelompokharta1).isNotEqualTo(kelompokharta2);
        kelompokharta1.setId(null);
        assertThat(kelompokharta1).isNotEqualTo(kelompokharta2);
    }
}
