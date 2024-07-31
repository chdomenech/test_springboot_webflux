package com.nttdata.costoconversion.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.costoconversion.application.input.conversion.InputConversionVO;
import com.nttdata.costoconversion.application.output.ConvertionOutputVO;
import com.nttdata.costoconversion.application.output.ResponseVO;
import com.nttdata.costoconversion.domain.service.ConversionService;
import com.nttdata.costoconversion.infrastructure.adapter.util.CoreUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;


@Tag(name = "Conversion", description = "The conversion Api")
@RestController
@RequestMapping("/api/conversion")
public class ConversionController {

	@Autowired
    private ConversionService conversionService;

    public ConversionController(ConversionService convertionService) {
        this.conversionService = convertionService;
    }

    @Operation(
            summary = "Create a conversion",
            description = "Create a conversion cost and persist in databases")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
            	    content = { @Content(mediaType = "application/json", 
            	      schema = @Schema(implementation = ConvertionOutputVO.class)) }),
            
            @ApiResponse(responseCode = "400", description = "Not found",content = { @Content(mediaType = "application/json", 
          	      schema = @Schema(implementation = ResponseVO.class))}),
            		
            @ApiResponse(responseCode = "500", description = "Error in server",content = { @Content(mediaType = "application/json", 
          	      schema = @Schema(implementation = ResponseVO.class))})
    })
    @PostMapping("create")
    public Mono<ResponseVO> create(@RequestBody InputConversionVO data) {    
    	  try {		
  			return Mono.justOrEmpty(CoreUtils.responseSuccess(conversionService.createConversion(data)));		
  		}catch(Exception e) {
  			 return Mono.justOrEmpty(CoreUtils.responseException(e));			
  		}
  	}
    	

}
