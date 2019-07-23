package com.liaojs.codegenerator.mapper;

import com.liaojs.codegenerator.model.Tables;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TableInfoMapper {

    List<Tables> listTable(@Param("tableSchema") String tableSchema);

}
