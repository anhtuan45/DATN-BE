
package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.blog.BlogResponse;
import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.dto.customer.topic.TopicResponse;
import com.ws.masterserver.entity.BlogEntity;
import com.ws.masterserver.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, String> {

    @Query("select new com.ws.masterserver.dto.customer.topic.TopicResponse(" +
            "c.id,\n" +
            "c.name,\n" +
            "c.image)\n" +
            "from TopicEntity c")
    List<TopicResponse> getAllTopic();
}