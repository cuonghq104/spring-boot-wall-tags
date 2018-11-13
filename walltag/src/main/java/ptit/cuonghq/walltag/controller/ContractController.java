package ptit.cuonghq.walltag.controller;

import ptit.cuonghq.walltag.models.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.Contract;
import ptit.cuonghq.walltag.models.beans.Place;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.requestmodels.CreateNewContractRB;
import ptit.cuonghq.walltag.models.responsemodels.ContractByPlaceResponseModel;
import ptit.cuonghq.walltag.services.ContractService;
import ptit.cuonghq.walltag.services.PlaceService;
import ptit.cuonghq.walltag.services.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "contract", description = "Api for both customer and provider in contract section", produces = "application/json", tags = {"Contract"})
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ProviderService authService;

    @Autowired
    private PlaceService placeService;

    @PostMapping
    @ApiOperation(value = "Create new contract")
    private ResponseEntity<ResponseObjectResult> createNewContract(@RequestHeader("Authorization") int idUser, CreateNewContractRB requestBody) {
        User user = authService.checkUser(idUser);
        if (user == null) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization Error", null), HttpStatus.UNAUTHORIZED);
        }

        Place place = placeService.getPlace(requestBody.getIdPlace());
        if (place == null) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 400, "Place ID is not exist", null), HttpStatus.BAD_REQUEST);
        }
        ResponseObjectResult result = contractService.createNewContract(user, place, requestBody);
        if (result.getCode() == 400) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }


    @PutMapping(value = "/{id_contract}")
    @ApiOperation(value = "Update contract status")
    private ResponseEntity<ResponseObjectResult> updateContractStatus(@PathVariable("id_contract") int idContract, @RequestHeader("Authorization") int idProvider, @RequestParam("status") String status) {
        User user = authService.checkUser(idProvider);
        Contract contract = contractService.getContract(idContract);

        if (user == null) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization Error", null), HttpStatus.UNAUTHORIZED);
        }

        if (!status.equalsIgnoreCase("approve") && !status.equalsIgnoreCase("reject")) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 404, "Status must be approve or reject", null), HttpStatus.BAD_REQUEST);

        }
        if (contract.getPlace().getUser().getId() != user.getId()) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 400, "You don't have permission to approve or reject this contract", null), HttpStatus.BAD_REQUEST);
        } else {

            if (!contract.getStatus().equalsIgnoreCase("waiting")) {
                return new ResponseEntity<>(new ResponseObjectResult(false, 400, "This contract request has already been " + contract.getStatus(), null), HttpStatus.BAD_REQUEST);
            }
            ResponseObjectResult result = contractService.updateContractStatus(contract, status);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/waiting")
    @ApiOperation(value = "Get contract list of provider which is currently on waiting status")
    private ResponseEntity<ResponseObjectResult> getWaitingContractList(@RequestHeader("Authorization") int idProvider) {
        User user = authService.checkUser(idProvider);
        if (user == null) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization Error", null), HttpStatus.UNAUTHORIZED);
        }

        if (user.getRole().equalsIgnoreCase("customer")) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization Error", null), HttpStatus.UNAUTHORIZED);
        }

        ResponseObjectResult result = contractService.getWaitingContractList(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id_contract}")
    @ApiOperation(value = "Get contract's detail information")
    private ResponseEntity<ResponseObjectResult> getContractDetail(@PathVariable("id_contract") int idContract, @RequestHeader("Authorization") int idProvider) {
        User user = authService.checkUser(idProvider);
        if (user == null) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization Error", null), HttpStatus.UNAUTHORIZED);
        }

        Contract contract = contractService.getContract(idContract);
        if (contract == null) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Contract ID not exist", null), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new ResponseObjectResult(true, 200, "Success", contract), HttpStatus.OK);
        }
    }

    @GetMapping("/place")
    @ApiOperation(value = "Lấy danh sách các hợp đồng hiện tại của địa điểm")
    private ResponseEntity<ResponseObjectResult> getContractByPlace(@RequestParam("id_place") int idPlace, @RequestHeader("Authorization") int idCustomer) {
        User user = authService.checkUser(idCustomer);
        if (user == null) {
            return new ResponseEntity<>(new ResponseObjectResult(false, 401, "Authorization Error", null), HttpStatus.UNAUTHORIZED);
        }

        ResponseObjectResult result = contractService.getContractByPlace(idPlace);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

    }

}
