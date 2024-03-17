package com.ds3128.userservice.model;
import com.ds3128.userservice.enums.MarkStatus;
import com.ds3128.userservice.enums.Mention;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class Mark {
    private Long idMark;

    private double value;

    private String observation;

    private Mention mention;

    private MarkStatus markStatus;

    private Assignment assignment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
