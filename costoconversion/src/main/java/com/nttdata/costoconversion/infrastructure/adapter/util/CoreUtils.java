package com.nttdata.costoconversion.infrastructure.adapter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.springframework.http.HttpStatus;

import com.nttdata.costoconversion.application.output.ResponseVO;

public class CoreUtils {

	public static final String INTERNAL_ERROR = "Internal Error";	

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String fomatDate(Date date) {
		String pattern = "yyyy-MM-dd:hh:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	

	public static ResponseVO responseSuccess(Object data) {
		ResponseVO response = new ResponseVO();
		// response.setHttpStatus(HttpStatus.OK);
		response.setStatus(HttpStatus.OK.value());
		response.setData(data);
		return response;		
	}


	/**
	 * Send response with error
	 * 
	 * @param responseVO
	 * @return
	 */
	public static ResponseVO responseException(Exception ex) {

		return new ResponseVO(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				INTERNAL_ERROR, ex);
	}

	/**
	 * Get random number
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static String getRandomNumberUsingInts(int min, int max) {
		Random random = new Random();
		int value = random.ints(min, max).findFirst().getAsInt();
		return Integer.toString(value);
	}

	/**
	 * Generate Conversion ID
	 * @return
	 */
	public static String generateId() {
		StringBuffer id = new StringBuffer();
		id.append(getRandomNumberUsingInts(100, 999)).append("-").append(getRandomNumberUsingInts(100, 999)).append("-")
				.append(getRandomNumberUsingInts(100, 999)).append("-").append(getRandomNumberUsingInts(1000, 9999));
		return id.toString();
	}
	
	public static Date convertDateMinValue(String date) throws ParseException {
		StringBuffer dateJoin = new StringBuffer();
		dateJoin.append(date).append(" 00:00:00");		
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateJoin.toString());
	}
	
	public static Date convertDateMaxValue(String date) throws ParseException {
		StringBuffer dateJoin = new StringBuffer();
		dateJoin.append(date).append(" 23:59:59");		
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateJoin.toString());
	}

}
