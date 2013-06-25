package com.github.lavosprime.mixchain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Song implements Comparable<Song>, Iterable<Song> {

    private int length;
    private String title;
    private String artist;
    private Set<Song> transitions;

    public Song(String title, String artist, int length) {
        this.length = length;
        this.title = title;
        this.artist = artist;
        this.transitions = new HashSet<Song>();
    }

    public boolean hasTransition(Song transition) {
        return transitions.contains(transition);
    }

    public void addTransition(Song transition) {
        transitions.add(transition);
    }

    @Override
    public Iterator<Song> iterator() {
        return transitions.iterator();
    }

    @Override
    public int compareTo(Song other) {
        if (!title.equals(other.title))
            return title.compareTo(other.title);
        else if (!artist.equals(other.artist))
            return artist.compareTo(other.artist);
        else return length - other.length;
    }

    @Override
    public boolean equals(Object other) {
        return this == other || other != null && other instanceof Song
                && title == ((Song) other).title
                && artist == ((Song) other).artist
                && length == ((Song) other).length;
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "\"" + title + "\" by " + artist + " (" + length + " s)";
    }

}