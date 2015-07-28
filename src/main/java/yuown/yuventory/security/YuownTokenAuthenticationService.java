package yuown.yuventory.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import yuown.yuventory.model.UserModel;

@Component
public class YuownTokenAuthenticationService {

	@Value("${X-AUTH-TOKEN}")
	private String AUTH_HEADER_NAME;

	private static final long TEN_DAYS = 1000 * 60 * 60 * 24 * 10;

	private final YuownTokenHandler yuownTokenHandler;

	@Autowired
	public YuownTokenAuthenticationService(@Value("${token.secret}") String secret) {
		yuownTokenHandler = new YuownTokenHandler(DatatypeConverter.parseBase64Binary(secret));
	}

	public void addAuthentication(HttpServletResponse response, Authentication authentication) {
		final UserModel user = (UserModel) authentication.getDetails();
		user.setExpires(System.currentTimeMillis() + TEN_DAYS);
		response.addHeader(AUTH_HEADER_NAME, yuownTokenHandler.createTokenForUser(user));
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);
		if (token != null) {
			final UserModel user = yuownTokenHandler.parseUserFromToken(token);
			if (user != null) {
				return new YuownAuthentication(user);
			}
		}
		return null;
	}
}
