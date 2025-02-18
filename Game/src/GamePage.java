
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.*;

import static java.awt.geom.Path2D.intersects;

public class GamePage extends JPanel implements KeyListener {
    public static int windowWidth = 1000;
    public static int windowHeight = 1080;
    public static Character player;
    private Image
            playerImage,
            fogImage,
            summerBackgroundImage,winterBackgroundImage,
            obstacleSummerTreeImage,obstacleWinterTreeImage,
            obstacleSummerMountainImage,obstacleWinterMountainImage,
            obstacleRockImage,obstacleWallImage,
            obstacleBirdImage,obstacleBeeImage,
            rewardSilverImage,rewardBronzeImage,rewardGoldImage,rewardEmeraldImage;
    private Movement movement;
    ArrayList<DynamicObstacles.Bird> birds = Main.getBird_obstacles();
    ArrayList<DynamicObstacles.Bee> bees = Main.getBee_obstacles();

    public static JLabel rewardLabel = new JLabel();

    public GamePage() {
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        player = new Character(1, "sema");
        Location.first_fog_map();
        movement = new Movement(Location.getMap(), Location.getFogMap());
        this.add(rewardLabel);
        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean temp = movement.moveCharacter(player);
                if (!temp) {
                    ((Timer) e.getSource()).stop(); // Timer'ı durdur
                }
                Location.updateFogMap(player.getCharacterX(), player.getCharacterY());
                checkRewardCollision(player.getCharacterX(), player.getCharacterY());

                for (DynamicObstacles.Bird bird : birds) {
                    bird.update();
                }
                for (DynamicObstacles.Bee bee : bees) {
                    bee.update();
                }

                //bird.update();
                //bee.update();
                repaint(); // Repaint the component to update the screen

            }
        });
        timer.start();
        setFocusable(true);
        loadImages();
        addKeyListener(this);
    }
    private void loadImages() {
        playerImage = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/character.png").getImage();
        fogImage =  new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/fog.png").getImage();
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

        // Ekranın ortasını hesapla
        int centerX = windowWidth / 2;
        int centerY = windowHeight / 2;

        // Oyuncunun dünya üzerindeki konumuna göre kamera offset'lerini hesapla
        int offsetX = centerX - player.getCharacterX()*2;
        int offsetY = centerY - player.getCharacterY()*2;
        // Engelleri map matrisine göre çiz
        int[][] map = Location.getMap();
        int[][] fog_map = Location.getFogMap();
        int gridSize = 20; // Her bir kare için boyut (10x10)

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (j <= map[0].length / 2) {
                    g.drawImage(summerBackgroundImage, j * gridSize + offsetX, i * gridSize + offsetY, gridSize, gridSize, this);
                } else if (j > map[0].length / 2) {
                    g.drawImage(winterBackgroundImage, j * gridSize + offsetX, i * gridSize + offsetY, gridSize, gridSize, this);
                }
            }
        }

        // Izgara çizimi
        g.setColor(Color.GRAY); // Izgara çizgileri için renk
        for (int i = 0; i <= windowHeight; i += gridSize) {
            g.drawLine(0, i, windowWidth, i);
        }
        for (int j = 0; j <= windowWidth; j += gridSize) {
            g.drawLine(j, 0, j, windowHeight);
        }


        boolean[][] visited = new boolean[map.length][map[0].length]; // Ziyaret edilen hücreleri takip etmek için

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int obstacle_id = map[i][j];
                if (obstacle_id != 0 && !visited[i][j]) { // Eğer hücre ziyaret edilmediyse ve engel varsa
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
                            g.drawImage(obstacleSummerTreeImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
                        case 2: // Winter_Tree
                            g.drawImage(obstacleWinterTreeImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
                        case 3: // Winter_Tree
                            g.drawImage(obstacleRockImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
                        case 4: // Winter_Tree
                            g.drawImage(obstacleWallImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
                        case 5: // Summer_Mountain
                            g.drawImage(obstacleSummerMountainImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
                        case 6: // Winter_Mountain
                            g.drawImage(obstacleWinterMountainImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
                        case 7: // Winter_Mountain
                            g.setColor(Color.RED); // Kırmızı rengi ayarlıyoruz.
                            g.fillRect(startX * gridSize+ offsetX, startY * gridSize+ offsetY, width, height); // İlgili hücreyi kırmızı ile dolduruyoruz.
                            for (DynamicObstacles.Bird bird : birds){
                                g.drawImage(obstacleBirdImage, startX * gridSize+ offsetX, startY * gridSize+ offsetY, bird.getGetBird_width()*2, bird.getBird_height()*2, this);
                            }
                            break;
                        case 8: // Winter_Mountain
                            //g.setColor(Color.RED); // Kırmızı rengi ayarlıyoruz.
                            g.fillRect(startX * gridSize+ offsetX, startY * gridSize+ offsetY, width, height); // İlgili hücreyi kırmızı ile dolduruyoruz.
                            for (DynamicObstacles.Bee bee : bees) {
                                g.drawImage(obstacleBeeImage, startX * gridSize+ offsetX, startY * gridSize+ offsetY, bee.getGetBee_width()*2, bee.getBee_height()*2, this);
                            }
                            break;
                        case 11: // Winter_Mountain
                            g.drawImage(rewardGoldImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
                        case 12: // Winter_Mountain
                            g.drawImage(rewardBronzeImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
                        case 13: // Winter_Mountain
                            g.drawImage(rewardSilverImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
                        case 14: // Winter_Mountain
                            g.drawImage(rewardEmeraldImage, startX * gridSize + offsetX, startY * gridSize + offsetY, width, height, this);
                            break;
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


        for (int i = 0; i < fog_map.length; i++) {
            for (int j = 0; j < fog_map[i].length; j++) {
                if(fog_map[i][j]==1){
                    g.drawImage(fogImage, j * gridSize + offsetX, i * gridSize + offsetY, gridSize,gridSize, this);
                }
            }
        }

        // Oyuncuyu ekranın merkezinde çiz
        g.drawImage(playerImage, centerX - (player.getCharacter_width() / 2)+5, centerY - (player.getCharacter_height() / 2)+5, player.getCharacter_width()*2, player.getCharacter_height()*2, this);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int map_x = Main.map_x;
        int map_y = Main.map_y;
        movePlayer(key);
        //checkCollisions();
        repaint();
    }

    private void movePlayer(int key) {
        int moveAmount = 10; // Hareket miktarı
        int newX = player.location.getX();
        int newY = player.location.getY();

        // Oyuncunun hareket yönüne bağlı olarak yeni potansiyel konumu hesapla
        if (key == KeyEvent.VK_LEFT) {
            newX -= moveAmount;
        } else if (key == KeyEvent.VK_RIGHT) {
            newX += moveAmount;
        } else if (key == KeyEvent.VK_UP) {
            newY -= moveAmount;
        } else if (key == KeyEvent.VK_DOWN) {
            newY += moveAmount;
        }

        // Yeni konumun harita sınırları içinde olup olmadığını kontrol et
        if (newX >= 0 && newX <= Main.map_x - player.getCharacter_width() && newY >= 0 && newY <= Main.map_y - player.getCharacter_height()) {
            player.location.setX(newX);
            player.location.setY(newY);
            Location.updateFogMap(newX, newY);
            checkRewardCollision(newX, newY); // Ödülle etkileşimi kontrol et
        }

    }

    private void checkRewardCollision(int playerX, int playerY) {
        int gridX = playerX / 10;
        int gridY = playerY / 10;
        int[][] map = Location.getMap();
        int rewardValue = map[gridY][gridX]; // Dikkat: Önce Y sonra X

        if (rewardValue >= 11 && rewardValue <= 14) {
            if (map[gridY + 1][gridX] == rewardValue && map[gridY][gridX + 1] == rewardValue) {
                for (int i = gridY; i < gridY + 2 && i < map.length; i++) {
                    for (int j = gridX; j < gridX + 2 && j < map[i].length; j++) {
                        map[i][j] = 0; // Belirli hücreyi 0 ile güncelle
                    }
                }
            } else if (map[gridY + 1][gridX] == rewardValue && map[gridY][gridX - 1] == rewardValue) {
                for (int i = gridY; i < gridY + 2 && i < map.length; i++) {
                    for (int j = gridX - 2; j < gridX + 2 && j < map[i].length; j++) {
                        map[i][j] = 0; // Belirli hücreyi 0 ile güncelle
                    }
                }
            } else if (map[gridY - 1][gridX] == rewardValue && map[gridY][gridX - 1] == rewardValue) {
                for (int i = gridY - 2; i < gridY + 2 && i < map.length; i++) {
                    for (int j = gridX - 2; j < gridX + 2 && j < map[i].length; j++) {
                        map[i][j] = 0; // Belirli hücreyi 0 ile güncelle
                    }
                }
            } else if (map[gridY - 1][gridX] == rewardValue && map[gridY][gridX + 1] == rewardValue) {
                for (int i = gridY - 2; i < gridY + 2 && i < map.length; i++) {
                    for (int j = gridX; j < gridX + 2 && j < map[i].length; j++) {
                        map[i][j] = 0; // Belirli hücreyi 0 ile güncelle
                    }
                }
            }
            // Haritayı güncelle
            Location.setMap(map);

            // Ödül toplandığında puanı artır veya ilgili işlemi yap
            // score++; // Örneğin
        }
    }




    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void keyTyped(KeyEvent e) { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simple Game");
            Main gamePanel = new Main();
            frame.add(gamePanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}