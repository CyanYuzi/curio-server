package com.cyan.curioserver.mapper;


import com.cyan.curioserver.entity.Document;
import com.cyan.curioserver.vo.DocumentVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DocumentMapper {

    @Select("select id  from curio.document " +
            "where user_id = #{userId} AND name = #{name} AND file_hash = #{fileHash} AND deleted = 0 " +
            "LIMIT 1")
    Long findDuplicateId(
            @Param("userId") Long userId,
            @Param("name") String name,
            @Param("fileHash") String fileHash
    );

    @Select("SELECT id FROM curio.document where user_id = #{userId} AND name = #{name} AND deleted = 0 LIMIT 1")
    Long findIdByName(
            @Param("userId") Long userId,
            @Param("name") String name);

    @Insert("INSERT INTO curio.document" +
            "(user_id, name, size, content_type, storage_path, file_hash) " +
            "VALUES " +
            "(#{userId},#{name},#{size},#{contentType},#{storagePath},#{fileHash})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(Document document);


    @Select("select id,name ,size,created_at AS createAt from curio.document " +
            "where user_id = #{userId} AND deleted = 0 " +
            "ORDER BY created_at DESC , id DESC")
    Page<DocumentVO> pageQuery(@Param("userId") Long userId);

    @Select("SELECT id , name ,size,storage_path AS storagePath from curio.document " +
            "where id = #{id} AND user_id = #{userId} AND deleted = 0" )
    Document findAvailableById(@Param("id") Long id, @Param("userId") Long userId);

    @Update("UPDATE curio.document SET deleted = 1 " +
            "WHERE id = #{id} AND user_id = #{userId} AND deleted = 0")
    int softDelete(@Param("id") Long id, @Param("userId") Long userId);
}
