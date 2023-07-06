package com.project.boardproject.api.image.mapper;

import com.project.boardproject.api.image.model.ImageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ImageMapper {

    ArrayList<ImageDTO> selectAll();
    ImageDTO selectOne(ImageDTO imageDTO);
    boolean insertImage(ImageDTO imageDTO);
    boolean deleteImage(ImageDTO imageDTO);
}
