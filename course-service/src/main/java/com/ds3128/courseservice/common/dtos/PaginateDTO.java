package com.ds3128.courseservice.common.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaginateDTO {
    private int offset;
    private int limit;
}
