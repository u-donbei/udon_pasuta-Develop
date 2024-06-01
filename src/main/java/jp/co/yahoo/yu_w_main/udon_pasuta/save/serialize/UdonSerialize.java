/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.save.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jp.co.yahoo.yu_w_main.udon_pasuta.misc.TextUtilities;
import jp.co.yahoo.yu_w_main.udon_pasuta.objects.character.Udon;
import org.apache.commons.codec.net.URLCodec;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UdonSerialize extends StdSerializer<Udon>{
	public UdonSerialize(Class<Udon> t) {
		super(t);
	}

	public UdonSerialize() {
		this(null);
	}

	@Override
	public void serialize(Udon udon, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
		gen.writeStartObject();
		System.out.println(udon.getName());
		gen.writeStringField("name" , udon.getName());
		gen.writeNumberField("xp", udon.getXP());
		gen.writeNumberField("level", udon.getLevel());
		gen.writeNumberField("tenkasu", udon.getTenkasu());
		gen.writeNumberField("hp", udon.getHp());
		gen.writeStringField("facing", udon.facing.toString());
		gen.writeNumberField("x", udon.getX());
		gen.writeNumberField("y", udon.getY());
		gen.writeEndObject();
	}


}
