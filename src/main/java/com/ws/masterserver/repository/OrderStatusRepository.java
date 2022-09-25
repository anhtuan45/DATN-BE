package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.order.detail.StatusDto;
import com.ws.masterserver.entity.OrderDetailEntity;
import com.ws.masterserver.entity.OrderStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatusEntity, String> {

    /**
     * @param orderId(mã đơn hàng)
     * @return lịch sử đơn hàng
     */
    @Query("select new com.ws.masterserver.dto.admin.order.detail.StatusDto(\n" +
            "os.status,\n" +
            "os.createdDate,\n" +
            "u.role,\n" +
            "concat(u.firstName, ' ', u.lastName))\n" +
            "from OrderStatusEntity os\n" +
            "left join UserEntity u on u.id = os.createdBy\n" +
            "where os.orderId = :orderId\n" +
            "order by os.createdDate desc")
    List<StatusDto> findHistory(@Param("orderId") String orderId);

    /**
     * @return số đơn hàng đang cần xử lý
     */
    @Query("select count(os)\n" +
            "from OrderStatusEntity os\n" +
            "group by os.orderId\n" +
            "having count(os) = 1")
    List<Long> countPending();

    /**
     * @return số đơn hàng đã bị hủy/từ chối trong ngày
     */
    @Query("select count(os)\n" +
            "from OrderStatusEntity os\n" +
            "where os.status in ('REJECT', 'CANCEL')\n" +
            "and cast(os.createdDate as date) = cast(current_date as date)")
    Long countRejectAndCancelToday();

    @Query(value = "SELECT * FROM order_status os WHERE os.order_id = ?1",nativeQuery = true)
    OrderStatusEntity findByOrderId(String orderId);
}
