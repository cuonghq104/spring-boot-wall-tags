package ptit.cuonghq.walltag.services;

import ptit.cuonghq.walltag.models.ResponseObjectResult;
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

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {

    @Autowired
    private ContractRepository repository;

    public ResponseObjectResult createNewContract(User user, Place place, CreateNewContractRB requestBody) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

        Contract contract = new Contract();
        contract.setCustomer(user);
        contract.setPlace(place);

        try {
            contract.setDateEnd(new Date(sdf1.parse(requestBody.getEndDate()).getTime()));
            contract.setDateStart(new Date(sdf1.parse(requestBody.getStartDate()).getTime()));
        } catch (ParseException e) {
            return new ResponseObjectResult(false, 400, "Date wrong format dd-MM-yyyy", null);
        }

        contract.setNote(requestBody.getNote());
        contract.setPosterUrl(requestBody.getPosterUrl());
        contract.setStatus("Waiting");

        Contract saved = repository.save(contract);
        return new ResponseObjectResult(true, 201, "Created", saved);
    }

    public ResponseObjectResult updateContractStatus(Contract contract, String status) {
        contract.setStatus(status);
        Contract saved = repository.save(contract);
        return new ResponseObjectResult(true, 201, (status.equalsIgnoreCase("reject") ? "Rejected" : "Approved"), saved);
    }

    public ResponseObjectResult getWaitingContractList(User provider) {
        List<ContractSummary> contracts = repository.getWaitingContracts(provider.getId());
        return new ResponseObjectResult(true, 200, contracts.size() + " result" + ((contracts.size() != 1) ? "s" : ""), contracts);
    }

    public Contract getContract(int idContract) {
        Optional<Contract> contract = repository.findById(idContract);
        return contract.orElse(null);
    }

    public ResponseObjectResult getContractByPlace(int idPlace) {
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
        return new ResponseObjectResult(true, 200, list.size() + " contracts", list);
    }
}
