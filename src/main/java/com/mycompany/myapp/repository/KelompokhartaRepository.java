package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Kelompokharta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Kelompokharta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KelompokhartaRepository extends JpaRepository<Kelompokharta,Long> {
    
}
