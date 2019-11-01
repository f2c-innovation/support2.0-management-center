package com.fit2cloud.support.service;

import com.fit2cloud.commons.server.base.domain.Category;
import com.fit2cloud.commons.server.base.domain.Dictionary;
import com.fit2cloud.commons.server.base.mapper.CategoryMapper;
import com.fit2cloud.support.dao.ext.ExtDictionaryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DictionaryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ExtDictionaryMapper extDictionaryMapper;

    public List<Category> getCategoryList(Category category){
        return extDictionaryMapper.getCategoryList(category);
    }

    public List<Dictionary> getDictionaryList(Dictionary dictionary){
        return extDictionaryMapper.getDictionaryList(dictionary);
    }

}
