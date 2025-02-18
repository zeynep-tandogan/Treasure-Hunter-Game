import java.awt.*;

public class Character {
    private int ID;
    private String Ad;
    Location location;
    private int character_height = 10,character_width = 10;

    public Character(int ID, String Ad) {
        this.ID = ID;
        this.Ad = Ad;
        do {
            this.location = new Location();
        } while (location.isLocationOccupied(location.getX(), location.getY(), character_width, character_height));
        location.matrix_map(location.getX(), location.getY(), character_height ,character_width , -1);
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public int getCharacterX() {
        return location.getX();
    }

    public int getCharacterY() {
        return location.getY();
    }

    public void setLocation(int x, int y) {
        if (this.location != null) {
            this.location.setX(x);
            this.location.setY(y);
        }
    }

    public int getCharacter_height() {
        return character_height;
    }

    public void setCharacter_height(int character_height) {
        this.character_height = character_height;
    }

    public int getCharacter_width() {
        return character_width;
    }

    public void setCharacter_width(int character_width) {
        this.character_width = character_width;
    }
}
