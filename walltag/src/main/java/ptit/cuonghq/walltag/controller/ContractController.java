package ptit.cuonghq.walltag.controller;

import ptit.cuonghq.walltag.models.commons.ResponseFactory;
import ptit.cuonghq.walltag.models.commons.ResponseObjectResult;
import ptit.cuonghq.walltag.models.beans.Contract;
import ptit.cuonghq.walltag.models.beans.Place;
import ptit.cuonghq.walltag.models.beans.User;
import ptit.cuonghq.walltag.models.requestmodels.CreateNewContractRB;
import ptit.cuonghq.walltag.models.requestmodels.UpdateContractStatusRequestBody;
import ptit.cuonghq.walltag.services.ContractService;
import ptit.cuonghq.walltag.services.PlaceService;
import ptit.cuonghq.walltag.services.ProviderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    private ResponseEntity<ResponseObjectResult> createNewContract(@RequestHeader("Authorization") int idUser, @RequestBody CreateNewContractRB requestBody) {
        User user = authService.checkCustomer(idUser);
        if (user == null) {
            return ResponseFactory.authorizationError();
        }

        Place place = placeService.getPlace(requestBody.getIdPlace());
        if (place == null) {
            return ResponseFactory.badRequest("Place ID is not exist");
        }
        return contractService.createNewContract(user, place, requestBody);
    }


    @PutMapping(value = "/customer_approve/{id_contract}")
    @ApiOperation(value = "Khách hàng xác nhận thi công xong")
    private ResponseEntity<ResponseObjectResult> customerApprove(@PathVariable("id_contract") int idContract,
                                                                 @RequestHeader("Authorization") int idCustomer) {
        User customer = authService.checkCustomer(idCustomer);
        Contract contract = contractService.getContract(idContract);

        if (customer == null) {
            return ResponseFactory.authorizationError();
        }

        if (contract.getStatus().equalsIgnoreCase("ready")) {
            return contractService.customerApproveContract(contract);
        }

        return ResponseFactory.badRequest("Can't change this contract status");
    }


    @PutMapping(value = "/{id_contract}")
    @ApiOperation(value = "Update contract status")
    private ResponseEntity<ResponseObjectResult> updateContractStatus(@PathVariable("id_contract") int idContract,
                                                                      @RequestHeader("Authorization") int idProvider,
                                                                      @RequestParam("status") String status,
                                                                      @RequestBody Optional<UpdateContractStatusRequestBody> requestBody) {
        User user = authService.checkProvider(idProvider);
        Contract contract = contractService.getContract(idContract);

        if (user == null) {
            return ResponseFactory.authorizationError();
        }

        if (contract.getStatus().equalsIgnoreCase("waiting")) {

            if (status.equalsIgnoreCase("approve")) {
                return contractService.approveContract(contract);
            }

            if (status.equalsIgnoreCase("reject")) {

                UpdateContractStatusRequestBody body = requestBody.orElse(null);
                if (body != null && body.getRejectReason() == null) {
                    return ResponseFactory.badRequest("Reject reason required ");
                }

                return contractService.rejectContract(contract, body.getRejectReason());
            }

            return ResponseFactory.badRequest("New status can't be anything else but approve or reject ");
        }

        if (contract.getStatus().equalsIgnoreCase("approve")) {

            if (status.equalsIgnoreCase("ready")) {

                UpdateContractStatusRequestBody body = requestBody.orElse(null);
                if (body != null && body.getReadyImageUrl() == null) {
                    return ResponseFactory.badRequest("Ready image url required ");
                }

                return contractService.doneConstructionContract(contract, body.getReadyImageUrl());
            }

            return ResponseFactory.badRequest("New status can't be anything else but ready ");

        }

        if (contract.getStatus().equalsIgnoreCase("ca")) {

            if (status.equalsIgnoreCase("carryout")) {

                return contractService.carryOutContract(contract);
            }

            return ResponseFactory.badRequest("Status must be carryout ");
        }

        if (contract.getStatus().equalsIgnoreCase("carryout")) {

            if (status.equalsIgnoreCase("finish")) {

                return contractService.finishContract(contract);
            }

            return ResponseFactory.badRequest("Status must be finish ");
        }

        return ResponseFactory.badRequest("Failed");
    }

    @GetMapping("/waiting")
    @ApiOperation(value = "Get contract list of provider which is currently on waiting status")
    private ResponseEntity<ResponseObjectResult> getWaitingContractList(@RequestHeader("Authorization") int idProvider) {
        User user = authService.checkProvider(idProvider);
        if (user == null) {
            return ResponseFactory.authorizationError();
        }

        if (user.getRole().equalsIgnoreCase("customer")) {
            return ResponseFactory.authorizationError();
        }

        return contractService.getWaitingContractList(user);
    }

    @GetMapping("/{id_contract}")
    @ApiOperation(value = "Get contract's detail information")
    private ResponseEntity<ResponseObjectResult> getContractDetail(@PathVariable("id_contract") int idContract, @RequestHeader("Authorization") int idProvider) {
        User user = authService.checkProvider(idProvider);
        if (user == null) {
            return ResponseFactory.authorizationError();
        }

        Contract contract = contractService.getContract(idContract);
        if (contract == null) {
            return ResponseFactory.badRequest("Contract ID not exist");
        } else {
            return ResponseFactory.ok("Success", contract);
        }
    }

    @GetMapping("/place")
    @ApiOperation(value = "Lấy danh sách các hợp đồng hiện tại của địa điểm")
    private ResponseEntity<ResponseObjectResult> getContractByPlace(@RequestParam("id_place") int idPlace, @RequestHeader("Authorization") int idCustomer) {
        User user = authService.checkProvider(idCustomer);
        if (user == null) {
            return ResponseFactory.authorizationError();
        }
        return contractService.getContractByPlace(idPlace);
    }

}
