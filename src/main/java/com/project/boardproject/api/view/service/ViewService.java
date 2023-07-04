package com.project.boardproject.api.view.service;

import com.project.boardproject.api.view.mapper.ViewMapper;
import com.project.boardproject.api.view.model.ViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewService {
    @Autowired
    ViewMapper viewMapper;

    public boolean insertView(ViewDTO bId) {
       return viewMapper.insertView(bId);
    }

    public ViewDTO viewCnt(ViewDTO bId) {
        return viewMapper.viewCnt(bId);
    }

    public boolean updateView(ViewDTO bId) {
        return viewMapper.updateView(bId);
    }

    public List<Integer> viewCntAll() {
        return viewMapper.viewCntAll();
    }
}
