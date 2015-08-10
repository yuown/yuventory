package yuown.yuventory.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import yuown.yuventory.business.services.ConfigurationService;
import yuown.yuventory.business.services.UserService;
import yuown.yuventory.model.ConfigurationModel;
import yuown.yuventory.model.UserModel;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class YuownAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private ObjectMapper objectMapper;

	@Autowired
	private YuownTokenAuthenticationService yuownTokenAuthenticationService;

	@Autowired
	private UserService userService;

	@Autowired
	private ConfigurationService configurationService;

	private static final String BARCODE_WIDTH = "barcode_width";

	private static final String BARCODE_HEIGHT = "barcode_height";

	@Autowired
	public YuownAuthenticationSuccessHandler(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
		this.objectMapper = mappingJackson2HttpMessageConverter.getObjectMapper();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_OK);
		final UserModel authenticatedUser = (UserModel) authResult.getPrincipal();
		final Authentication userAuthentication = new YuownAuthentication(authenticatedUser);
		yuownTokenAuthenticationService.addAuthentication(response, authenticatedUser);
		SecurityContextHolder.getContext().setAuthentication(userAuthentication);

		setPostLoginData();
	}

	private void setPostLoginData() {
		ConfigurationModel width = configurationService.getByName(BARCODE_WIDTH);
		ConfigurationModel height = configurationService.getByName(BARCODE_HEIGHT);
		int barcodeWidth = 0;
		int barcodeHeight = 0;
		if (width != null) {
			barcodeWidth = width.getValue();
		}
		if (height != null) {
			barcodeHeight = height.getValue();
		}
		System.setProperty(BARCODE_WIDTH, Integer.toString(barcodeWidth));
		System.setProperty(BARCODE_HEIGHT, Integer.toString(barcodeHeight));
	}
}