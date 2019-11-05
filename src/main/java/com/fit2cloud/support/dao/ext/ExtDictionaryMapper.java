package com.fit2cloud.support.dao.ext;

import com.fit2cloud.commons.server.base.domain.Category;
import com.fit2cloud.commons.server.base.domain.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtDictionaryMapper {

    List<Category> getCategoryList(Category request);

    List<Dictionary> getDictionaryList(Dictionary dictionary);

    Object updateCategory(@Param("id") String id);

    Object updateDictionary(@Param("id") String id);

}
