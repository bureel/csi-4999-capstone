package com.seniorproject.mims.repository;

import com.seniorproject.mims.domain.VictimPhoto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VictimPhoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VictimPhotoRepository extends JpaRepository<VictimPhoto, Long> {

}
