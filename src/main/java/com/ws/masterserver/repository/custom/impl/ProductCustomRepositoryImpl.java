package com.ws.masterserver.repository.custom.impl;

import com.ws.masterserver.dto.admin.product.search.ProductRes;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.dto.customer.product.ProductResponse;
import com.ws.masterserver.dto.customer.product.search.ProductDto;
import com.ws.masterserver.repository.custom.ProductCustomRepository;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.base.rest.PageData;
import com.ws.masterserver.utils.base.rest.PageReq;
import com.ws.masterserver.utils.common.*;
import com.ws.masterserver.utils.constants.WsCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unchecked")
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public PageData<ProductResponse> search(ProductReq req) {
        String prefix = "select\n";
        String select =
                "new com.ws.masterserver.dto.customer.product.ProductResponse(\n" +
                        "p.id as productId,\n" +
                        "p.name as productName,\n" +
                        "p.active as active,\n" +
                        "po.image as thumbnail,\n" +
                        "cast(po.price as string) as price,\n" +
                        "c.name as categoryName,\n" +
                        "p.des as description,\n" +
                        "m.name as materialName,\n" +
//                "t.name as typeName,\n" +
                        "p.createdDate as productCreatedDate,\n" +
                        "p.createdBy as productCreatedBy,\n" +
                        "trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))) as createdName)\n";
        String from = "from ProductEntity p\n";
        String joins = "left join CategoryEntity c on p.categoryId = c.id\n" +
                "left join MaterialEntity m on p.materialId = m.id\n" +
//                "left join TypeEntity t on p.typeId = t.id\n" +
                "left join UserEntity u on p.createdBy = u.id\n" +
                "left join ProductOptionEntity po on p.id = po.productId\n";
        String where = "where 1 = 1\n" +
                "and c.active = 1\n" +
                "and p.active = 1\n" +
                "and po.price in (select min(price) from ProductOptionEntity where po.productId = p.id)\n" +
                "and po.image in (select image from ProductOptionEntity where po.productId = p.id)\n";

        if (Boolean.FALSE.equals(StringUtils.isNullOrEmpty(req.getTextSearch()))) {
            where += "and (lower(p.name) like concat('%', '" + req.getTextSearch().trim().toLowerCase(Locale.ROOT) + "', '%')\n" +
                    "or lower(c.name) like concat('%', '" + req.getTextSearch().trim().toLowerCase(Locale.ROOT) + "', '%'))\n";
        }

        if (Boolean.FALSE.equals(StringUtils.isNullOrEmpty(req.getMinPrice()))
                && Boolean.FALSE.equals(StringUtils.isNullOrEmpty(req.getMaxPrice()))) {
            where += "and po.price BETWEEN " + req.getMinPrice().trim() + " AND " + req.getMaxPrice().trim() + "\n";
        }

        String order = "order by ";
        switch (req.getPageReq().getSortField()) {
            case "name":
                order += "p.name";
                break;
            case "price":
                order += "po.price";
                break;
            case "createdName":
                order += "u.lastName";
                break;
            default:
                order += "p.createdDate";
        }
        order += " " + req.getPageReq().getSortDirection() + "\n";

        //Result jpa
        String jpql = prefix + select + from + joins + where + order;

        log.info("JPQL: {}", jpql);

        javax.persistence.Query query = entityManager.createQuery(jpql);
        long totalElements = 0L;
        if (Boolean.FALSE.equals(query.getResultList().isEmpty())) totalElements = query.getResultList().size();


        query.setFirstResult(req.getPageReq().getPage() * req.getPageReq().getPageSize());
        query.setMaxResults(req.getPageReq().getPageSize());

        List<ProductResponse> productList = query.getResultList();

        if (productList.isEmpty()) {
            return new PageData<>(true);
        }

        productList.forEach(product -> {
            Long price = Long.valueOf(product.getPrice());
            String priceFormat = MoneyUtils.format(price);
            product.setPrice(priceFormat);
        });

        List productMoneyFormatedList = (List) productList;
        return PageData
                .setResult(
                        productMoneyFormatedList,
                        req.getPageReq().getPage(),
                        req.getPageReq().getPageSize(),
                        totalElements,
                        WsCode.OK);
    }

    @Override
    public Object searchV2(com.ws.masterserver.dto.customer.product.search.ProductReq req) {
        WsRepository repository = BeanUtils.getBean(WsRepository.class);
        String sql = "select distinct p1.id,\n" +
                "                p1.name as p1_name,\n" +
                "                po1.po_sub1_min_price,\n" +
                "                po1.po_sub1_max_price,\n" +
                "                p1.des,\n" +
                "                m1.name as m1_name,\n" +
                "                ct1.name as ct1_name,\n" +
                "                t1.name as t1_name\n," +
                "                p1.created_date\n" +
                "from product p1\n" +
                "         left join (\n" +
                "    select po_sub1.product_id as po_sub1_product_id,\n" +
                "           min(po_sub1.price) as po_sub1_min_price,\n" +
                "           max(po_sub1.price) as po_sub1_max_price\n" +
                "    from product_option po_sub1\n" +
                "    group by po_sub1.product_id) po1 on\n" +
                "        po1.po_sub1_product_id = p1.id\n" +
                "         left join material m1 on\n" +
                "        m1.id = p1.material_id\n" +
                "         left join category ct1 on\n" +
                "        ct1.id = p1.category_id\n" +
                "         left join type t1 on\n" +
                "        t1.id = ct1.type_id\n" +
                "         left join product_option po2 on\n" +
                "        po2.product_id = p1.id\n" +
                "where 1 = 1\n";


        if (!StringUtils.isNullOrEmpty(req.getCategory())) {
            String textSearch = req.getCategory().trim().toUpperCase(Locale.ROOT);
            String like = "concat('%', unaccent('" + textSearch +"'), '%')";
            sql += "and (unaccent(upper(ct1.slug)) like " + like + ")\n";
        }

        if (!StringUtils.isNullOrEmpty(req.getTextSearch())) {
            String textSearch = req.getTextSearch().trim().toUpperCase(Locale.ROOT);
            String like = "concat('%', unaccent('" + textSearch +"'), '%')";
            sql += "and (unaccent(upper(p1.name)) like " + like + ")\n";
        }


        if (!StringUtils.isNullOrEmpty(req.getMinPrice())) {
            sql += "and po1.po_sub1_min_price >= " + Long.valueOf(req.getMinPrice()) + "\n";
        }
        if (!StringUtils.isNullOrEmpty(req.getMaxPrice())) {
            sql += "and po1.po_sub1_min_price <= " + Long.valueOf(req.getMaxPrice()) + "\n";
        }
        if (!req.getColorIds().isEmpty()) {
            sql += "and po2.color_id in " + req.getColorIds().stream().map(o -> "'" + o + "'").collect(Collectors.joining(", ", "(", ")")) + "\n";
        }
        if (!req.getSizeIds().isEmpty()) {
            sql += "and po2.size_id in " + req.getSizeIds().stream().map(o -> "'" + o + "'").collect(Collectors.joining(", ", "(", ")")) + "\n";
        }

        sql += getOrderFilter(req.getPageReq());

        log.info("ProductCustomRepositoryImpl searchV2 sql: {}", sql);

        javax.persistence.Query query = entityManager.createNativeQuery(sql);

        Long totalElements = Long.valueOf(query.getResultList().size());

        List<Object[]> objects = query.getResultList();
        List<ProductDto> productDtos;
        List<ProductDto> res = new ArrayList<>();
        //List<Object> res = new ArrayList<>();

        if (!objects.isEmpty()) {
            productDtos = objects.stream().map(obj -> {
                String productId = JpaUtils.getString(obj[0]);
                return ProductDto.builder()
                        .id(productId)
                        .name(JpaUtils.getString(obj[1]))
                        .minPrice(JpaUtils.getLong(obj[2]))
                        .maxPrice(JpaUtils.getLong(obj[3]))
                        .des(JpaUtils.getString(obj[4]))
                        .materialName(JpaUtils.getString(obj[5]))
                        .categoryName(JpaUtils.getString(obj[6]))
                        .typeName(JpaUtils.getString(obj[7]))
                        .colors(repository.colorRepository.findDistinctByProductId(productId))
                        .sizes(repository.sizeRepository.findDistinctByProductId(productId))
                        .images(repository.productOptionRepository.findImageByProductId(productId))
                        .createdDate(JpaUtils.getDate(obj[8]))
                        .build();
            }).collect(Collectors.toList());

            Comparator compare;
            switch (req.getPageReq().getSortField()) {
                case "name":
                    compare = Comparator.comparing(ProductDto::getName);
                    break;
                case "price":
                    compare = Comparator.comparing(ProductDto::getMinPrice);
                    break;
                default:
                    compare = Comparator.comparing(ProductDto::getCreatedDate);
            }
            productDtos.sort(compare);

            if (StringUtils.isNullOrEmpty(req.getPageReq().getSortDirection()) || "desc".equals(req.getPageReq().getSortDirection())) {
                Collections.reverse(productDtos);
            }
            Integer page = req.getPageReq().getPage();
            if (page == null || page < 0){
                page = 0;
            }

            Integer pageSize = req.getPageReq().getPageSize();
            if (pageSize == null || pageSize < 1) {
                pageSize = 10;
            }

            int start = page * pageSize;
            if (start > totalElements) {
                start = totalElements.intValue();
            }
            int end = start + pageSize;
            if (end > totalElements) {
                end = totalElements.intValue();
            }

            res = productDtos.subList(start, end);
        }

        return PageData.setResult(res, req.getPageReq().getPage(), req.getPageReq().getPageSize(), totalElements);
    }

    @Override
    public Object search4Admin(ProductReq req, WsRepository repository) {
        String sql = "select p1.id as p1_id,\n" +
                "p1.name as p1_name,\n" +
                "coalesce((select min(po1.price) from product_option po1 where po1.product_id = p1.id), 0) as p1_min_price,\n" +
                "coalesce((select max(po2.price) from product_option po2 where po2.product_id = p1.id), 0) as p1_max_price,\n" +
                "coalesce((select sum(po3.qty) from product_option po3 where po3.product_id = p1.id), 0) as p1_qty,\n" +
                "p1.des as p1_des,\n" +
                "p1.active as p1_active,\n" +
                "p1.created_date as p1_created_date,\n" +
                "m1.name as m1_name,\n" +
                "ct1.name as ct1_name,\n" +
                "t1.name as t1_name,\n" +
                "coalesce((select count(po4.id) from product_option po4 where po4.product_id = p1.id), 0) as po_sum,\n" +
                "coalesce(p1.view_number, 0) as p1_view_number\n" +
                "from product p1\n" +
                "left join material m1 on m1.id = p1.material_id\n" +
                "left join category ct1 on ct1.id = p1.category_id\n" +
                "left join type t1 on t1.id = ct1.type_id\n" +
                "where 1 = 1\n";
        if (!StringUtils.isNullOrEmpty(req.getTextSearch())) {
            String textSearch = req.getTextSearch().trim().toUpperCase(Locale.ROOT);
            sql += "and unaccent(upper(p1.name)) like concat('%', unaccent('" + textSearch + "'), '%')\n";
        }

        if (null != req.getActive()) {
            sql += "and p1.active = " + req.getActive() + "\n";
        }

        if (!StringUtils.isNullOrEmpty(req.getTypeId())) {
            sql += "and t1.id = '" + req.getTypeId() + "'\n";
        }

        if (req.getCategoryId() != null) {
            sql += "and p1.category_id = '" + req.getCategoryId() + "'\n";
        }

        String orderField = "";
        String orderDirection = " " + (StringUtils.isNullOrEmpty(req.getPageReq().getSortDirection()) || "desc".equals(req.getPageReq().getSortDirection()) ? "desc" : "asc") + "\n";
        switch (req.getPageReq().getSortField()) {
            case "name":
                orderField = "p1.name";
                break;
            case "price":
                orderField = "p1_min_price";
                break;
            default:
                orderField = "p1.created_date";
        }
        sql += "order by " + orderField + orderDirection;
        log.info("search4Admin() sql: {}", sql);
        Query query = entityManager.createNativeQuery(sql);
        Long totalElements = (long) query.getResultList().size();
        query.setFirstResult(req.getPageReq().getPage() * req.getPageReq().getPageSize());
        query.setMaxResults(req.getPageReq().getPageSize());

        List<Object[]> objects = query.getResultList();
        if (objects.isEmpty()) {
            return PageData.setEmpty(req.getPageReq());
        }
        List<ProductRes> productRes = objects.stream().map(obj -> {
            final String id = JpaUtils.getString(obj[0]);
            final String name = JpaUtils.getString(obj[1]);
            final Long minPrice = JpaUtils.getLong(obj[2]);
            final Long maxPrice = JpaUtils.getLong(obj[3]);
            final Long qty = JpaUtils.getLong(obj[4]);
            final String des = JpaUtils.getString(obj[5]);
            final Boolean active = JpaUtils.getBoolean(obj[6]);
            final Date createdDate = JpaUtils.getDate(obj[7]);
            final String materialName = JpaUtils.getString(obj[8]);
            final String categoryName = JpaUtils.getString(obj[9]);
            final String typeName = JpaUtils.getString(obj[10]);
            final Long productOptionNumber = JpaUtils.getLong(obj[11]);
            final Long viewNumber = JpaUtils.getLong(obj[12]);
            final List<String> sizes = repository.sizeRepository.findDistinctByProductId(id);
            final List<String> colors = repository.colorRepository.findDistinctByProductId(id);
            final List<String> specificationStrings = repository.productOptionRepository.findSpecificationDto(id);
            final String specifications = specificationStrings.stream().collect(Collectors.joining(", "));
            Long soldNumber = repository.productRepository.countSellNumber(id).orElse(0L);
            Long reviewNumber = repository.reviewRepository.countByProductId(id).orElse(0L);
            return ProductRes.builder()
                    .id(id)
                    .name(name)
                    .minPrice(MoneyUtils.formatV2(minPrice))
                    .maxPrice(MoneyUtils.formatV2(maxPrice))
                    .qty(qty)
                    .des(des)
                    .active(active)
                    .activeName(active ? "Hoạt động" : "Ngừng hoạt động")
                    .activeClazz(active ? "success" : "danger")
                    .createdDate(createdDate)
                    .createdDateFmt(createdDate == null ? null : DateUtils.toStr(createdDate, DateUtils.F_DDMMYYYY))
                    .materialName(materialName)
                    .categoryName(categoryName)
                    .typeName(typeName)
                    .sizes(sizes)
                    .colors(colors)
                    .specifications(specifications)
                    .soldNumber(soldNumber)
                    .productOptionNumber(productOptionNumber)
                    .viewNumber(viewNumber)
                    .reviewNumber(reviewNumber)
                    .build();
        }).collect(Collectors.toList());
        return PageData.setResult(productRes,
                req.getPageReq().getPage(),
                req.getPageReq().getPageSize(),
                totalElements);
    }

    private String getOrderFilter(PageReq pageReq) {
        String sortDirection = " " + (pageReq.getSortDirection() == null ? "desc" : "asc");
        String sortField = "";
        String result = "order by ";
        switch (pageReq.getSortField()) {
            case "name":
                sortField = "p1.name";
                break;
            case "price":
                sortField = "po1.po_sub1_min_price";
                break;
            default:
                sortField = "p1.created_date";
        }
        return result + sortField + sortDirection;
    }
}
