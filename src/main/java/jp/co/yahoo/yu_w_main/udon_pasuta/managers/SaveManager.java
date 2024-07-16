package jp.co.yahoo.yu_w_main.udon_pasuta.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.yahoo.yu_w_main.udon_pasuta.annotations.Manager;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Manager
public final class SaveManager {
	private SaveManager() {}
	public static void save(Udon t) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try (Writer w = Files.newBufferedWriter(Paths.get(PathConsts.SAVE_DIR + "1\\udon.json"));
			OutputStream os = new BufferedOutputStream(new FileOutputStream(PathConsts.SAVE_DIR + "1\\udon"))) {
			String json = mapper.writeValueAsString(t);
			os.write(hash(json));
			w.write(json);
			w.flush();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static Udon readUdon() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Udon res = mapper.readValue(Paths.get(PathConsts.SAVE_DIR + "1\\udon.json").toFile(), Udon.class);
		return res;
	}

	private static byte[] hash(String plain) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(plain.getBytes(StandardCharsets.UTF_8));
	}

	private static boolean verify(String plain, byte[] hash) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] nHash = md.digest(plain.getBytes(StandardCharsets.UTF_8));

		return Arrays.equals(hash, nHash);
	}

}
