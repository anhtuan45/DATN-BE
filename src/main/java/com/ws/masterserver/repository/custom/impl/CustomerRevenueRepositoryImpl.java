package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.report.customer.CustomerReportReq;
import com.ws.masterserver.dto.admin.report.customer.CustomerReportRes;
import com.ws.masterserver.repository.custom.CustomerRevenueRepository;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.JpaUtils;
import com.ws.masterserver.utils.common.MoneyUtils;
import com.ws.masterserver.utils.common.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerRevenueRepositoryImpl implements CustomerRevenueRepository {

    private final EntityManager entityManager;

    @Override
    public Object get(CustomerReportReq req) {
        String sql = String.format("select u.id as userId,\n" +
                "sum(o.total) as turnover,\n" +
                "count(o.id) as purchases , \n" +
                "u.phone as phone ,\n" +
                "CONCAT (u.first_name ,' ',u.last_name) as fullName\n" +
                "from orders o \n" +
                "join users u on o.user_id = u.id \n" +
                "where 1 = 1\n");

        if(!StringUtils.isNullOrEmpty(req.getTextSearch())) {
            String textSearch = "'%" + req.getTextSearch().trim().toUpperCase() + "%'";
            sql += "and (unaccent(upper(trim(concat(u.first_name, ' ', u.last_name)))) like " + textSearch + " or\n" +
                    "u.phone like " + textSearch + ")\n";
        }


        sql += "and o.payed = true ";

        String groupBy = "group by u.id\n";

        sql += groupBy;

        com.ws.masterserver.utils.base.rest.PageReq pageReq = req.getPageReq();


        String orderField = "";
        String orderDirection = " " + (StringUtils.isNullOrEmpty(req.getPageReq().getSortDirection()) || "desc".equals(req.getPageReq().getSortDirection()) ? "desc" : "asc") + "\n";
        switch (req.getPageReq().getSortField()) {
            case "turnover":
                orderField = "turnover";
                break;
            case "purchases":
                orderField = "purchases";
                break;
            default:
                orderField = "u.id";
        }
        sql += "order by " + orderField + orderDirection;

        log.info("get() sql: {}", sql);

        Query query = entityManager.createNativeQuery(sql);
        Long totalElements = Long.valueOf(query.getResultList().size());
        query.setFirstResult(req.getPageReq().getPage() * req.getPageReq().getPageSize());
        query.setMaxResults(req.getPageReq().getPageSize());

        List<Object[]> objects = query.getResultList();


        return PageData.setResult(objects.stream().map(obj -> CustomerReportRes.builder()
                        .userId(JpaUtils.getString(obj[0]))
                        .turnover(MoneyUtils.formatV2(JpaUtils.getLong(obj[1])))
                        .purchases(JpaUtils.getInt(obj[2]))
                        .phone(JpaUtils.getString(obj[3]))
                        .fullName(JpaUtils.getString(obj[4]))
                        .build()).collect(Collectors.toList()),
                pageReq.getPage(),
                pageReq.getPageSize(),
                totalElements);
    }
}
