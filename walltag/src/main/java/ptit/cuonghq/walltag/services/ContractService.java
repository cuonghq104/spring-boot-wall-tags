package ptit.cuonghq.walltag.services;

import org.springframework.http.ResponseEntity;
import ptit.cuonghq.walltag.models.commons.ResponseFactory;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.Contract;
import ptit.cuonghq.walltag.models.beans.Place;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.repositories.ContractRepository;
import ptit.cuonghq.walltag.models.requestmodels.CreateNewContractRB;
import ptit.cuonghq.walltag.models.responsemodels.ContractByPlace;
import ptit.cuonghq.walltag.models.responsemodels.ContractByPlaceResponseModel;
import ptit.cuonghq.walltag.models.responsemodels.ContractSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ptit.cuonghq.walltag.services.serviceinterface.ContractServiceInterface;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService implements ContractServiceInterface {

    @Autowired
    private ContractRepository repository;

    @Override
    public ResponseEntity<ResponseObjectResult> createNewContract(User user, Place place, CreateNewContractRB requestBody) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

        Contract contract = new Contract();

        String start = requestBody.getStartDate();
        String end = requestBody.getEndDate();

        List<String> result = repository.checkDateOccupied(requestBody.getIdPlace(), start, end);

        if (result.size() != 0) {
            return ResponseFactory.badRequest("Can't create contract. Date rented has been occupied");
        }
        contract.setCustomer(user);
        contract.setPlace(place);
        contract.setConstructionPrice(place.getConstructionPrice());
        contract.setRentingPrice(place.getPrice().getValue());

        try {
            contract.setDateEnd(new Date(sdf1.parse(requestBody.getEndDate()).getTime()));
            contract.setDateStart(new Date(sdf1.parse(requestBody.getStartDate()).getTime()));
        } catch (ParseException e) {
            return ResponseFactory.badRequest("Date wrong format dd-MM-yyyy");
        }

        contract.setNote(requestBody.getNote());
        contract.setPosterUrl(requestBody.getPosterUrl());
        contract.setStatus("waiting");

        Contract saved = repository.save(contract);
        return ResponseFactory.ok("Created", saved);
    }

    @Override
    public ResponseEntity<ResponseObjectResult> approveContract(Contract contract) {
        contract.setStatus("approve");
        Contract saved = repository.save(contract);

        int reject = repository.updateOccupiedContract(contract.getPlace().getId(), contract.getDateStartSqlFormat(), contract.getDateEndSqlFormat());
        return ResponseFactory.ok( "Approved, reject " + reject + " contracts", saved);
    }

    @Override
    public ResponseEntity<ResponseObjectResult> rejectContract(Contract contract, String reason) {
        contract.setRejectReason(reason);
        contract.setStatus("reject");
        Contract saved = repository.save(contract);
        return ResponseFactory.ok("Rejected", saved);
    }

    @Override
    public ResponseEntity<ResponseObjectResult> doneConstructionContract(Contract contract, String imageUrl) {
        contract.setReadyImageUrl(imageUrl);
        contract.setStatus("ready");
        Contract saved = repository.save(contract);
        return ResponseFactory.ok("OK", saved);
    }

    @Override
    public ResponseEntity<ResponseObjectResult> customerApproveContract(Contract contract) {
        contract.setStatus("ca");
        Contract saved = repository.save(contract);
        return ResponseFactory.ok("OK", saved);
    }

    @Override
    public ResponseEntity<ResponseObjectResult> carryOutContract(Contract contract) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date(System.currentTimeMillis()));
        String dateStart = contract.getDateStart();
        if (currentDate.equalsIgnoreCase(dateStart)) {
            contract.setStatus("carryout");
            Contract saved = repository.save(contract);
            return ResponseFactory.ok("OK", saved);
        } else {
            return ResponseFactory.badRequest("This contract can't be carryout until " + dateStart);
        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> finishContract(Contract contract) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date(System.currentTimeMillis()));
        String dateEnd = contract.getDateStart();
        if (currentDate.equalsIgnoreCase(dateEnd)) {
            contract.setStatus("finish");
            Contract saved = repository.save(contract);
            return ResponseFactory.ok("Finished", saved);
        } else {
            return ResponseFactory.badRequest("This contract can't be finish until");
        }
    }

    @Override
    public ResponseEntity<ResponseObjectResult> getWaitingContractList(User provider) {
        List<ContractSummary> contracts = repository.getWaitingContracts(provider.getId());
        return ResponseFactory.ok(contracts.size() + " result" + ((contracts.size() != 1) ? "s" : ""), contracts);
    }

    @Override
    public Contract getContract(int idContract) {
        Optional<Contract> contract = repository.findById(idContract);
        return contract.orElse(null);
    }

    @Override
    public ResponseEntity<ResponseObjectResult> getContractByPlace(int idPlace) {
        List<ContractByPlaceResponseModel> response = repository.getContractByPlaceId(idPlace);
        List<ContractByPlace> list = new ArrayList<>();
        for (ContractByPlaceResponseModel item : response) {
            ContractByPlace contract = new ContractByPlace();
            contract.setId(item.getId());
            contract.setDateStart(item.getDateStart());
            contract.setDateEnd(item.getDateEnd());
            contract.setConstructionPrice(item.getConstructionPrice());
            contract.setNote(item.getNote());
            contract.setPosterUrl(item.getPosterUrl());
            contract.setRentingPrice(item.getRentingPrice());
            contract.setStatus(item.getStatus());
            contract.setUser(new ContractByPlace.UserSummary(item.getIdCustomer(), item.getCustomerFirstName() + " " + item.getCustomerLastName(), item.getCustomerEmail(), item.getCustomerPhone(), item.getCustomerImageUrl()));
            list.add(contract);
        }
        return ResponseFactory.ok( list.size() + " contracts", list);
    }
}
