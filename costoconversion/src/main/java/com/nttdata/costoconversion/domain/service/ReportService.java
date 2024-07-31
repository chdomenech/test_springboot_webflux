package com.nttdata.costoconversion.domain.service;

import java.util.List;

import com.nttdata.costoconversion.application.input.report.InputReportVO;
import com.nttdata.costoconversion.application.output.ReportOutputVO;

public interface ReportService {
	
	List<ReportOutputVO> generateReport(InputReportVO data) throws Exception;
}
