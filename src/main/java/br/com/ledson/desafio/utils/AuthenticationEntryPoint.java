package br.com.ledson.desafio.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx) throws IOException, ServletException {
		response.addHeader("WWW-Authenticate", "Basic realm=" +getRealmName());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.println(authEx.getMessage().replaceAll("\n", "").replaceAll("\r", ""));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("Desafio-Login");
		super.afterPropertiesSet();
	}

}