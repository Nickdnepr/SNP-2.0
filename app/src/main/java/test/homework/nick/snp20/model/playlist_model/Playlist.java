package test.homework.nick.snp20.model.playlist_model;

/**
 * Created by Nick on 05.12.16.
 */
public class Playlist {
    private String title;
    private String artwork;

    public Playlist(String title, String artwork) {
        this.title = title;
        this.artwork = artwork;
    }

    public String getTitle() {
        return title;
    }

    public String getArtwork() {
        return artwork;
    }
}
