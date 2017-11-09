package com.seniorproject.mims.repository;

import com.seniorproject.mims.domain.Report;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Report entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select report from Report report where report.user.login = ?#{principal.username}")
    Page<Report> findByUserIsCurrentUser(Pageable pageable);

}
