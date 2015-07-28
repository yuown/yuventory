package yuown.yuventory.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import yuown.yuventory.model.UserModel;

import com.fasterxml.jackson.databind.ObjectMapper;

public class YuownLoginFilter extends AbstractAuthenticationProcessingFilter {

	@Autowired
	private AuthenticationManager yuownAuthenticationManagerForRest;

	protected YuownLoginFilter(String defaultFilterProcessesUrl) {
		super(new AntPathRequestMatcher(defaultFilterProcessesUrl));
	}

	@PostConstruct
	public void init() {
		setAuthenticationManager(yuownAuthenticationManagerForRest);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		final UserModel user = new ObjectMapper().readValue(request.getInputStream(), UserModel.class);
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		return getAuthenticationManager().authenticate(loginToken);
	}

	@Override
	public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
		super.setAuthenticationSuccessHandler(authenticationSuccessHandler);
	}
}
