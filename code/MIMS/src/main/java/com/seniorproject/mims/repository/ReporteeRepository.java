package com.seniorproject.mims.repository;

import com.seniorproject.mims.domain.Reportee;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Reportee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReporteeRepository extends JpaRepository<Reportee, Long> {

}
