package com.github.lavosprime.mixchain;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

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
        } else for (File file : root.listFiles()) crawl(file, exts);
    }

    public static String[] makeParts(String fileName) {
        String[] raw = fileName.split("[.]");
        if (raw.length == 0)
            return new String[] {"", ""};
        else {
            String[] parts = new String[2];
            parts[0] = raw[0];
            parts[1] = raw[raw.length - 1].toLowerCase();
            for (int i = 1; i < raw.length - 1; i++)
                parts[0] += "." + raw[i];
            return parts;
        }
    }

    private void add(File file, String title) {
        System.out.println(file.getAbsolutePath());
        System.out.println("    Title detected: " + title);
        System.out.print("    Use different title? (return if none) ");
        String temp = console.nextLine().trim();
        if (!temp.isEmpty()) title = temp;
        do {
            System.out.print("    Artist? ");
            temp = console.nextLine().trim();
        } while (temp.isEmpty());
        String artist = temp;
        int length = detectLength(file);
        if (length < 0) {
            System.out.println("    Length could not be detected.");
            do {
                System.out.print("    Length? (seconds) ");
                try {
                    length = Integer.parseInt(console.nextLine().trim());
                } catch (NumberFormatException e) { }
                if (length < 0)
                    System.out.println("    Positive integer needed.");
            } while (length < 0);
        } else {
            System.out.println("    Length detected: " + length + " seconds");
            System.out.print("    Use different length? (return if none) ");
            int temp2 = length;
            try {
                temp2 = Integer.parseInt(console.nextLine().trim());
            } catch (NumberFormatException e) { }
            if (temp2 > 0) length = temp2;
        }
        System.out.println();
        Song song = new Song(title, artist, length);
        songs.add(song);
        System.out.println(song.toString() + " added.");
        System.out.println();
    }

    private int detectLength(File file) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = stream.getFormat();
            long frames = stream.getFrameLength();
            return (int) ((frames + 0.0) / format.getFrameRate());
        } catch (Exception e) {
            return -1;
        }
    }

    public Set<Song> results() {
        return Collections.unmodifiableSet(songs);
    }

}