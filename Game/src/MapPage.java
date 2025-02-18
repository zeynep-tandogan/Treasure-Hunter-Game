import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MapPage extends JPanel {
        private int[][] map; // Harita matrisi
        private Image
                playerImage,
                summerBackgroundImage,winterBackgroundImage,
                obstacleSummerTreeImage,obstacleWinterTreeImage,
                obstacleSummerMountainImage,obstacleWinterMountainImage,
                obstacleRockImage,obstacleWallImage,
                obstacleBirdImage,obstacleBeeImage,
                rewardSilverImage,rewardBronzeImage,rewardGoldImage,rewardEmeraldImage;

    ArrayList<DynamicObstacles.Bird> birds = Main.getBird_obstacles();
    ArrayList<DynamicObstacles.Bee> bees = Main.getBee_obstacles();

    public MapPage() {
            map = Location.getMap(); // Harita matrisini al (Location sınıfınızın bu metodla uyumlu olduğunu varsayıyorum)
            loadImages();
            setPreferredSize(new Dimension(1920, 1080)); // Panelin boyutunu ayarla
        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (DynamicObstacles.Bird bird : birds){
                    bird.update();
                }
                for (DynamicObstacles.Bee bee : bees) {
                    bee.update();
                }
                repaint(); // Repaint the component to update the screen

            }
        });
        timer.start();
        }

    private void loadImages() {
        playerImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/character.png").getImage();
        //fogImage =  new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/fog.png").getImage();
        obstacleSummerTreeImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/summerTree.png").getImage();
        obstacleSummerMountainImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/summerMountain.png").getImage();
        obstacleWinterTreeImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/winterTree.png").getImage();
        obstacleWinterMountainImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/winterMountain.png").getImage();
        obstacleRockImage =  new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/rock.png").getImage();
        obstacleWallImage =  new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/wall.png").getImage();
        obstacleBirdImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/raccoon.gif").getImage();
        obstacleBeeImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/fox.gif").getImage();
        summerBackgroundImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/grass.png").getImage();
        winterBackgroundImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/winter.png").getImage();
        rewardGoldImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/gold.gif").getImage();
        rewardSilverImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/silver.gif").getImage();
        rewardEmeraldImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/zumrut.gif").getImage();
        rewardBronzeImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/bronz.gif").getImage();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // gridSize değerini haritanın boyutuna ve panelin boyutuna göre ayarlayın
        super.paintComponent(g);

        // Panelin boyutlarına göre her bir hücrenin boyutunu hesapla
        int gridSize = Math.min(1920 / map[0].length, 1080 / map.length);

        boolean[][] visited = new boolean[map.length][map[0].length]; // Ziyaret edilen hücreleri takip etmek için

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (j <= map[0].length / 2) {
                    g.drawImage(summerBackgroundImage, j * gridSize, i * gridSize, gridSize, gridSize, this);
                } else if (j > map[0].length / 2){
                    g.drawImage(winterBackgroundImage, j * gridSize, i * gridSize, gridSize, gridSize, this);
                }
            }
        }
        g.setColor(Color.GRAY); // Izgara çizgileri için renk
        for (int i = 0; i <= 1080; i += gridSize) {
            g.drawLine(0, i, 1080, i);
        }
        for (int j = 0; j <= 1080; j += gridSize) {
            g.drawLine(j, 0, j, 1080);
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int obstacle_id = map[i][j];
                if (obstacle_id != 0 && !visited[i][j]) {
                    // Engelin başlangıç noktasını bul
                    int startX = j;
                    int startY = i;

                    // Engelin bitiş noktasını bul (sağa ve aşağıya doğru)
                    int endX = startX;
                    int endY = startY;
                    while (endX < map[i].length && map[i][endX] == obstacle_id) {
                        endX++;
                    }
                    while (endY < map.length && map[endY][j] == obstacle_id) {
                        endY++;
                    }

                    // Engelin boyutunu hesapla
                    int width = (endX - startX) * gridSize;
                    int height = (endY - startY) * gridSize;

                    // Engeli çiz
                    switch (obstacle_id) {
                        case 1:
                            g.drawImage(obstacleSummerTreeImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 2: // Winter_Tree
                            g.drawImage(obstacleWinterTreeImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 3: // Winter_Tree
                            g.drawImage(obstacleRockImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 4: // Winter_Tree
                            g.drawImage(obstacleWallImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 5: // Summer_Mountain
                            g.drawImage(obstacleSummerMountainImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 6: // Winter_Mountain
                            g.drawImage(obstacleWinterMountainImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 7: // Winter_Mountain
                            g.setColor(Color.RED); // Kırmızı rengi ayarlıyoruz.
                            g.fillRect(startX * gridSize+20, startY * gridSize, width, height); // İlgili hücreyi kırmızı ile dolduruyoruz.
                            break;
                        case 8: // Winter_Mountain
                            //g.setColor(Color.RED); // Kırmızı rengi ayarlıyoruz.
                            g.fillRect(startX * gridSize, startY * gridSize+20, width, height); // İlgili hücreyi kırmızı ile dolduruyoruz.
                            break;
                        case 10: // Winter_Mountain
                            g.drawImage(obstacleBirdImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 11: // Winter_Mountain
                            g.drawImage(rewardGoldImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 12: // Winter_Mountain
                            g.drawImage(rewardBronzeImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 13: // Winter_Mountain
                            g.drawImage(rewardSilverImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 14: // Winter_Mountain
                            g.drawImage(rewardEmeraldImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        case 20: // Winter_Mountain
                            g.drawImage(obstacleBirdImage, startX * gridSize , startY * gridSize , width, height, this);
                            break;
                        // Diğer engel türleri için benzer case blokları eklenebilir
                    }

                    for (DynamicObstacles.Bird bird : birds){
                        g.drawImage(obstacleBirdImage, bird.location.getX(), bird.location.getY(), bird.getGetBird_width(), bird.getBird_height(), this);
                    }
                    for (DynamicObstacles.Bee bee : bees) {
                        g.drawImage(obstacleBeeImage, bee.location.getX(), bee.location.getY(), bee.getGetBee_width(), bee.getBee_height(), this);
                    }

                    // Ziyaret edilen hücreleri işaretle
                    for (int y = startY; y < endY; y++) {
                        for (int x = startX; x < endX; x++) {
                            visited[y][x] = true;
                        }
                    }


                    // İşlenen alanı atla
                    j = endX - 1;
                }
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Map Page");
            MapPage mapPage = new MapPage();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(mapPage);
            frame.pack(); // İçerik boyutuna göre pencere boyutunu ayarla
            frame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
            frame.setVisible(true);
        });
    }
}
