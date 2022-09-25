package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.report.discount.DiscountRevenueRes;
import com.ws.masterserver.dto.admin.report.discount.DiscountRevenueReq;
import com.ws.masterserver.repository.custom.AdminDiscountRevenueRepository;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.enums.DiscountStatusEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class AdminDiscountRevenueRepositoryImpl implements AdminDiscountRevenueRepository {
    private final EntityManager entityManager;

    @Override
    public PageData get(DiscountRevenueReq payload) {
        String sql = "select\n" +
                "\td.id ,\n" +
                "\td.code as code,\n" +
                "\td.status ,\n" +
                "\td.start_date ,\n" +
                "\td.end_date ,\n" +
                "\tcoalesce(sum(o.total), 0) as total\n" +
                "from\n" +
                "\tdiscount d\n" +
                "left join orders o on\n" +
                "\to.discount_id = d.id\n" +
                "\tand o.payed = true\n" +
                "where\n" +
                "\t1 = 1\n";
        String where = "";
        if (!StringUtils.isNullOrEmpty(payload.getStatus())) {
            DiscountStatusEnums status = DiscountStatusEnums.from(payload.getStatus());
            where += "and d.status = '" + status.name() + "'\n";
        }
        if (!StringUtils.isNullOrEmpty(payload.getTextSearch())) {
            String textSearch = "'%" + payload.getTextSearch().trim().toUpperCase() + "%'\n";
            where += "and upper(d.code) like " + textSearch;
        }
        String groupBy = "group by d.id\n";
        PageReq pageReq = payload.getPageReq();

        String orderBy = "order by " + pageReq.getSortField() + " " + pageReq.getSortDirection();
        sql += where + groupBy + orderBy;
        log.info("get() sql: {}", sql);
        Query query = entityManager.createNativeQuery(sql);
        Long totalElements = (long) query.getResultList().size();
        query.setFirstResult(pageReq.getPage());
        if (pageReq.getPageSize() != null) {
            query.setMaxResults(pageReq.getPageSize());
        }

        List<Object[]> objects = query.getResultList();
        return PageData.setResult(objects.stream().map(obj -> {
                    DiscountStatusEnums statusEnums = DiscountStatusEnums.from(JpaUtils.getString(obj[2]));

                    Date startDate = JpaUtils.getDate(obj[3]);
                    Date endDate = JpaUtils.getDate(obj[4]);
                    Long revenue = JpaUtils.getLong(obj[5]);
                    return DiscountRevenueRes.builder()
                            .id(JpaUtils.getString(obj[0]))
                            .code(JpaUtils.getString(obj[1]))
                            .status(StatusResUtils.getStatus4Discount(statusEnums))
                            .startDate(startDate)
                            .startDateFmt(startDate == null ? null : DateUtils.toStr(startDate, DateUtils.F_DDMMYYYYHHMM))
                            .endDate(endDate)
                            .endDateFmt(endDate == null ? null : DateUtils.toStr(endDate, DateUtils.F_DDMMYYYYHHMM))
                            .revenue(revenue)
                            .revenueFmt(MoneyUtils.formatV2(revenue))
                            .build();
                }).collect(Collectors.toList()),
                pageReq.getPage(),
                pageReq.getPageSize() == null ? 1 : pageReq.getPageSize(),
                totalElements);
    }
}
