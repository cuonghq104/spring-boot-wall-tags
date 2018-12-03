package ptit.cuonghq.walltag.models.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ptit.cuonghq.walltag.models.beans.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    @Query(value = "SELECT * FROM tbl_image WHERE type = :type AND belong_to = :belong_to", nativeQuery = true)
    List<Image> getImages(@Param("type") String type, @Param("belong_to") int belongTo);
}
