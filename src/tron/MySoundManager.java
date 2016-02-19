/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tron;

import audio.Playlist;
import audio.SoundManager;
import audio.Source;
import audio.Track;
import java.util.ArrayList;

/**
 *
 * @author Nick
 */
public class MySoundManager extends SoundManager {
    
    public static MySoundManager getSoundManager (){
        ArrayList<Track> tracks = new ArrayList<>();
        tracks.add(new Track(MySoundManager.SMG_NOISE, Source.RESOURCE, "/tron/apollo.wav" ));
        //tracks.add(new Track(MySoundManager.SMG_NOISE, Source.RESOURCE, "/tron/apollo.wav" ));
        
        Playlist playlist = new Playlist(tracks);
        return new MySoundManager(playlist);
    } 

    public MySoundManager(Playlist playlist) {
        super(playlist);
    }
    public static final String FART_NOISE = "Fart";
    public static final String SMG_NOISE = "SMG";
    public static final String HELL_o_SONG = "HELLO";
            
}
