/*
 * Copyright (c) 2023 ウードン (yu_w_main@yahoo.co.jp)
 */
package jp.co.yahoo.yu_w_main.udon_pasuta.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.co.yahoo.yu_w_main.udon_pasuta.constants.PathConsts;
import jp.co.yahoo.yu_w_main.udon_pasuta.logging.Logging;
import jp.co.yahoo.yu_w_main.udon_pasuta.variables.Variable;
import jp.co.yahoo.yu_w_main.yulib.classpath.PathClassLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class PluginLoader {
	private static Path[] plugins;

	static {
		Path p = Paths.get(PathConsts.PLUGIN_DIR.toString());

		File[] fs = Objects.requireNonNull(p.toFile().listFiles());
		plugins = new Path[fs.length];

		for (int i = 0; i < fs.length; i++) {
			plugins[i] = fs[i].toPath();
		}
	}

	@SuppressWarnings("unchecked")
	public static PluginExecutor getExecutors() throws Exception {
		//プラグインの名前とnameが一致するかを調べ、一致するならそのExecutorを返す。
		for (Path plugin : plugins) {
			//プラグインのjarファイルを一時ファイルに解凍しする
			Path tmp = Files.createTempDirectory("udon-pasuta-plugin-loader");

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(plugin.toFile()));

			try (ZipInputStream zis = new ZipInputStream(bis)) {
				ZipEntry e;

				while ((e = zis.getNextEntry()) != null) {
					File uncFile = new File(tmp + "\\" + e.getName());

					if (e.isDirectory()) {
						uncFile.mkdirs();
					} else {
						int len = 0;

						byte[] data = new byte[1024];
						try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(uncFile))) {
							while ((len = zis.read(data)) != -1) {
								bos.write(data, 0, len);
							}
						}
					}
				}
			}

			//nameとプラグインの名前が一致するかを調べる
			File[] inTmp = Objects.requireNonNull(tmp.toFile().listFiles());

			for (File m : inTmp) {
				if (m.isDirectory() && m.getName().equals("META-INF")) {
					File[] inM = m.listFiles();

					for (File info : Objects.requireNonNull(inM)) {
						if (info.isFile() && info.getName().equals("info.json")) {


							ObjectMapper mapper = new ObjectMapper();
							InfoData data = mapper.readValue(info, InfoData.class);

							System.out.println(data);
							try {
								PathClassLoader loader = new PathClassLoader(plugin);
								Class<? extends PluginExecutor> clazz = (Class<? extends PluginExecutor>) Class.forName(data.mainClass, true, loader);

								PluginExecutor executor = clazz.getConstructor(PluginExecutor.PluginRunPos.class).newInstance(PluginExecutor.PluginRunPos.valueOf(data.runPos));
								Variable.plugins.add(executor);
								return executor;
							} catch (Exception e) {
								Logging.LOGGER.error("プラグインの構成が破損しています", e);
							}
						}
					}
				}
			}
		}
		throw new Exception();
	}

	/**
	 * 指定したプラグインの{@link PluginExecutor}を返す。
	 *
	 * @param name プラグイン名
	 * @return そのプラグインのExecutor
	 * @see PluginExecutor
	 */
	@SuppressWarnings("unchecked")
	public static PluginExecutor getExecutor(String name) throws IOException {
		//プラグインの名前とnameが一致するかを調べ、一致するならそのExecutorを返す。
		for (Path plugin : plugins) {
			//プラグインのjarファイルを一時ファイルに解凍しする
			Path tmp = Files.createTempDirectory("udon-pasuta-plugin-loader");

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(plugin.toFile()));

			try (ZipInputStream zis = new ZipInputStream(bis)) {
				ZipEntry e;

				while ((e = zis.getNextEntry()) != null) {
					File uncFile = new File(tmp + "\\" + e.getName());

					if (e.isDirectory()) {
						uncFile.mkdirs();
					} else {
						int len = 0;

						byte[] data = new byte[1024];
						try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(uncFile))) {
							while ((len = zis.read(data)) != -1) {
								bos.write(data, 0, len);
							}
						}
					}
				}
			}

			//nameとプラグインの名前が一致するかを調べる
			File[] inTmp = Objects.requireNonNull(tmp.toFile().listFiles());

			for (File m : inTmp) {
				if (m.isDirectory() && m.getName().equals("META-INF")) {
					File[] inM = m.listFiles();

					for (File info : Objects.requireNonNull(inM)) {
						if (info.isFile() && info.getName().equals("info.json")) {


							ObjectMapper mapper = new ObjectMapper();
							InfoData data = mapper.readValue(info, InfoData.class);

							System.out.println(data);

							if (data.name.equals(name)) {
								try {
									PathClassLoader loader = new PathClassLoader(plugin);
									Class<? extends PluginExecutor> clazz = (Class<? extends PluginExecutor>) Class.forName(data.mainClass, true, loader);

									PluginExecutor executor = clazz.getConstructor(PluginExecutor.PluginRunPos.class).newInstance(PluginExecutor.PluginRunPos.valueOf(data.runPos));
									Variable.plugins.add(executor);
									return executor;
								} catch (Exception e) {
									Logging.LOGGER.error("プラグインの構成が破損しています", e);
								}
							}
						}
					}
				}
			}
		}

		throw new RuntimeException("Plugin " + name + " is NotFound");
	}

	public static class InfoData {
		public String name;
		public String description;
		public String version;
		public String author;
		public String moduleName;
		public String runPos;
		public String mainClass;

		@Override
		public String toString() {
			return "InfoData{" +
					"name='" + name + '\'' +
					", description='" + description + '\'' +
					", version='" + version + '\'' +
					", author='" + author + '\'' +
					", mainClass='" + mainClass + '\'' +
					", moduleName='" + moduleName + '\'' +
					'}';
		}
	}
}
