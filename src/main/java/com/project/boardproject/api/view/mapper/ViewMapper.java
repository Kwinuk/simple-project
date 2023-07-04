package com.project.boardproject.api.view.mapper;

import com.project.boardproject.api.view.model.ViewDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewMapper {

    boolean insertView(ViewDTO bId);

    ViewDTO viewCnt(ViewDTO bId);

    boolean updateView(ViewDTO bId);

    List<Integer> viewCntAll();

}
