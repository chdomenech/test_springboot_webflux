package com.nttdata.costoconversion.application.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.costoconversion.application.input.report.InputReportVO;
import com.nttdata.costoconversion.application.output.ResponseVO;
import com.nttdata.costoconversion.domain.service.ReportService;
import com.nttdata.costoconversion.infrastructure.adapter.util.CoreUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Mono;

@Tag(name = "Report", description = "The report Api")
@RestController
@RequestMapping("/api/report")
public class ReportController {

	@Autowired
    private ReportService reportService;
	
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(
            summary = "Create a report",
            description = "Create a report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @PostMapping("generate")
    public Mono<ResponseVO> generate(@RequestBody InputReportVO data) {
    	  try {		
  			return Mono.justOrEmpty(CoreUtils.responseSuccess(reportService.generateReport(data)));		
  		}catch(Exception e) {
  			 return Mono.justOrEmpty(CoreUtils.responseException(e));			
  		}
    }
}

