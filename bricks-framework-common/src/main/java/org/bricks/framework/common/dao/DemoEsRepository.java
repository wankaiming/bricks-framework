package org.bricks.framework.common.dao;

import org.bricks.framework.common.dto.DemoTestDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface DemoEsRepository extends ElasticsearchRepository<DemoTestDto, String> {

	DemoTestDto queryDemoInfoById(String id);

}
