package com.seniorproject.mims.repository.search;

import com.seniorproject.mims.domain.Report;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Report entity.
 */
public interface ReportSearchRepository extends ElasticsearchRepository<Report, Long> {
}
