package com.nttdata.costoconversion.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.costoconversion.application.input.purchase.InputPurchaseVO;
import com.nttdata.costoconversion.application.output.ResponseVO;
import com.nttdata.costoconversion.domain.service.PurchaseService;
import com.nttdata.costoconversion.infrastructure.adapter.util.CoreUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Purchase", description = "The purchase Api")
@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
	
	@Autowired
    private PurchaseService purchaseService;
	
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Operation(
            summary = "Create a purchase",
            description = "Create a purchase and persist in databases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("save")
    public Mono<ResponseVO> create(@RequestBody InputPurchaseVO data) {
    	  try {		
  			return Mono.justOrEmpty(CoreUtils.responseSuccess(purchaseService.savePurchase(data)));		
  		}catch(Exception e) {
  			 return Mono.justOrEmpty(CoreUtils.responseException(e));			
  		}
  	}    
}
