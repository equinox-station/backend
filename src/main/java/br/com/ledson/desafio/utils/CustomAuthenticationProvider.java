package br.com.ledson.desafio.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private List<GrantedAuthority> grantedAuths = new ArrayList<>();

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String user = authentication.getName();
		String password = authentication.getCredentials().toString();

		/*
		 *  Logica de autenticação
		 */
		if (user.equals("ledson") && password.equals("kappa123")) {
			grantedAuths.add(new SimpleGrantedAuthority("USER"));
			return new UsernamePasswordAuthenticationToken(user, password, grantedAuths);
		}

		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}