package com.seniorproject.mims.repository;

import com.seniorproject.mims.domain.Tip;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tip entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipRepository extends JpaRepository<Tip, Long> {

}
