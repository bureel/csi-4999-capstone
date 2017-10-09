package org.jhipster.blog.repository.search;

import org.jhipster.blog.domain.Reportee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reportee entity.
 */
public interface ReporteeSearchRepository extends ElasticsearchRepository<Reportee, Long> {
}
