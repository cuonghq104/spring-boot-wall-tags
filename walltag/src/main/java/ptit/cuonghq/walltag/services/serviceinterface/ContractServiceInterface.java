package ptit.cuonghq.walltag.services.serviceinterface;

import org.springframework.http.ResponseEntity;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.Contract;
import ptit.cuonghq.walltag.models.beans.Place;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.requestmodels.CreateNewContractRB;

public interface ContractServiceInterface {

    ResponseEntity<ResponseObjectResult> createNewContract(User user, Place place, CreateNewContractRB requestBody);

    ResponseEntity<ResponseObjectResult> approveContract(Contract contract);

    ResponseEntity<ResponseObjectResult> rejectContract(Contract contract, String reason);

    ResponseEntity<ResponseObjectResult> doneConstructionContract(Contract contract, String imageUrl);

    ResponseEntity<ResponseObjectResult> customerApproveContract(Contract contract);

    ResponseEntity<ResponseObjectResult> carryOutContract(Contract contract);

    ResponseEntity<ResponseObjectResult> finishContract(Contract contract);

    ResponseEntity<ResponseObjectResult> getWaitingContractList(User provider);

    Contract getContract(int idContract);

    ResponseEntity<ResponseObjectResult> getContractByPlace(int idPlace);
}
