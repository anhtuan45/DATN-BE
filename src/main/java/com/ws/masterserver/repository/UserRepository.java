package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.user.CustomerResponse;
import com.ws.masterserver.dto.customer.user.UserDto;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.utils.base.rest.CurrentUser;
import com.ws.masterserver.utils.constants.enums.RoleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmailAndActive(String value, Boolean active);

    UserEntity findByIdAndActive(String id, Boolean aTrue);

    @Query("select new com.ws.masterserver.utils.base.rest.CurrentUser(\n" +
            "u.id,\n" +
            "trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))),\n" +
            "u.role,\n" +
            "u.email)\n" +
            "from UserEntity u\n" +
            "where u.active = true and u.id = :id")
    CurrentUser findCurrentUserByIdAndActive(@Param("id") String id);


    @Query("select new com.ws.masterserver.dto.customer.user.CustomerResponse(\n" +
            "u.id,\n" +
            "trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))),\n" +
            "u.role,\n" +
            "u.dob,\n" +
            "u.phone,\n" +
            "a.combination,\n" +
            "u.email)\n" +
            "from UserEntity u\n" +
            "JOIN AddressEntity a on a.userId = u.id\n" +
            "where u.active = true and a.isDefault = true and u.id = :id")
    CustomerResponse findCustomerById(@Param("id") String id);

    @Query("select new com.ws.masterserver.dto.customer.user.UserDto(" +
            "u.email,\n" +
            "u.password,\n" +
            "u.role)\n" +
            "from UserEntity u\n" +
            "where u.email = :email and u.active = :active")
    UserDto findUserDtoByEmail(@Param("email") String email, @Param("active") Boolean active);

    /**
     * @return số người dùng mới trong tuần
     */
    @Query("select count(u) from UserEntity u\n" +
            "where extract('week' from u.createdDate) = extract('week' from current_date)")
    Long countNewUserThisWeek();

    @Query("select distinct u\n" +
            "from UserEntity u\n" +
            "left join CustomerGroupEntity cg on cg.userId = u.id\n" +
            "left join CustomerTypeEntity ct on ct.id = cg.customerTypeId\n" +
            "where (unaccent(upper(u.firstName)) like unaccent(:textSearch)\n" +
            "or unaccent(upper(u.lastName)) like unaccent(:textSearch)\n" +
            "or unaccent(upper(u.email)) like unaccent(:textSearch)\n" +
            "or unaccent(upper(u.phone)) like unaccent(:textSearch))\n" +
            "and (:active is null or u.active = :active)\n" +
            "and (:role is null or u.role = :role)\n" +
            "and (:customerTypeId is null or ct.id = :customerTypeId)")
    Page<UserEntity> search(@Param("textSearch") String textSearch,
                            @Param("active") Boolean active,
                            @Param("role") RoleEnum role,
                            @Param("customerTypeId") String customerTypeId,
                            Pageable pageable);

    @Query("select trim(concat(coalesce(u.firstName, ''), ' ', coalesce(u.lastName, ''))) as name\n" +
            "from UserEntity u\n" +
            "where u.id = ?1")
    String findNameById(String createdBy);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhone(String phone);

    boolean existsByIdAndActive(String id, boolean active);

    boolean existsByPhoneAndIdNot(String phone, String id);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, String id);

    @Query("select (count(u) > 0) from UserEntity u where upper(u.email) = upper(?1) and u.active = ?2")
    boolean existsByEmailIgnoreCaseAndActive(String email, boolean b);

    @Query("select (count(u) > 0) from UserEntity u where u.phone = ?1 and u.active = ?2")
    boolean existsByPhoneAndActive(String trim, boolean b);

    @Query("select u from UserEntity u where u.active = true and (u.role = 'ROLE_CUSTOMER' or u.role = 'ROLE_STAFF') order by u.lastName")
    List<UserEntity> customerNoPage();

    @Query("select u.id\n" +
            "from UserEntity u\n" +
            "where u.active = true and u.role = ?1")
    List<String> findUserIdByRole(RoleEnum role);

    @Query("select u from UserEntity u where upper(u.email) = upper(?1) and u.active = ?2 and u.role = ?3")
    UserEntity findByEmailIgnoreCaseAndActiveAndRole(String email, Boolean active, RoleEnum role);

    @Query("SELECT u FROM UserEntity u\n" +
            "WHERE u.role = :role\n" +
            "AND (:active IS NULL OR u.active = :active)\n" +
            "AND (UNACCENT(UPPER(u.firstName)) LIKE CONCAT('%', UNACCENT(:textSearch) , '%')\n" +
            "OR UNACCENT(UPPER(u.lastName)) LIKE CONCAT('%', UNACCENT(:textSearch) , '%')\n" +
            "OR UNACCENT(UPPER(u.email)) LIKE CONCAT('%', UNACCENT(:textSearch) , '%')\n" +
            "OR u.phone LIKE CONCAT('%', UNACCENT(:textSearch) , '%'))")
    Page<UserEntity> searchCustomer(@Param("role") RoleEnum role,
                                    @Param("active") Boolean active,
                                    @Param("textSearch") String textSearch,
                                    Pageable pageable);

    @Query("select u from UserEntity u where u.id = ?1")
    UserEntity findByIdSkip(String updatedBy);

    /**
     * Thông kế người dùng theo giờ
     */
    @Query(nativeQuery = true, value = "select\n" +
            "to_char(created_date, 'HH24') ,\n" +
            "count(id)\n" +
            "from users u\n" +
            "where cast(u.created_date as date) = ?1\n" +
            "and u.\"role\" = 'ROLE_CUSTOMER'\n" +
            "group by to_char(created_date, 'HH24')\n" +
            "order by cast(to_char(created_date, 'HH24') as int)")
    List<Object[]> getUserReportByHour(Date date);

    @Query(nativeQuery = true, value = "select\n" +
            "to_char(created_date, 'HH24') ,\n" +
            "count(id)\n" +
            "from users u\n" +
            "where cast(u.created_date as date) between ?1 and ?2\n" +
            "and u.\"role\" = 'ROLE_CUSTOMER'\n" +
            "group by to_char(created_date, 'HH24')\n" +
            "order by cast(to_char(created_date, 'HH24') as int)")
    List<Object[]> getUserReportByHour(Date startDate, Date endDate);

    @Query(nativeQuery = true, value = "select\n" +
            "to_char(created_date, 'HH24') ,\n" +
            "count(id)\n" +
            "from users u\n" +
            "where cast(u.created_date as date) = current_date - interval '1 day'\n" +
            "and u.\"role\" = ?1\n" +
            "group by to_char(created_date, 'HH24')\n" +
            "order by cast(to_char(created_date, 'HH24') as int)")
    List<Object[]> getUserYesterday(String role);

    @Query(nativeQuery = true, value = "select\n" +
            "to_char(created_date, 'DD/MM/YYYY') ,\n" +
            "count(id)\n" +
            "from users u \n" +
            "where cast(u.created_date as date) >= ?1\n" +
            "and u.\"role\" = 'ROLE_CUSTOMER'\n" +
            "group by to_char(created_date, 'DD/MM/YYYY')\n" +
            "order by to_char(created_date, 'DD/MM/YYYY');\n")
    List<Object[]> getUserReportByDay(Date date);

    @Query(nativeQuery = true, value = "select\n" +
            "to_char(created_date, 'DD/MM/YYYY') ,\n" +
            "count(id)\n" +
            "from users u \n" +
            "where cast(u.created_date as date) between ?1 and ?2\n" +
            "and u.\"role\" = 'ROLE_CUSTOMER'\n" +
            "group by to_char(created_date, 'DD/MM/YYYY')\n" +
            "order by to_char(created_date, 'DD/MM/YYYY');\n")
    List<Object[]> getUserReportByDay(Date startDate, Date endDate);

    @Query(nativeQuery = true, value = "select\n" +
            "to_char(created_date, 'MM/YYYY') ,\n" +
            "count(id)\n" +
            "from users u \n" +
            "where cast(u.created_date as date) >= ?1\n" +
            "and u.\"role\" = 'ROLE_CUSTOMER'\n" +
            "group by to_char(created_date, 'MM/YYYY')\n" +
            "order by to_char(created_date, 'MM/YYYY');\n")
    List<Object[]> getUserReportByMonth(Date dateByMonth);

    @Query(nativeQuery = true, value = "select\n" +
            "to_char(created_date, 'MM/YYYY') ,\n" +
            "count(id)\n" +
            "from users u \n" +
            "where cast(u.created_date as date) between ?1 and ?2\n" +
            "and u.\"role\" = 'ROLE_CUSTOMER'\n" +
            "group by to_char(created_date, 'MM/YYYY')\n" +
            "order by to_char(created_date, 'MM/YYYY');\n")
    List<Object[]> getUserReportByMonth(Date startDate, Date endDate);


    @Query(nativeQuery = true, value = "select\n" +
            "to_char(u.created_date , 'YYYY'),\n" +
            "count(u.id)\n" +
            "from users u \n" +
            "where cast(u.created_date as date) >= ?1\n" +
            "and u.\"role\" = 'ROLE_CUSTOMER'\n" +
            "group by to_char(u.created_date, 'YYYY')\n" +
            "order by to_char(u.created_date, 'YYYY')")
    List<Object[]> getUserReportByYear(Date dateByYear);

    @Query(nativeQuery = true, value = "select\n" +
            "to_char(u.created_date , 'YYYY'),\n" +
            "count(u.id)\n" +
            "from users u \n" +
            "where cast(u.created_date as date) between ?1 and ?2\n" +
            "and u.\"role\" = 'ROLE_CUSTOMER'\n" +
            "group by to_char(u.created_date, 'YYYY')\n" +
            "order by to_char(u.created_date, 'YYYY')")
    List<Object[]> getUserReportByYear(Date startDateByYear, Date endDateByYear);

    @Query("select u from UserEntity u where u.id = ?1 and u.role = ?2")
    UserEntity findByIdAndRole(String userId, RoleEnum role);

    @Query("select u from UserEntity u where u.email = ?1 and u.active = true")
    UserEntity findCustomerByEmail(String email);
}
