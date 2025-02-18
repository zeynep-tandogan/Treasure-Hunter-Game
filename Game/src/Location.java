import java.util.Random;
import java.util.Scanner;

public class Location {
    static int map_x = Main.map_x ; //pixel sayısı
    static int map_y = Main.map_y ;
    private static int[][] map = new int[map_x/10][map_y/10];
    private static int[][] fog_map = new int[map_x/10][map_y/10];
    private Random rand = new Random(); // Random nesnesini doğru şekilde başlattık.
    private int x, y; // Bu koordinatlar için başlangıç değerlerine henüz ihtiyaç yok.

    public Location() {
        for (int i = 0; i < fog_map.length; i++) {
            for (int j = 0; j < fog_map[i].length; j++) {
                fog_map[i][j] = 1;
            }
        }

        // Koordinatlar için rastgele değerler atayın, ancak bunu kullanıcıdan map_x ve map_y alındıktan sonra yapmalısınız.
        this.x = 10 * (rand.nextInt(map_x / 10) + 1); // 10 ile map_x/10 (dahil) arasında bir değer.
        this.y = 10 * (rand.nextInt(map_y / 10) + 1); // 10 ile map_y/10 (dahil) arasında bir değer.
    }
    public void Location_summer(){
        // Koordinatlar için rastgele değerler atayın, ancak bunu kullanıcıdan map_x ve map_y alındıktan sonra yapmalısınız.
        x = 10 * (rand.nextInt(map_x / 20) + 1) + 10; // 10 ile map_x/20 (dahil) arasında bir değer.
        y = 10 * (rand.nextInt(map_y / 10) + 1); // 10 ile map_y/10 (dahil) arasında bir değer.

    }
    public void Location_winter(){
        x = 10 * (rand.nextInt(map_x / 10) + map_x / 20) + 10; // map_x/20 ile map_x/10 (dahil) arasında bir değer.
        y = 10 * (rand.nextInt(map_y / 10) + 1); // 10 ile map_y/10 (dahil) arasında bir değer.
    }

    public void matrix_map(int startX, int startY, int size_x, int size_y, int obstacle_id) {
        // startX ve startY koordinatlarını birim karelerin başlangıç noktası olarak kullan
        // size_x ve size_y ile belirlenen alana obstacle_id değerini ata
        for (int i = 0; i < size_x/10; i++) {
            for (int j = 0; j < size_y/10; j++) {
                int newY = (startX / 10) + i;
                int newX = (startY / 10) + j;
                if (newX >= 0 && newX < map_x / 10 && newY >= 0 && newY < map_y / 10) {
                    map[newX][newY] = obstacle_id; // x ve y koordinatları düzeltildi
                }
            }
        }
    }

    public static void updateFogMap(int playerX, int playerY) {
        // Karakterin etrafındaki 7x7 alanı aç
        int startX = Math.max(playerX / 10 - 3, 0);
        int startY = Math.max(playerY / 10 - 3, 0);
        int endX = Math.min(playerX / 10 + 3, fog_map[0].length - 1);
        int endY = Math.min(playerY / 10 + 3, fog_map.length - 1);

        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                fog_map[i][j] = 0; // Görüş alanını temsil eden değer
            }
        }
    }


    public static void first_fog_map() {
        int startX = Math.max(GamePage.player.getCharacterX() / 10 - 3, 0);
        int startY = Math.max(GamePage.player.getCharacterY() / 10 - 3, 0);
        int endX = Math.min(GamePage.player.getCharacterX() / 10 + 3, fog_map[0].length - 1);
        int endY = Math.min(GamePage.player.getCharacterY() / 10 + 3, fog_map.length - 1);

        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                fog_map[i][j] = -2; // Görüş alanını temsil eden değer
            }
        }
    }

    // fog_map getter
    public static int[][] getFogMap() {
        return fog_map;
    }

    public static void printMap() {
        for (int i = 0; i < map.length; i++) { // map.length, matrisin satır sayısını verir
            for (int j = 0; j < map[i].length; j++) { // map[i].length, i. satırdaki sütun sayısını verir
                System.out.print(map[i][j] + " "); // Her bir hücreyi yazdır ve bir boşluk bırak
            }
            System.out.println(); // Her satırdan sonra bir yeni satıra geçmek için
        }
    }

    public boolean isLocationOccupied(int startX, int startY, int size_x, int size_y) {
        if(startX+size_x> map_x){return true;}
        if(startX-size_x< 0){return true;}
        if(startY+size_y>map_y){return true;}
        if(startY-size_y<0){return true;}

        for (int i = startX / 10; i < (startX / 10) + size_x / 10; i++) {
            for (int j = startY / 10; j < (startY / 10) + size_y / 10; j++) {
                if (i >= 0 && i < map.length && j >= 0 && j < map[0].length && map[j][i] != 0) {
                    return true; // Çakışma tespit edildi!
                }
            }
        }
        return false; // Çakışma tespit edilmedi.
    }



    // Getter ve Setter metodları
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static int[][] getMap() {
        return map;
    }

    public static void setMap(int[][] map) {
        Location.map = map;
    }
}
