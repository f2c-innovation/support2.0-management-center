package com.fit2cloud.support.service;


import com.fit2cloud.commons.utils.UUIDUtil;
import com.fit2cloud.support.dao.GradeServiceMapper;
import com.fit2cloud.support.model.GradeService;
import com.fit2cloud.support.model.GradeServiceExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class GradeServiceService {

    @Resource
    private GradeServiceMapper gradeServiceMapper;

    public List<GradeService> getGradeServiceList() {
        GradeServiceExample example = new GradeServiceExample();
        List<GradeService> resultList = gradeServiceMapper.selectByExampleWithBLOBs(example);
        return resultList;
    }

    public GradeService getGradeServiceById(String id){
        GradeService gradeService = gradeServiceMapper.selectByPrimaryKey(id);
        return gradeService;
    }
    public void insert(GradeService gradeService){
        gradeService.setId(UUIDUtil.newUUID());
        gradeService.setCreateTime(System.currentTimeMillis());
        gradeService.setUpdateTime(System.currentTimeMillis());
        gradeServiceMapper.insertSelective(gradeService);
    }

    public void delete(String gradeServiceId){
        gradeServiceMapper.deleteByPrimaryKey(gradeServiceId);
    }

    public void update(GradeService gradeService){
        gradeService.setUpdateTime(System.currentTimeMillis());
        gradeServiceMapper.updateByPrimaryKeyWithBLOBs(gradeService);
    }

}
