package com.project.boardproject.api.image.service;

import com.project.boardproject.api.image.mapper.ImageMapper;
import com.project.boardproject.api.image.model.ImageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ImageService {
    @Autowired
    ImageMapper imageMapper;

    public ArrayList<ImageDTO> selectAll(ImageDTO imageDTO) {
        return imageMapper.selectAll(imageDTO);
    }

    public ImageDTO selectOne(ImageDTO imageDTO) {
        return imageMapper.selectOne(imageDTO);
    }

    public boolean insertImage(ImageDTO imageDTO) {
        return imageMapper.insertImage(imageDTO);
    }

    public boolean deleteImage(ImageDTO imageDTO) {
        return imageMapper.deleteImage(imageDTO);
    }

}
