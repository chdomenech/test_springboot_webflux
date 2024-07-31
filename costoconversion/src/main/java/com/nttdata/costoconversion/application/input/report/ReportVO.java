package com.nttdata.costoconversion.application.input.report;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportVO {

	@NotNull
	private String date;
	@NotNull
	private String model;
	@NotNull
	private String cryptocurrency;
}
