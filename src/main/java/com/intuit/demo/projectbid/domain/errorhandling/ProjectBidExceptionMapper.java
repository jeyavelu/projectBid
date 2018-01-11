package com.intuit.demo.projectbid.domain.errorhandling;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.util.StringUtils;

import com.intuit.demo.projectbid.application.AppConstants;

@Provider
public class ProjectBidExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage();		
		
		errorMessage.setCode(AppConstants.GENERIC_APP_ERROR_CODE);
		errorMessage.setLink(AppConstants.API_URL);
		errorMessage.setMessage(ex.getMessage());

		setHttpStatus(ex, errorMessage);
						
		StringWriter errorStackTrace = new StringWriter();
		ex.printStackTrace(new PrintWriter(errorStackTrace));
		
		return Response.status(errorMessage.getStatus())
				.entity(errorMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

	private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {
		if(ex instanceof WebApplicationException ) {
			errorMessage.setStatus(((WebApplicationException)ex).getResponse().getStatus());
		} else if(ex instanceof AppException) {
			errorMessage.setStatus(Response.Status.OK.getStatusCode());
			if(null != ((AppException) ex).getLink())
				errorMessage.setLink(((AppException) ex).getLink());
			if(0 != ((AppException) ex).getCode())
				errorMessage.setCode(((AppException) ex).getCode());
		} else {
			errorMessage.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); //defaults to internal server error 500
		}
	}

}