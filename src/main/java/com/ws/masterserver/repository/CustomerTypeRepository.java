package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.user.search.CustomerTypeDto;
import com.ws.masterserver.entity.CustomerTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerTypeEntity, String> {

    @Query("select ct from CustomerTypeEntity ct where ct.active = true order by ct.name")
    List<CustomerTypeEntity> noPage();

    @Query("select c from CustomerTypeEntity c where c.id = ?1 and c.active = ?2")
    CustomerTypeEntity findByIdAndActive(String customerTypeId, boolean b);

    @Query("select new com.ws.masterserver.dto.admin.user.search.CustomerTypeDto(\n" +
            "ct.id,\n" +
            "ct.name)" +
            "from CustomerTypeEntity ct\n" +
            "left join CustomerGroupEntity cg on cg.customerTypeId = ct.id\n" +
            "where cg.userId = ?1")
    List<CustomerTypeDto> findByCustomerId(String id);
}
