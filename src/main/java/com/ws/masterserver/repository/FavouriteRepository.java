package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.favourite.response.FavouriteResponse;
import com.ws.masterserver.entity.FavouriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteEntity, String> {

    @Query(value = "select distinct on (p.id) p.id AS productId,p.name AS productName,f.id AS favouriteId,po.price AS price,po.image AS image \n" +
            "from favourite f \n" +
            "join product p on p.id = f.product_id \n" +
            "join product_option po on p.id = po.product_id \n" +
            "where f.user_id = ?1",nativeQuery = true)
    List<FavouriteResponse> getListFavourites(String userId);


    boolean existsByProductId(String productId);

    FavouriteEntity findByProductId(String productId);

    @Query("SELECT COUNT(f.id) FROM FavouriteEntity f WHERE f.userId = :userId")
    Integer countFavourite(@Param("userId") String userId);
}
