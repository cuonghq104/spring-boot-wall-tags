package ptit.cuonghq.walltag.models.repositories;

import ptit.cuonghq.walltag.models.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM tbl_user WHERE (phone = :phoneOrEmail and password = :password) OR (email = :phoneOrEmail AND password = :password)", nativeQuery = true)
    User login(@Param("phoneOrEmail") String phoneOrEmail, @Param("password") String password);

    @Query(value = "SELECT * FROM tbl_user WHERE id_facebook = :idFacebook", nativeQuery = true)
    User findProviderByFacebookId(@Param("idFacebook") String idFacebook);

    @Query(value = "SELECT * FROM tbl_user WHERE phone = :phoneOrEmail", nativeQuery = true)
    User findUserByPhone(@Param("phoneOrEmail") String phone);

    @Query(value = "SELECT * FROM tbl_user WHERE email = :phoneOrEmail", nativeQuery = true)
    User findUserByEmail(@Param("phoneOrEmail") String email);
}
