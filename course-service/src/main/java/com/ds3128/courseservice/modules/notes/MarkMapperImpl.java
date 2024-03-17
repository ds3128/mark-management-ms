package com.ds3128.courseservice.modules.notes;

import com.ds3128.courseservice.modules.notes.dtos.CreateMarkDto;
import com.ds3128.courseservice.modules.notes.dtos.MarkDtoResponse;
import com.ds3128.courseservice.modules.notes.dtos.UpdateMarkDto;
import com.ds3128.courseservice.modules.notes.entities.Mark;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class MarkMapperImpl {

    private final ModelMapper modelMapper = new ModelMapper();

    public Mark fromMarkDtoCreate(CreateMarkDto createMarkDto){
//        return this.modelMapper.map(createMarkDto, Mark.class);
        Mark mark = new Mark();
        BeanUtils.copyProperties(createMarkDto, mark);
        return mark;
    }

    public Mark fromMarkDtoUpdate(UpdateMarkDto updateMarkDto) {
//        return this.modelMapper.map(updateMarkDto, Mark.class);
        Mark mark = new Mark();
        BeanUtils.copyProperties(updateMarkDto, mark);
        return mark;
    }

    public MarkDtoResponse fromMark(Mark mark) {
        return this.modelMapper.map(mark, MarkDtoResponse.class);
    }


}
