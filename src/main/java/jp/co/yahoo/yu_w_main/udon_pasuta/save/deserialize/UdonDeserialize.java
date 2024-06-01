/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.save.deserialize;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.Facing;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;

import java.io.IOException;

public class UdonDeserialize extends StdDeserializer<Udon> {

	public UdonDeserialize(Class<?> vc) {
		super(vc);
	}

	public UdonDeserialize() {
		this(null);
	}

	@Override
	public Udon deserialize(JsonParser parser, DeserializationContext context) throws IOException, JacksonException {
		JsonNode root = context.readTree(parser);
		int xp = root.get("xp").intValue();
		int level = root.get("level").intValue();
		int x = root.get("x").intValue();
		int y = root.get("y").intValue();
		int hp = root.get("hp").intValue();
		Facing facing = Facing.valueOf(root.get("facing").textValue());
		String name = null;
		try {
			/*name = new URLCodec("UTF-8").decode(root.get("name").textValue(), "UTF-8");*/
			name = root.get("name").textValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Udon udon = new Udon(name);
		udon.setY(y);
		udon.setX(x);
		udon.setXp(xp);
		udon.setLevel(level);
		udon.setHp(hp);
		udon.facing = facing;
		return udon;
	}
}
