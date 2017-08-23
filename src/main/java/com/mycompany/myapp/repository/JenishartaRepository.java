package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Jenisharta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Jenisharta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JenishartaRepository extends JpaRepository<Jenisharta,Long> {
    
}
