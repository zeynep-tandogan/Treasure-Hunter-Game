import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main extends JFrame {
    public static int windowWidth = 1920;
    public static int windowHeight = 1080;
    public static int map_x,map_y;
    public Character player;
    private DynamicObstacles.Bird bird;
    private ArrayList<PermanentObstacles.Summer_Tree> summer_tree_obstacles = new ArrayList<PermanentObstacles.Summer_Tree>();
    private ArrayList<PermanentObstacles.Summer_Mountain> summer_mountain_obstacles = new ArrayList<PermanentObstacles.Summer_Mountain>();
    private ArrayList<PermanentObstacles.Winter_Tree> winter_tree_obstacles = new ArrayList<PermanentObstacles.Winter_Tree>();
    private ArrayList<PermanentObstacles.Winter_Mountain> winter_mountain_obstacles = new ArrayList<PermanentObstacles.Winter_Mountain>();
    private ArrayList<PermanentObstacles.Rock> rock_obstacles = new ArrayList<PermanentObstacles.Rock>();
    private ArrayList<PermanentObstacles.Wall> wall_obstacles = new ArrayList<PermanentObstacles.Wall>();
    private static ArrayList<DynamicObstacles.Bird> bird_obstacles = new ArrayList<DynamicObstacles.Bird>();
    private static ArrayList<DynamicObstacles.Bee> bee_obstacles = new ArrayList<DynamicObstacles.Bee>();
    private ArrayList<Reward.Silver_Reward> silver_reward = new ArrayList<>();
    private ArrayList<Reward.Bronze_Reward> bronze_reward = new ArrayList<>();
    private ArrayList<Reward.Gold_Reward> gold_reward = new ArrayList<>();
    private ArrayList<Reward.Emerald_Reward> emerald_reward = new ArrayList<>();
    private Random rand = new Random();


    private JButton updateButton;
    private JButton openNewPageButton;
    private JButton openMapPageButton;
    private JTextField mapWidthField;
    private JTextField mapHeightField;

    public Main() {
        super("Giriş Sayfası");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(windowWidth, windowHeight);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon("C:/Users/zeyne/Desktop/Prolab/assets/main.png");
                Image backgroundImage = backgroundIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);

        mapWidthField = new JTextField(5);
        // mapWidthField'a odaklanma dinleyicisi ekle
        mapWidthField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (mapWidthField.getText().equals("Genişlik giriniz.")) {
                    mapWidthField.setText("");
                    mapWidthField.setForeground(Color.white);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (mapWidthField.getText().isEmpty()) { // Kullanıcı metni temizlediyse ve başka bir şey yazmadıysa
                    mapWidthField.setText("Genişlik giriniz."); // Placeholder metnini tekrar göster
                    mapWidthField.setForeground(Color.white); // Opsiyonel: Placeholder metni için farklı bir renk kullan
                }
            }
        });

        mapHeightField = new JTextField(5);
        mapHeightField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (mapHeightField.getText().equals("Yükseklik giriniz.")) { // Placeholder metniyle karşılaştır
                    mapHeightField.setText(""); // Kullanıcı odaklandığında metni temizle
                    mapHeightField.setForeground(Color.white); // Opsiyonel: Metin rengini değiştir
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (mapHeightField.getText().isEmpty()) { // Kullanıcı metni temizlediyse ve başka bir şey yazmadıysa
                    mapHeightField.setText("Yükseklik giriniz."); // Doğru placeholder metnini tekrar göster
                    mapHeightField.setForeground(Color.white); // Opsiyonel: Placeholder metni için farklı bir renk kullan
                }
            }
        });

// Başlangıçta placeholder metnini ve rengini ayarla
        mapWidthField.setText("Genişlik giriniz.");
        mapWidthField.setForeground(Color.white);

        mapHeightField.setText("Yükseklik giriniz.");
        mapHeightField.setForeground(Color.white);



        // Butonları burada oluştur
        updateButton = new JButton();
        openNewPageButton = new JButton();
        openMapPageButton = new JButton();

        // Şimdi butonların özelliklerini ayarla
        updateButton.setContentAreaFilled(false);
        updateButton.setBorderPainted(false);
        updateButton.setOpaque(false);
        updateButton.setBounds(1070, 530, 168, 45);

        openNewPageButton.setContentAreaFilled(false);
        openNewPageButton.setBorderPainted(false);
        openNewPageButton.setOpaque(false);
        openNewPageButton.setBounds(265, 400, 215, 70);

        openMapPageButton.setContentAreaFilled(false);
        openMapPageButton.setBorderPainted(false);
        openMapPageButton.setOpaque(false);
        openMapPageButton.setBounds(1020, 590, 190, 50);

        //mapWidthField.setOpaque(false);
        Border border = BorderFactory.createLineBorder(Color.white, 1); // Mavi renkli, 2 piksel kalınlığında bir çerçeve
        mapWidthField.setBackground(new Color(0, 0, 0, 150));
        mapHeightField.setBackground(new Color(0, 0, 0, 150));
        mapWidthField.setBorder(border);
        mapWidthField.setBounds(1130, 400, 165, 50);

        //mapHeightField.setOpaque(false);
        mapHeightField.setBorder(border);
        mapHeightField.setBounds(1070, 465, 183, 50);

        // Bileşenleri arka plan paneline ekle
        backgroundPanel.add(mapWidthField);
        backgroundPanel.add(mapHeightField);
        backgroundPanel.add(updateButton);
        backgroundPanel.add(openNewPageButton);
        backgroundPanel.add(openMapPageButton);

        add(backgroundPanel);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                map_x = Integer.parseInt(mapWidthField.getText());
                map_y = Integer.parseInt(mapHeightField.getText());
                initGame();
                //.showMessageDialog(null, "Değer Güncellendi: " + map_x+map_y);
            }
        });
        openNewPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame gameFrame = new JFrame("Oyun Sayfası"); // Yeni bir JFrame oluştur
                GamePage gamePage = new GamePage(); // GamePage panelini oluştur

                gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Sadece bu pencereyi kapat
                gameFrame.setSize(windowWidth, windowHeight); // Pencere boyutunu ayarla
                gameFrame.add(gamePage); // GamePage panelini JFrame'e ekle
                gameFrame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
                gameFrame.setVisible(true); // Pencereyi görünür yap

                // Bu satırı ekleyin eğer yeni açılan pencerede tuş vuruşlarını algılamasını istiyorsanız
                gamePage.requestFocusInWindow();
            }
        });


        openMapPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame gameFrame = new JFrame("Oyun Sayfası"); // Yeni bir JFrame oluştur
                MapPage gamePage = new MapPage(); // GamePage panelini oluştur

                gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Sadece bu pencereyi kapat
                gameFrame.setSize(windowWidth, windowHeight); // Pencere boyutunu ayarla
                gameFrame.add(gamePage); // GamePage panelini JFrame'e ekle
                gameFrame.setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir
                gameFrame.setVisible(true); // Pencereyi görünür yap

                // Bu satırı ekleyin eğer yeni açılan pencerede tuş vuruşlarını algılamasını istiyorsanız
                gamePage.requestFocusInWindow();
            }
        });

        setLocationRelativeTo(null); // Pencereyi ekranda ortalar
    }

    private void initGame() {
        for (int i = 0; i < 5; i++) {
            summer_tree_obstacles.add(new PermanentObstacles.Summer_Tree());
            summer_mountain_obstacles.add(new PermanentObstacles.Summer_Mountain());
            winter_tree_obstacles.add(new PermanentObstacles.Winter_Tree());
            winter_mountain_obstacles.add(new PermanentObstacles.Winter_Mountain());
            rock_obstacles.add(new PermanentObstacles.Rock());
            wall_obstacles.add(new PermanentObstacles.Wall());
            silver_reward.add(new Reward.Silver_Reward());
            bronze_reward.add(new Reward.Bronze_Reward());
            gold_reward.add(new Reward.Gold_Reward());
            emerald_reward.add(new Reward.Emerald_Reward());
        }
        for (int i = 0; i < 2; i++) {
            bird_obstacles.add(new DynamicObstacles.Bird());
            bee_obstacles.add(new DynamicObstacles.Bee());
        }
        Location.printMap();
    }

    public static ArrayList<DynamicObstacles.Bird> getBird_obstacles() {
        return bird_obstacles;
    }

    public static ArrayList<DynamicObstacles.Bee> getBee_obstacles() {
        return bee_obstacles;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}



