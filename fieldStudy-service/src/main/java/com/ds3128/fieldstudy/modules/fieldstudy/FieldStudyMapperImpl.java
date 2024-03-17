package com.ds3128.fieldstudy.modules.fieldstudy;

import com.ds3128.fieldstudy.modules.fieldstudy.dtos.CreateFieldStudyDto;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.UpdateFieldStudyDto;
import com.ds3128.fieldstudy.modules.fieldstudy.dtos.FieldStudyDtoResponse;
import com.ds3128.fieldstudy.modules.fieldstudy.entities.FieldStudy;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class FieldStudyMapperImpl {

    private final ModelMapper modelMapper = new ModelMapper();

    public FieldStudy fromFieldStudyDtoCreate(CreateFieldStudyDto createFieldStudyDto){
        return modelMapper.map(createFieldStudyDto, FieldStudy.class);
    }

    public FieldStudy fromFieldStudyDtoUpdate(UpdateFieldStudyDto fieldStudyDtoRequest){
        return modelMapper.map(fieldStudyDtoRequest, FieldStudy.class);
    }

    public FieldStudyDtoResponse fromFieldStudy(FieldStudy fieldStudy){
        return modelMapper.map(fieldStudy, FieldStudyDtoResponse.class);
    }

    public FieldStudy fromFieldStudyResponse(FieldStudyDtoResponse fieldStudy){
        return modelMapper.map(fieldStudy, FieldStudy.class);
    }
}
