package yuown.yuventory.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import yuown.yuventory.model.UserModel;

@Component
public class YuownTokenAuthenticationService {

	@Value("${auth.header.name}")
	private String AUTH_HEADER_NAME;

	@Value("${valid.days.for.auth.token}")
	private int validDaysForAuthToken;

	private static final long ONE_DAY = 1000 * 60 * 60 * 24;

	private final YuownTokenHandler yuownTokenHandler;

	@Autowired
	public YuownTokenAuthenticationService(@Value("${token.secret}") String secret) {
		yuownTokenHandler = new YuownTokenHandler(DatatypeConverter.parseBase64Binary(secret));
	}

	public void addAuthentication(HttpServletResponse response, UserModel user) {
		user.setExpires(System.currentTimeMillis() + (ONE_DAY * validDaysForAuthToken));
		String encryptedToken = yuownTokenHandler.createTokenForUser(user);
		if (StringUtils.isNotBlank(encryptedToken)) {
			response.addHeader(AUTH_HEADER_NAME, encryptedToken);
			response.addHeader("USER_FULLNAME", user.getFullName());
			response.addHeader("USER_ROLES", user.getAuthorities().toString());
		}
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final UserModel user = yuownTokenHandler.parseUserFromToken(token);
			if (user != null) {
				User authenticateuser = new User(user.getUsername(), user.getPassword(), user.getAuthorities());
				return new YuownAuthentication(authenticateuser);
			}
		}
		return null;
	}

	public int getUser(HttpServletRequest httpRequest) {
		final String token = httpRequest.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final UserModel user = yuownTokenHandler.parseUserFromToken(token);
			if (user != null) {
				return user.getId();
			}
		}
		return -1;
	}
}
