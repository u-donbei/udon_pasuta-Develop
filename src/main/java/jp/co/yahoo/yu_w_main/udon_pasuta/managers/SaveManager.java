package jp.co.yahoo.yu_w_main.udon_pasuta.managers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.yahoo.yu_w_main.udon_pasuta.annotations.Manager;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Manager
public final class SaveManager {
	private SaveManager() {}
	public static void save(Udon t) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		try (Writer w = Files.newBufferedWriter(Paths.get(PathConsts.SAVE_DIR + "1\\udon.json"))) {
			String json = mapper.writeValueAsString(t);
			w.write(json);
			w.flush();
		}
	}

	public static Udon readUdon() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		Udon res = mapper.readValue(Paths.get(PathConsts.SAVE_DIR + "1\\udon.json").toFile(), Udon.class);
		return res;
	}

}
