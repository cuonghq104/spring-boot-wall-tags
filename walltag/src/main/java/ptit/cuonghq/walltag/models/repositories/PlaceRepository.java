package ptit.cuonghq.walltag.models.repositories;

import ptit.cuonghq.walltag.models.beans.Place;
import ptit.cuonghq.walltag.models.responsemodels.PlaceSearch;
import ptit.cuonghq.walltag.models.responsemodels.PlaceSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {

    @Query(nativeQuery = true)
    List<PlaceSummary> findPlaceByProviderId(@Param("id_provider") int idProvider);

    @Query(nativeQuery = true)
    List<PlaceSearch> searchPlace(@Param("lat") double latitude,
                                  @Param("lng") double longitude,
                                  @Param("distance") double distance,
                                  @Param("min_width") int minWidth,
                                  @Param("max_width") int maxWidth,
                                  @Param("min_height") int minHeight,
                                  @Param("max_height") int maxHeight,
                                  @Param("min_price") double minPrice,
                                  @Param("max_price") double maxPrice,
                                  @Param("wall_type") String wallType,
                                  @Param("poster_type") String posterType);

    @Query(value = "SELECT tbl_place.* FROM tbl_favorite_place\n" +
            "JOIN tbl_place ON id_place = tbl_place.id\n" +
            "WHERE id_customer = :id_customer", nativeQuery = true)
    List<Place> getFavoritePlace(@Param("id_customer") int idCustomer);

    @Query(value = "SELECT * FROM tbl_place\n" +
            "ORDER BY date_created DESC\n" +
            "LIMIT 10", nativeQuery = true)
    List<Place> getNewCreatePlace();
}
