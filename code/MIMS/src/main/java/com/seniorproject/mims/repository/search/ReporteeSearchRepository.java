package com.seniorproject.mims.repository.search;

import com.seniorproject.mims.domain.Reportee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reportee entity.
 */
public interface ReporteeSearchRepository extends ElasticsearchRepository<Reportee, Long> {
}
