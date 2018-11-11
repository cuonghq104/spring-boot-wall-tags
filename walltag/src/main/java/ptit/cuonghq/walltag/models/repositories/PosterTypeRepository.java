package ptit.cuonghq.walltag.models.repositories;

import ptit.cuonghq.walltag.models.beans.PosterType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosterTypeRepository extends JpaRepository<PosterType, Integer> {
}
