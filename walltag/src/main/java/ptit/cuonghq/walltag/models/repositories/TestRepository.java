package ptit.cuonghq.walltag.models.repositories;

import ptit.cuonghq.walltag.models.beans.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer> {
}
