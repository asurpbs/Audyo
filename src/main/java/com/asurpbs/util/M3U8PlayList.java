/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.asurpbs.util;

import com.asurpbs.audyo.Audyo;
import com.asurpbs.user.PlayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 *
 * @author Mithila Prabashwara
 */
public class M3U8PlayList {
    public String playlist;
    
    public M3U8PlayList(String playlistPath) {
        this.playlist = playlistPath;
    }
    
    public boolean isEmpty() {
        try (Scanner reader = new Scanner(new File(this.playlist))) {
            if (reader.hasNextLine()) {
                String data = reader.nextLine().strip();
                return data.isEmpty() || data.charAt(0) == '#';
            }
            return true;
        } catch (FileNotFoundException ex) {
            PlayList.create(this.playlist.replace(Audyo.config.playList_dir + File.separator, "").replace("_"," ").replace(".m3u8", ""));
            return true;
        }
    }
    
    private void defineM3U8() {
        try {
            OutputStream outputStream = new FileOutputStream(this.playlist);
            byte[] bytes = "#EXTM3U\n".getBytes();
            outputStream.write(bytes);
            outputStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    private void write(String fileName) {
        try {
            OutputStream outputStream = new FileOutputStream(this.playlist, true);
            byte[] bytes = "#EXT-X-RATING:0\n".concat(Audyo.config.Song_dir + File.separator + fileName + "\n").getBytes();
            outputStream.write(bytes);
            outputStream.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void writer(String songFileName) {
        if (this.isEmpty()) this.defineM3U8();
        this.write(songFileName);
    }
    
    /**
     * 
     * @param filePath - The playlist name that you whish to create
     */
    public static void create(String filePath)  {
        try {
            if (Files.exists(Paths.get(filePath))) {
                System.out.println("The PlayList is already created.");
            } else {
                new File(filePath).createNewFile();
                System.out.println("The PlayList is created successfully.");
            }
        } catch (IOException ex) {
                System.out.println(ex.getMessage());
        }
    }

}
