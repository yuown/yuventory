package yuown.yuventory.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class YuownAuthenticationFilter extends GenericFilterBean {

	@Autowired
	private YuownTokenAuthenticationService yuownTokenAuthenticationService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		SecurityContextHolder.getContext().setAuthentication(yuownTokenAuthenticationService.getAuthentication((HttpServletRequest) request));
		chain.doFilter(request, response);
	}
}
