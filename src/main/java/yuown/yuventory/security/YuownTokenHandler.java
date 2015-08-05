package yuown.yuventory.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import yuown.yuventory.model.UserModel;

public class YuownTokenHandler {

	private static final String ALGORITHM = "AES";

	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

	private Key secretKey;

	public YuownTokenHandler(byte[] secret) {
		secretKey = new SecretKeySpec(secret, ALGORITHM);
	}

	public String createTokenForUser(UserModel user) {
		String encryptedToken = null;
		try {
			byte[] userBytes = toJSON(user);
			byte[] hash = doCrypto(Cipher.ENCRYPT_MODE, userBytes);
			encryptedToken = new String(toBase64(hash));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return encryptedToken;
	}

	public UserModel parseUserFromToken(String token) {
		try {
			byte[] input = fromBase64(token);
			byte[] jsonBytes = doCrypto(Cipher.DECRYPT_MODE, input);
			final UserModel user = fromJSON(jsonBytes);
			if (new Date().getTime() < user.getExpires()) {
				return user;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] doCrypto(int mode, byte[] input) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(mode, secretKey);
		return cipher.doFinal(input);
	}

	private UserModel fromJSON(final byte[] userBytes) {
		try {
			return new ObjectMapper().readValue(new ByteArrayInputStream(userBytes), UserModel.class);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private byte[] toJSON(UserModel user) {
		try {
			return new ObjectMapper().writeValueAsBytes(user);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}

	private byte[] fromBase64(String content) {
		return DatatypeConverter.parseBase64Binary(content);
	}

	private String toBase64(byte[] content) {
		return DatatypeConverter.printBase64Binary(content);
	}
}
