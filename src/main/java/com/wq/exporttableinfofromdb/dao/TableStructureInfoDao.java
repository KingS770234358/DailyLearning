package com.wq.exporttableinfofromdb.dao;

import com.wq.exporttableinfofromdb.pojo.TableStructureInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TableStructureInfoDao {


    @Select("show DATABASES")
    List<String> getAllDB();

    @Select("SELECT "
            + "table_name "
            + "FROM "
            + "information_schema.tables "
            + "WHERE table_schema = #{dbName} "
            + "AND table_type = 'base table'")
    List<String> getAllTablesByDBName(@Param("dbName") String dbName);

    @Select("SELECT "
            + "COLUMN_NAME,"
            + "DATA_TYPE,"
            + "COLUMN_TYPE,"
            + "CHARACTER_MAXIMUM_LENGTH,"
            + "IS_NULLABLE,"
            + "COLUMN_DEFAULT,"
            + "COLUMN_COMMENT "
            + "FROM INFORMATION_SCHEMA.COLUMNS "
            + "where table_schema = #{dbName} AND table_name  = #{tbName}")
    List<TableStructureInfo> getColumnInfoByTableName(@Param("dbName") String dbName, @Param("tbName") String tbName);
}
