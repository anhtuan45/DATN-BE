package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.report.product.ProductRevenueReq;
import com.ws.masterserver.dto.admin.report.product.ProductRevenueRes;
import com.ws.masterserver.repository.custom.ProductRevenueRepository;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.common.JpaUtils;
import com.ws.masterserver.utils.common.MoneyUtils;
import com.ws.masterserver.utils.common.PageableUtils;
import com.ws.masterserver.utils.common.StringUtils;
import com.ws.masterserver.utils.validator.auth.AuthValidator;
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
@SuppressWarnings("unchecked")
public class ProductRevenueRepositoryImpl implements ProductRevenueRepository {
    private static final String PRODUCT_NAME_SQL = "p.name";
    private static final String CATEGORY_NAME_SQL = "c.name";
    private static final String TOTAL_SQL = "coalesce(sum(o.total), 0)";

    private final EntityManager entityManager;

    @Override
    public PageData get(ProductRevenueReq payload) {
        String sql = String.format("select p.id ,\n" +
                "%s as productName,\n" +
                "c.name as categoryName,\n" +
                "coalesce(count(po.id), 0) as optionNumber,\n" +
                "%s as total\n" +
                "from product p \n" +
                "left join product_option po on po.product_id = p.id \n" +
                "left join order_detail od on od.product_option_id = po.id \n" +
                "left join orders o on o.id = od.order_id \n" +
                "left join category c on c.id = p.category_id \n" +
                "where 1 = 1\n", PRODUCT_NAME_SQL, TOTAL_SQL);
        if (!StringUtils.isNullOrEmpty(payload.getTextSearch())) {
            String textSearch = "concat('%', unaccent('" + payload.getTextSearch().trim().toUpperCase() + "'), '%')";
            sql += String.format("and (unaccent(upper(%s)) like %s\n" +
                    "or unaccent(upper(%s)) like %s)\n", PRODUCT_NAME_SQL, textSearch, CATEGORY_NAME_SQL, textSearch);
        }
        if (!StringUtils.isNullOrEmpty(payload.getCategoryId())) {
            sql += String.format("and c.id = '%s'", payload.getCategoryId());
        }

        com.ws.masterserver.utils.base.rest.PageReq pageReq = payload.getPageReq();

        String groupBy = "group by p.id, p.name, c.name\n";
        sql += groupBy;

        String orderBy = PRODUCT_NAME_SQL;
        if (!StringUtils.isNullOrEmpty(pageReq.getSortField()) && "total".equalsIgnoreCase(pageReq.getSortField())) {
            orderBy = TOTAL_SQL;
        }

        sql += String.format("order by %s %s", orderBy, pageReq.getSortDirection());

        log.info("get() sql: {}", sql);
        Query query = entityManager.createNativeQuery(sql);

        Long totalElements = (long) query.getResultList().size();

        query.setFirstResult(pageReq.getPage());
        query.setMaxResults(pageReq.getPageSize());

        List<Object[]> objects = query.getResultList();

        return PageData.setResult(objects.stream().map(obj -> {
                    Long revenue = JpaUtils.getLong(obj[4]);
                    return ProductRevenueRes.builder()
                            .id(JpaUtils.getString(obj[0]))
                            .name(JpaUtils.getString(obj[1]))
                            .categoryName(JpaUtils.getString(obj[2]))
                            .optionNumber(JpaUtils.getLong(obj[3]))
                            .revenue(revenue)
                            .revenueFmt(MoneyUtils.formatV2(revenue))
                            .build();
                }).collect(Collectors.toList()),
                pageReq.getPage(),
                pageReq.getPageSize(),
                totalElements);
    }
}
