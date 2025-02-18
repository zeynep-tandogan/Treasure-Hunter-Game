public class DynamicObstacles {
    //yukarı aşağı

    public static class Bird extends Obstacle {
        private int bird_height = 20, getBird_width = 20;
        private float speed = 0.01F; // Engel hızı
        private int direction = 100; // Engel hareket yönü (1: sağa, -1: sola)
        // Engel hareket sınırları
        int x = location.getX();
        protected int minX, maxX;
        public Bird() {
            do {
                location = new Location(); // Yeni bir konum atayın.
            } while (location.isLocationOccupied(location.getX()-15, location.getY(),50, 20));
            location.matrix_map(location.getX()-15, location.getY(), 50,20, 7);
            minX = location.getX() ; // Örnek: Konumdan 25 birim yukarı
            maxX = location.getX() + 30;
        }
        public void update() {
            int x = location.getX();
            x += speed * direction;
            location.setX(x);
            // Engel sınırlara ulaştığında yönünü değiştir
            if (x <= minX || x >= maxX) {
                direction *= -1;
            }
        }

        public int getMinX() {
            return minX;
        }

        public void setMinX(int minX) {
            this.minX = minX;
        }

        public int getMaxX() {
            return maxX;
        }

        public void setMaxX(int maxX) {
            this.maxX = maxX;
        }
        // Kuş özellikleri

        public int getBird_height() {
            return bird_height;
        }

        public int getGetBird_width() {
            return getBird_width;
        }
    }


    public static class Bee extends Obstacle {
        private int bee_height = 20, getBee_width = 20;
        private float speed = 0.01F; // Engel hızı
        private int direction = 100; // Engel hareket yönü (1: sağa, -1: sola)
        // Engel hareket sınırları
        int y = location.getY();
        protected int minY, maxY;
        public Bee() {
            do {
                location = new Location(); // Yeni bir konum atayın.
            } while (location.isLocationOccupied(location.getX(), location.getY()-15,20, 50));
            location.matrix_map(location.getX(), location.getY()-15, 20,50, 8);
            minY = location.getY() ; // Örnek: Konumdan 25 birim yukarı
            maxY = location.getY() + 30;
        }
        //of düzgündü niye bozuldu ki ya :(
        public void update() {
            int y = location.getY();
            y += speed * direction;
            location.setY(y);
            // Engel sınırlara ulaştığında yönünü değiştir
            if (y <= minY || y >= maxY) {
                direction *= -1;
            }
        }

        public int getMinY() {
            return getMinY();
        }

        public void setMinX(int minY) {
            this.minY = minY;
        }

        public int getMaxY() {
            return maxY;
        }

        public void setMaxY(int maxY) {
            this.maxY = maxY;
        }
        // Kuş özellikleri

        public int getBee_height() {
            return bee_height;
        }

        public int getGetBee_width() {
            return getBee_width;
        }
    }



}
