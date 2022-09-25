package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.address.AddressRes;
import com.ws.masterserver.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, String> {
//    Boolean existsByWardCodeAndExactIgnoreCase(String wardCode, String trim);

    @Query(value = "SELECT * FROM address a WHERE a.user_id = ?1 and a.is_default = true",nativeQuery = true)
    AddressEntity findAddressIsDefaultByUserId(String userId);

    @Query("select new com.ws.masterserver.dto.customer.address.AddressRes(" +
            "a.id,\n" +
            "a.nameOfRecipient,\n" +
            "a.exact,\n" +
            "a.provinceId,\n" +
            "a.provinceName,\n" +
            "a.districtId,\n" +
            "a.districtName,\n" +
            "a.wardCode,\n" +
            "a.wardName,\n" +
            "a.combination,\n" +
            "a.phoneNumber,\n" +
            "a.userId,\n" +
            "a.isDefault,\n" +
            "a.active)\n" +
            "from AddressEntity a\n" +
            "JOIN UserEntity u on a.userId = u.id\n" +
            "where u.id = :userId\n" +
            "ORDER BY a.isDefault DESC")
    List<AddressRes> getListAddressByUserId(@Param("userId") String userId);

    @Query(value = "SELECT * FROM address a WHERE a.user_id = ?1",nativeQuery = true)
    List<AddressEntity> getAddressByUserId(String userId);

    @Query("select a from AddressEntity a where a.id = ?1 and a.userId = ?2")
    AddressEntity findByIdAndUserId(String addressId, String userId);

    @Query("select a from AddressEntity a where a.userId = :userId and a.isDefault = true")
    AddressEntity findAddressIsDefault(@Param("userId") String userId);
}
