package com.ds3128.courseservice.modules.client;

import com.ds3128.courseservice.common.types.PaginateResponse;
import com.ds3128.courseservice.modules.models.GraduateLevel;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "FIELDSTUDY-SERVICE")
public interface FieldStudyRestClient {

    @GetMapping("graduate/findOneGraduateById/{idLevel}")
    @Cacheable("graduateLevelByIdCache")
    GraduateLevel findOneGraduate(@PathVariable String idLevel);

    @GetMapping("graduate/findAll")
    @Cacheable("allGraduateLevelCache")
    PaginateResponse<GraduateLevel> findAllGraduate(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "10") int limit);
}
