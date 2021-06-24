package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getUserCredentials(Integer userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{userName}, #{key}), #{password}, #{userId}")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credential credential);
}
