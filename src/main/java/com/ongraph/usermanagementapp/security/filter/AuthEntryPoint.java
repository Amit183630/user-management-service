package com.ongraph.usermanagementapp.security.filter;

import java.io.IOException;

import javax.naming.AuthenticationException;

import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ongraph.usermanagementapp.model.DataResponse;
import com.ongraph.usermanagementapp.model.ErrorCodes;
import com.ongraph.usermanagementapp.model.ErrorDetails;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AuthEntryPoint implements AuthenticationEntryPoint {


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException {
		log.error("Unauthorized error: {}", authException);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		DataResponse dataResponse = new DataResponse(
				new ErrorDetails(ErrorCodes.E_AUTH401.name(), authException.getMessage()));
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), dataResponse);

	}
}
