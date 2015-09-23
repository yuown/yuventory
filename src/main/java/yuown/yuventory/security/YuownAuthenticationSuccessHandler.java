package yuown.yuventory.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import yuown.yuventory.business.services.UserService;
import yuown.yuventory.model.UserModel;

public class YuownAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private YuownTokenAuthenticationService yuownTokenAuthenticationService;

	@Autowired
	private UserService userService;

	private Logger log = LoggerFactory.getLogger(YuownAuthenticationSuccessHandler.class);

	@Autowired
	public YuownAuthenticationSuccessHandler(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {

	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		final User authenticatedUser = (User) authResult.getPrincipal();

		UserModel fromDB = userService.getByUsername(authenticatedUser.getUsername());

		log.debug("User: {} - Auths: {}", fromDB.getUsername(), fromDB.getAuthorities());

		final Authentication userAuthentication = new YuownAuthentication(authenticatedUser);
		yuownTokenAuthenticationService.addAuthentication(response, fromDB);
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);
	}
}