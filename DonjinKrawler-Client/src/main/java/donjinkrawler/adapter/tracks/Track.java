package donjinkrawler.adapter.tracks;

public class Track {

    private final String type;

    private final String file;

    public Track(String type, String file) {
        this.type = type;
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public String getFile() {
        return file;
    }
}
