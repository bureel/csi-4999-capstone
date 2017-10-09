package org.jhipster.blog.repository.search;

import org.jhipster.blog.domain.Report;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Report entity.
 */
public interface ReportSearchRepository extends ElasticsearchRepository<Report, Long> {
}
