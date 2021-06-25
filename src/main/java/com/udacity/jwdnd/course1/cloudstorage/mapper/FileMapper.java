package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getUserFiles(Integer userId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insert(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    void delete(Integer fileId, Integer userId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    File getFile(Integer fileId, Integer userId);

}
