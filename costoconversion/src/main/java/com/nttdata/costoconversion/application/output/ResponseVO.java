package com.nttdata.costoconversion.application.output;


import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Christian
 * @param <T>
 *
 */
@Data
@AllArgsConstructor
public class ResponseVO {
	
	@JsonInclude(Include.NON_NULL)
    private HttpStatus httpStatus;
	
	@JsonInclude(Include.NON_NULL)
    private Integer status;
	
	@JsonInclude(Include.NON_NULL)
    private Integer internalStatus;
	
	@JsonInclude(Include.NON_NULL)
    private String message;
	
	@JsonInclude(Include.NON_NULL)
	private String debugMessage;	
	
	@JsonInclude(Include.NON_NULL)
	private Object data;
	
	public ResponseVO() {}

	public ResponseVO(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
    }

    public ResponseVO(HttpStatus httpStatus, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.message = "Unexpected error";
    }

    public ResponseVO(HttpStatus httpStatus, Integer status, String message, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

	public ResponseVO(Integer internalStatus, String message) {
		super();
		this.internalStatus = internalStatus;
		this.message = message;
	}
    
	
	public ResponseVO(Integer internalStatus, String message, Throwable ex) {
		super();
		this.internalStatus = internalStatus;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}


	public ResponseVO(Integer internalStatus, String message, Object object) {
		super();
		this.internalStatus = internalStatus;
		this.message = message;
		this.data = object;
	}
	 
    
}
