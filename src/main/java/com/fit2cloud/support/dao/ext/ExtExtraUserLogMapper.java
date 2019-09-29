package com.fit2cloud.support.dao.ext;


import com.fit2cloud.support.model.ExtraUserLog;

public interface ExtExtraUserLogMapper {

    ExtraUserLog selectByPrimaryKey(String name);

    void delete(String name);


}
