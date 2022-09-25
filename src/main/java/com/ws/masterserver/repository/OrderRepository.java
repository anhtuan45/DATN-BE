package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.dashboard.EarningDayDto;
import com.ws.masterserver.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    @Query("select o.shipPrice from OrderEntity o where o.id = ?1")
    Long findPriceShipById(String id);

    @Query("select count(o.id) from OrderEntity o\n" +
            "where cast(o.createdDate as date) = cast(current_date as date)")
    Long getNumberToday();

    /**
     * @return doanh thu trong tuần
     */
    @Query("select sum(o.total)\n" +
            "from OrderEntity o\n" +
            "where o.payed = true\n" +
            "and extract('week' from o.createdDate) = extract('week' from current_date)")
    Long getEarningThisWeek();

    /**
     * @return doanh thu trong ngafy
     */
    @Query("select sum(o.total)\n" +
            "from OrderEntity o\n" +
            "where o.payed = true\n" +
            "and cast(o.createdDate as date) = cast(current_date as date)")
    Long getEarningToday();

    /**
     * @return doanh thu theo thứ trong tuần hiện tại
     */
    @Query("select new com.ws.masterserver.dto.admin.dashboard.EarningDayDto(\n" +
            "extract('dow' from o.createdDate) - 1 as dayOfWeek,\n" +
            "sum(o.total))\n" +
            "from OrderEntity o\n" +
            "where extract('week' from o.createdDate) = extract('week' from current_date) - ?1\n" +
            "and o.payed = true\n" +
            "group by dayOfWeek\n" +
            "order by dayOfWeek")
    List<EarningDayDto> getEarningWeekWithDay(int minusWeek);

    @Query(nativeQuery = true,
            value = "select\n" +
                    "\t(case\n" +
                    "\t\textract(dow\n" +
                    "\tfrom\n" +
                    "\t\to.updated_date)\n" +
                    "\t\twhen 0 then 7\n" +
                    "\t\telse extract(dow\n" +
                    "\tfrom\n" +
                    "\t\to.updated_date)\n" +
                    "\tend) + 1,\n" +
                    "\tsum(total)\n" +
                    "from\n" +
                    "\torders o\n" +
                    "where\n" +
                    "\tDATE_PART('week', o.updated_date) = DATE_PART('week', current_date)\n" +
                    "group by\n" +
                    "\textract(dow\n" +
                    "from\n" +
                    "\to.updated_date)")
    List<Object[]> getEarningThisWeekDashboard();

    /**
     * @return doanhh thu theo từng loại sản phẩm
     */
    @Query(nativeQuery = true,
            value = "select c.id ,\n" +
                    "c.name,\n" +
                    "sum(o.total) as total\n" +
                    "from category c \n" +
                    "left join product p on p.category_id = c.id \n" +
                    "left join product_option po on po.product_id = p.id \n" +
                    "left join order_detail od on od.product_option_id = po.id \n" +
                    "left join orders o on o.id = od.order_id \n" +
                    "where c.active = true\n" +
                    "group by c.id ,c.name\n" +
                    "having sum(o.total) is not null \n" +
                    "limit 3")
    List<Object[]> getCategoryRevenue();

    @Query(value = "SELECT nextval('codeseq')", nativeQuery = true)
    Long getNextCodeOrder();

    @Query(value = "SELECT * FROM orders o WHERE o.user_id = ?1 ORDER BY o.created_date desc", nativeQuery = true)
    List<OrderEntity> getMyOrder(String userId);

    @Query("select o\n" +
            "from OrderEntity o\n" +
            "where (o.code like :textSearch or o.note like :textSearch)\n" +
            "and (o.userId = :userId)\n" +
            "and (:status is null or o.status = :status)")
    Page<OrderEntity> search(@Param("textSearch") String textSearch, @Param("userId") String userId, @Param("status") String status, Pageable pageReq);

    @Query("select o from OrderEntity o where o.id = ?1")
    OrderEntity findByIdV1(String id);

    @Query("select orders from OrderEntity orders\n" +
            "left join UserEntity user on user.id = orders.userId\n" +
            "left join AddressEntity address on address.id = orders.addressId\n" +
            "where (:status is null or orders.status = :status)\n" +
            "and (:provinceId is null or address.provinceId = :provinceId)\n" +
            "and (:districtId is null or address.districtId = :districtId)\n" +
            "and (:wardCode is null or address.wardCode = :wardCode)\n" +
            "and (user.phone like :textSearch \n" +
            "or unaccent(upper(user.firstName)) like unaccent(:textSearch)\n" +
            "or unaccent(upper(user.lastName)) like unaccent(:textSearch)\n" +
            "or user.phone like :textSearch)")
    Page<OrderEntity> searchV2(@Param("status") String status,
                               @Param("provinceId") String provinceId,
                               @Param("districtId") String districtId,
                               @Param("wardCode") String wardCode,
                               @Param("textSearch") String textSearch,
                               Pageable pageable);

    @Query("select count(o.id) > 0\n" +
            "from OrderEntity o\n" +
            "where o.userId = ?1 and o.discountId = ?2")
    boolean checkCustomerHasUsedDiscount(String userId, String id);

    @Query("select count(o.id) from OrderEntity o where o.userId = ?1")
    Long countByUserId(String id);

    @Query(nativeQuery = true, value = "select o.created_date from orders o where o.user_id = :userId limit 1")
    Date findRecentOrderByCustomerId(@Param("userId") String userId);

    @Query("select count(o.id) > 0 from OrderEntity o where o.discountId = :discountId")
    boolean checkDiscountHasUsed(@Param("discountId") String discountId);

    /**
     * thông kế doanh số theo giờ
     */
    @Query(nativeQuery = true,
            value = "select\n" +
                    "to_char(updated_date, 'HH24') ,\n" +
                    "sum(total)\n" +
                    "from orders o\n" +
                    "where cast(o.updated_date as date) = ?1\n" +
                    "and o.payed = true\n" +
                    "group by to_char(updated_date, 'HH24')\n" +
                    "order by cast(to_char(updated_date, 'HH24') as int)")
    List<Object[]> getRevenueReportByHour(Date date);

    @Query(nativeQuery = true,
            value = "select\n" +
                    "to_char(updated_date, 'HH24') ,\n" +
                    "sum(total)\n" +
                    "from orders o\n" +
                    "where cast(o.updated_date as date) between ?1 and ?2\n" +
                    "and o.payed = true\n" +
                    "group by to_char(updated_date, 'HH24')\n" +
                    "order by cast(to_char(updated_date, 'HH24') as int)")
    List<Object[]> getRevenueReportByHour(Date startDate, Date endDate);

    @Query(nativeQuery = true,
            value = "select\n" +
                    "to_char(updated_date, 'HH24') ,\n" +
                    "sum(total)\n" +
                    "from orders o\n" +
                    "where cast(o.updated_date as date) = current_date - interval '1 day'\n" +
                    "and o.payed = true\n" +
                    "group by to_char(updated_date, 'HH24')\n" +
                    "order by cast(to_char(updated_date, 'HH24') as int)")
    List<Object[]> getRevenueYesterday();

    @Query(nativeQuery = true,
            value = "select\n" +
                    "to_char(updated_date, 'DD/MM/YYYY'),\n" +
                    "sum(total)\n" +
                    "from orders o\n" +
                    "where cast(o.updated_date as date) >= ?1\n" +
                    "and o.payed = true\n" +
                    "group by to_char(updated_date, 'DD/MM/YYYY')\n" +
                    "order by to_char(updated_date, 'DD/MM/YYYY')")
    List<Object[]> getRevenueReportByDay(Date date);

    @Query(nativeQuery = true,
            value = "select\n" +
                    "to_char(updated_date, 'DD/MM/YYYY'),\n" +
                    "sum(total)\n" +
                    "from orders o\n" +
                    "where cast(o.updated_date as date) between ?1 and ?2\n" +
                    "and o.payed = true\n" +
                    "group by to_char(updated_date, 'DD/MM/YYYY')\n" +
                    "order by to_char(updated_date, 'DD/MM/YYYY')")
    List<Object[]> getRevenueReportByDay(Date startDate, Date endDate);

    @Query(nativeQuery = true,
            value = "select\n" +
                    "to_char(updated_date, 'MM/YYYY'),\n" +
                    "sum(total)\n" +
                    "from orders o\n" +
                    "where cast(o.updated_date as date) >= ?1\n" +
                    "and o.payed = true\n" +
                    "group by to_char(updated_date, 'MM/YYYY')\n" +
                    "order by to_char(updated_date, 'MM/YYYY')")
    List<Object[]> getRevenueReportByMonth(Date dateByMonth);

    @Query(nativeQuery = true,
            value = "select\n" +
                    "to_char(updated_date, 'MM/YYYY'),\n" +
                    "sum(total)\n" +
                    "from orders o\n" +
                    "where cast(o.updated_date as date) between ?1 and ?2\n" +
                    "and o.payed = true\n" +
                    "group by to_char(updated_date, 'MM/YYYY')\n" +
                    "order by to_char(updated_date, 'MM/YYYY')")
    List<Object[]> getRevenueReportByMonth(Date startDate, Date endDate);


    @Query(nativeQuery = true,
            value = "select\n" +
                    "to_char(updated_date, 'YYYY'),\n" +
                    "sum(total)\n" +
                    "from orders o\n" +
                    "where cast(o.updated_date as date) >= ?1\n" +
                    "and o.payed = true\n" +
                    "group by to_char(updated_date, 'YYYY')\n" +
                    "order by to_char(updated_date, 'YYYY')")
    List<Object[]> getRevenueReportByYear(Date dateByYear);

    @Query(nativeQuery = true,
            value = "select\n" +
                    "to_char(updated_date, 'YYYY'),\n" +
                    "sum(total)\n" +
                    "from orders o\n" +
                    "where cast(o.updated_date as date) between ?1 and ?2\n" +
                    "and o.payed = true\n" +
                    "group by to_char(updated_date, 'YYYY')\n" +
                    "order by to_char(updated_date, 'YYYY')")
    List<Object[]> getRevenueReportByYear(Date startDateByYear, Date endDateByYear);

    @Query(value = "SELECT COUNT(*) FROM orders o WHERE o.user_id = ?1", nativeQuery = true)
    Integer countOrder(String userId);

    @Query(nativeQuery = true, value = "select\n" +
            "\tto_char(o.updated_date, 'DD/MM/YYYY') thoigian,\n" +
            "\tcount(o.id) sldonhang,\n" +
            "\tcoalesce(sum(od1.od_sub1_total), 0) doanhthu,\n" +
            "\tcoalesce(sum(o.shop_price_discount) + sum(od2.od_sub2_discount_total), 0) giamgia,\n" +
            "\t\tsum (case\n" +
            "\t\twhen o.status = 'REFUND'\n" +
            "\t\tor o.status = 'ACCEPT_REFUND' then o.total\n" +
            "\t\telse 0\n" +
            "\tend) tralaihang,\n" +
            "\tcoalesce(sum(od1.od_sub1_total), 0) - coalesce(sum(o.shop_price_discount) + sum(od2.od_sub2_discount_total), 0) doanhthuthuc,\n" +
            "\tcoalesce(sum(o1.o_sub1_ship_price), 0) vanchuyen,\n" +
            "\tcoalesce(sum(o.total), 0) tongdoanhthu,\n" +
            "\tcoalesce(sum(od3.od3_sub_qty), 0) sldathang,\n" +
            "\tcoalesce(sum(od4.od4_sub_qty), 0) sltralai,\n" +
            "\tcoalesce(sum(od3.od3_sub_qty), 0) - coalesce(sum(od4.od4_sub_qty), 0) slthuc\n" +
            "from\n" +
            "\torders o\n" +
            "left join (\n" +
            "\tselect\n" +
            "\t\tod_sub1.order_id as od_sub1_order_id,\n" +
            "\t\tsum(od_sub1.qty * od_sub1.price) as od_sub1_total\n" +
            "\tfrom\n" +
            "\t\torder_detail od_sub1\n" +
            "\tgroup by\n" +
            "\t\tod_sub1.order_id) od1 on\n" +
            "\tod1.od_sub1_order_id = o.id\n" +
            "left join (\n" +
            "\tselect\n" +
            "\t\tod_sub2.order_id as od_sub2_order_id,\n" +
            "\t\tsum(od_sub2.discount) as od_sub2_discount_total\n" +
            "\tfrom\n" +
            "\t\torder_detail od_sub2\n" +
            "\tgroup by\n" +
            "\t\tod_sub2.order_id) od2 on\n" +
            "\tod2.od_sub2_order_id = o.id\n" +
            "left join (\n" +
            "\tselect\n" +
            "\t\tto_char(o_sub1.updated_date, 'DD/MM/YYYY') as o_sub1_time,\n" +
            "\t\tsum(o_sub1.ship_price) as o_sub1_ship_price\n" +
            "\tfrom\n" +
            "\t\torders o_sub1\n" +
            "\tgroup by\n" +
            "\t\tto_char(o_sub1.updated_date, 'DD/MM/YYYY')) o1 on\n" +
            "\to1.o_sub1_time = to_char(o.updated_date, 'DD/MM/YYYY')\n" +
            "left join (\n" +
            "\tselect\n" +
            "\t\tod3_sub.order_id as od3_sub_order_id,\n" +
            "\t\tsum(od3_sub.qty) as od3_sub_qty\n" +
            "\tfrom\n" +
            "\t\torder_detail od3_sub\n" +
            "\tgroup by\n" +
            "\t\tod3_sub.order_id ) od3 on\n" +
            "\tod3.od3_sub_order_id = o.id\n" +
            "left join (\n" +
            "\tselect\n" +
            "\t\tod4_sub.order_id as od4_sub_order_id,\n" +
            "\t\tsum(od4_sub.qty) as od4_sub_qty\n" +
            "\tfrom\n" +
            "\t\torder_detail od4_sub\n" +
            "\tgroup by\n" +
            "\t\tod4_sub.order_id ) od4 on\n" +
            "\tod4.od4_sub_order_id = o.id\n" +
            "\tand o.status in ('REFUND', 'ACCEPT_REFUND')\n" +
            "where\n" +
            "\tcast(o.updated_date as date) between ?1 and ?2\n" +
            "group by\n" +
            "\tto_char(o.updated_date, 'DD/MM/YYYY')")
    List<Object[]> getRevenueDetailReport(Date startDate, Date endDate);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select o from OrderEntity o where o.id = ?1")
    OrderEntity findByIdLock(String id);

    @Query(nativeQuery = true,
            value = "select\n" +
                    "\t(case\n" +
                    "\t\textract(dow\n" +
                    "\tfrom\n" +
                    "\t\to.updated_date)\n" +
                    "\t\twhen 0 then 7\n" +
                    "\t\telse extract(dow\n" +
                    "\tfrom\n" +
                    "\t\to.updated_date)\n" +
                    "\tend) + 1,\n" +
                    "\tsum(total)\n" +
                    "from\n" +
                    "\torders o\n" +
                    "where\n" +
                    "\tDATE_PART('week', o.updated_date) = DATE_PART('week', current_date) - 1\n" +
                    "group by\n" +
                    "\textract(dow\n" +
                    "from\n" +
                    "\to.updated_date)")
    List<Object[]> getEarningLastWeekDashboard();
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select o from OrderEntity o where o.id = ?1 and o.userId = ?2")
    OrderEntity findByIdAndUserIdLock(String orderId, String userId);
}
