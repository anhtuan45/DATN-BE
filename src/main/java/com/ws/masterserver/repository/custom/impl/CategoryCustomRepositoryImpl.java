package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.category.CategoryReq;
import com.ws.masterserver.dto.admin.category.CategoryRes;
import com.ws.masterserver.repository.custom.CategoryCustomRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Locale;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryCustomRepositoryImpl implements CategoryCustomRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public PageData<CategoryRes> search(CurrentUser currentUser, CategoryReq req) {
//        var prefix = "select\n";
//        var select = "new com.ws.masterserver.dto.admin.category.CategoryRes(\n" +
//                "c.id as categoryId,\n" +
//                "c.name as categoryName,\n" +
//                "c.des as categoryDes,\n" +
//                "c.active as categoryActive,\n" +
//                "c.createdDate as categoryCreatedDate,\n" +
//                "c.createdBy as categoryCreatedBy,\n" +
//                "trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))) as createdName)\n";
//        var from = "from CategoryEntity c\n";
//        var joins = "left join UserEntity u on c.createdBy = u.id\n";
//        var where = "where 1 = 1\n";
//        if (req.getActive() != null) {
//            where += "and c.active = " + req.getActive() + "\n";
//        }
//        if (Boolean.FALSE.equals(StringUtils.isNullOrEmpty(req.getTextSearch()))) {
//            where += "and (lower(c.name) like concat('%', '" + req.getTextSearch().trim().toLowerCase(Locale.ROOT) + "', '%')\n" +
//                    "or lower(c.des) like concat('%', '" + req.getTextSearch().trim().toLowerCase(Locale.ROOT) + "', '%'))\n";
//        }
//
//        var order = "order by ";
//        switch (req.getPageReq().getSortField()) {
//            case "name":
//                order += "c.name";
//                break;
//            case "des":
//                order += "c.des";
//                break;
//            case "active":
//                order += "c.active";
//                break;
//            case "createdName":
//                order += "u.lastName";
//                break;
//            default:
//                order += "c.createdDate";
//        }
//        order += " " + req.getPageReq().getSortDirection() + "\n";
//
//        //Result jpa
//        var jpql = prefix + select + from + joins + where + order;
//
//        log.info("JPQL: {}", jpql);
//
//        var query = entityManager.createQuery(jpql);
//        var totalElements = 0L;
//        if (Boolean.FALSE.equals(query.getResultList().isEmpty())) totalElements = query.getResultList().size();
//        query.setFirstResult(req.getPageReq().getPage() * req.getPageReq().getPageSize());
//        query.setMaxResults(req.getPageReq().getPageSize());
//
//        var categoryResList = query.getResultList();
//        if (categoryResList.isEmpty()) {
//            return new PageData<>(true);
//        }
//        return PageData
//                .setResult(
//                        categoryResList,
//                        req.getPageReq().getPage(),
//                        req.getPageReq().getPageSize(),
//                        totalElements,
//                        WsCode.OK);
        return null;
    }
}
