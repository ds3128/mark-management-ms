package com.ds3128.fieldstudy.modules.graduate;

import com.ds3128.fieldstudy.modules.graduate.dtos.CreateGraduateLevelDto;
import com.ds3128.fieldstudy.modules.graduate.dtos.UpdateGraduateLevelDto;
import com.ds3128.fieldstudy.modules.graduate.dtos.GraduateLevelDtoResponse;
import com.ds3128.fieldstudy.modules.graduate.entities.GraduateLevel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GraduateLevelMapperImpl {

    private final ModelMapper modelMapper = new ModelMapper();

    public GraduateLevel fromGraduateLevelDto(CreateGraduateLevelDto createGraduateLevelDto){
        return modelMapper.map(createGraduateLevelDto, GraduateLevel.class);
    }

    public GraduateLevel fromGraduateLevelDtoUpdate(UpdateGraduateLevelDto graduateLevelDtoRequest){
        return modelMapper.map(graduateLevelDtoRequest, GraduateLevel.class);
    }

    public GraduateLevelDtoResponse fromGraduateLevel(GraduateLevel graduateLevel){
        return modelMapper.map(graduateLevel, GraduateLevelDtoResponse.class);
    }
}
