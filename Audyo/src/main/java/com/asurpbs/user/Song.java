/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asurpbs.user;

/**
 *
 * @author Mithila Prabashwara
 */

import com.asurpbs.util.DoublyLinkedList;

public class Song {

    public String title;;
    public String artist;
    public String duration;
    public String fileName;
    public static int count = 0; //The count of the all files in the folder
    public static String[] supportedFormats = {"mp3", "flac", "opus", "wav", "aac"};
    /**
     *To store all songs
     */
    public static DoublyLinkedList allList = new DoublyLinkedList();
    
    /**
     * 
     * @param title - song's title in title case (title case is not necessary)
     * @param artist - artist's name in title case (title case is not necessary)
     * @param timeSpan - the song's duration as seconds
     * @param fileName - file's name
     */
    public Song(String title, String artist, int timeSpan, String fileName) {
        this.title = title.strip();
        this.artist = artist.strip();
        this.duration = formatDuration(timeSpan);
        this.fileName = fileName.strip();
        count++;
    }
    
    /**
     * get song's metadata as a array
     * @return Song;s title, artist and song's duration as String
     */
    public String[] getMetaData() {
        return new String[] {this.title, this.artist, this.duration};
    }
    
    /**
     * format duration as mm:ss ~ ISO 8601 format
     * @param seconds - Song's duration as seconds
     * @return The duration in mm : ss for ma as String
     */
    private String formatDuration(int seconds) {
        return String.format("%02d:%02d",seconds / 60, seconds % 60);
    }
    

    /**
     * Get back the reference of needed song instance from the Song.allList
     * @param name - song name without extension
     * @return the reference to the song instance in the heap
     */
    public static Song getByName(String name) {
        Object[] objLists = Song.allList.getObjectArray();
        for (Object obj : objLists) {
            if (obj instanceof Song temp) {
                if (temp.title.toLowerCase().equals(name.trim().toLowerCase())) {
                    return temp;
                }
            }
        }
        return null;
    }
    
    /**
     * Use to view all songs with title, artist, and duration.
     */
    public static void viewAllSongs() {
        if (allList.isEmpty()) {
            System.out.println(":( No songs found.");
        } else {
            Object[] objLists = allList.getObjectArray();
            int i = 1;

            System.out.println("===========================================");
            System.out.println("                 [ALL SONGS]                ");
            System.out.println("===========================================");
            System.out.printf(" %-4s %-20s %-20s %-10s\n", "No.", "Title", "Artist", "Duration");
            System.out.println("===========================================");

            for (Object obj : objLists) {
                if (obj instanceof Song song) {
                    System.out.printf(" %-4d %-20s %-20s %-10s\n", i++, song.title, song.artist, song.duration);
                } else {
                    System.out.println(" :( Unexpected data found.");
                }
            }

            System.out.println("===========================================");
        }
    }
    
}
