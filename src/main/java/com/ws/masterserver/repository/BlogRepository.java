
package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, String> {
    BlogEntity findByIdAndActive(String id, Boolean active);

    @Query(value = "select new com.ws.masterserver.dto.customer.blog.BlogResponse(\n" +
            "c.id,\n" +
            "c.content,\n" +
            "c.createdDate,\n" +
            "c.title,\n" +
            "c.topicId,\n" +
            "c.image,\n" +
            "c.description,\n" +
            "o.name," +
            "c.active) from BlogEntity c\n" +
            "left join TopicEntity o on o.id = c.topicId")
    List<BlogResponse> getAllBlog();


    @Query(value = "select new com.ws.masterserver.dto.customer.blog.BlogResponse(\n" +
            "c.id,\n" +
            "c.content,\n" +
            "c.createdDate,\n" +
            "c.title,\n" +
            "c.topicId,\n" +
            "c.image,\n" +
            "c.description,\n" +
            "o.name," +
            "c.active) from BlogEntity c\n" +
            "left join TopicEntity o on o.id = c.topicId where o.id=?1")
    List<BlogResponse> getAllBlogByTopic(String id);

    @Query(value = "select new com.ws.masterserver.dto.customer.blog.BlogResponse(\n" +
            "c.id,\n" +
            "c.content,\n" +
            "c.createdDate,\n" +
            "c.title,\n" +
            "c.topicId,\n" +
            "c.image,\n" +
            "c.description,\n" +
            "o.name," +
            "c.active) from BlogEntity c\n" +
            "left join TopicEntity o on o.id = c.topicId where c.id=?1")
    List<BlogResponse> getBlogDetail(String id);

    @Query("select o\n" +
            "from BlogEntity  o\n" +
            "WHERE (UNACCENT(UPPER(o.title)) LIKE CONCAT('%', UNACCENT(:textSearch), '%')\n" +
            "OR UNACCENT(UPPER(o.description)) LIKE CONCAT('%', UNACCENT(:textSearch), '%'))\n" +
            "AND (:active IS NULL OR o.active = :active)\n" +
            "AND (:topicId IS NULL OR o.topicId = :topicId)")
    Page<BlogEntity> search(@Param("textSearch") String textSearch,
                            @Param("active") Boolean active,
                            @Param("topicId") String topicId,
                            Pageable pageable);
}
