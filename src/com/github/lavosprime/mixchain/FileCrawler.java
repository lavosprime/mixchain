package com.github.lavosprime.mixchain;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FileCrawler {

	public static final Set<String> EXTENSIONS;

	static {
		EXTENSIONS = new HashSet<String>();
		String[] temp = new String[] {
				"wav", "mp3", "m4a", "ogg", "flac"
		};
		for (String s : temp) EXTENSIONS.add(s);
	}

	private Scanner console;
	private Set<Song> songs;

	public FileCrawler(Scanner console) {
		this.console = console;
		songs = new HashSet<Song>();
	}

	public void crawl(File root) {
		crawl(root, false);
	}

	public void crawl(File root, boolean useAll) {
		crawl(root, useAll ? null : EXTENSIONS);
	}

	public void crawl(File root, Set<String> exts) {
		if (root.isFile()) {
			String[] parts = makeParts(root.getName());
			if (exts == null || exts.contains(parts[1]))
				add(root, parts[0]);
		}
	}

	public static String[] makeParts(String fileName) {
		String[] raw = fileName.split(".");
		String[] parts = new String[2];
		parts[0] = raw[0];
		parts[1] = raw[raw.length - 1].toLowerCase();
		for (int i = 1; i < raw.length - 1; i++)
			parts[0] += "." + raw[i];
		return parts;
	}

	private void add(File root, String title) {

	}

	public Set<Song> results() {
		return Collections.unmodifiableSet(songs);
	}

}