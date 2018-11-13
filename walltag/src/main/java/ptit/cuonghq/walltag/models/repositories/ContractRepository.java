package ptit.cuonghq.walltag.models.repositories;

import ptit.cuonghq.walltag.models.beans.Contract;
import ptit.cuonghq.walltag.models.responsemodels.ContractByPlaceResponseModel;
import ptit.cuonghq.walltag.models.responsemodels.ContractSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query(nativeQuery = true)
    List<ContractSummary> getWaitingContracts(@Param("id_provider") int idProvider);

    @Query(nativeQuery = true)
    List<ContractByPlaceResponseModel> getContractByPlaceId(@Param("id_place") int idPlace);
}
