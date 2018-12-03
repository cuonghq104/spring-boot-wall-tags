package ptit.cuonghq.walltag.models.repositories;

import org.springframework.data.jpa.repository.Modifying;
import ptit.cuonghq.walltag.models.beans.Contract;
import ptit.cuonghq.walltag.models.responsemodels.ContractByPlaceResponseModel;
import ptit.cuonghq.walltag.models.responsemodels.ContractSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query(nativeQuery = true)
    List<ContractSummary> getWaitingContracts(@Param("id_provider") int idProvider);

    @Query(nativeQuery = true)
    List<ContractByPlaceResponseModel> getContractByPlaceId(@Param("id_place") int idPlace);

    @Query(nativeQuery = true)
    List<String> checkDateOccupied(@Param("id_place") int idPlace, @Param("start_date") String startDate, @Param("end_date") String endDate);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_contract\n" +
            "SET status = 'reject'\n" +
            "WHERE id_place = :id_place\n" +
            "AND status = 'waiting'\n" +
            "AND (\n" +
            "\tdate_start < :start_date AND date_end > :start_date\n" +
            "    OR\n" +
            "    date_start < :end_date AND date_end > :end_date\n" +
            "    OR\n" +
            "    date_start > :start_date AND date_end < :end_date\n" +
            ")", nativeQuery = true)
    int updateOccupiedContract(@Param("id_place") int idPlace,
                               @Param("start_date") String startDate,
                               @Param("end_date") String endDate);
}
