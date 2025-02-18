import java.util.Random;

public class PermanentObstacles {

    public static class Summer_Tree extends Obstacle {
        private int size_tree;
        Random rand = new Random();
        public Summer_Tree() {
            super(); //obstacle this.location = new Location();
            this.size_tree = (rand.nextInt(4) + 2)*10;
            do {
                location.Location_summer(); // Yeni bir konum atayın.
            } while (location.isLocationOccupied(location.getX(), location.getY(), size_tree, size_tree));
            location.matrix_map(location.getX(), location.getY(), getSize_tree(), getSize_tree(), 1);
        }
        // Ağac özellikleri 2X2,3X3,4X4,5X5
        public int getSize_tree() {
            return size_tree;
        }

        public int getX() {
            return this.location.getX();
        }

        public int getY() {
            return this.location.getY();
        }
    }


    public static class Winter_Tree extends Obstacle {
        private int size_tree;
        Random rand = new Random();
        public Winter_Tree() {
            super(); //obstacle this.location = new Location();
            this.size_tree = (rand.nextInt(4) + 2)*10;
            do {
                location.Location_winter(); // Yeni bir konum atayın.
            } while (location.isLocationOccupied(location.getX(), location.getY(), size_tree, size_tree));
            location.matrix_map(location.getX(), location.getY(), getSize_tree(), getSize_tree(), 2);
        }
        // Ağac özellikleri 2X2,3X3,4X4,5X5

        public int getSize_tree() {
            return size_tree;
        }

        public int getX() {
            return this.location.getX();
        }

        public int getY() {
            return this.location.getY();
        }
    }



    public static class Rock extends Obstacle {
        private int size_rock ;
        Random rand = new Random();

        public Rock() {
            super();
            this.size_rock = (rand.nextInt((3-2)+1)+2)*10;
            do {
                this.location = new Location();
            } while (location.isLocationOccupied(location.getX(), location.getY(), size_rock, size_rock));
            location.matrix_map(location.getX(), location.getY(), size_rock,size_rock,3);
        }
        // Kaya özellikleri 2X2,3X3

        public int getSize_rock() {
            return size_rock;
        }

    }

    public static class Wall extends Obstacle {

        private int size_wall_height = 100,size_wall_weight = 10;


        public Wall() {
            super();
            do {
                this.location = new Location();
            } while (location.isLocationOccupied(location.getX(), location.getY(), size_wall_height, size_wall_weight));
            location.matrix_map(location.getX(), location.getY(), size_wall_height,size_wall_weight,4);
        }
        // Duvar özellikleri 10X1

        public int getSize_wall_height() {
            return size_wall_height;
        }

        public int getSize_wall_weight() {
            return size_wall_weight;
        }
    }

    public static class Summer_Mountain extends Obstacle {
        private int size_mountain = 90;
        public Summer_Mountain() {
            super();
            do {
                location.Location_summer(); // Yeni bir konum atayın.
            } while (location.isLocationOccupied(location.getX(), location.getY(), 90, 90));
            location.matrix_map(location.getX(), location.getY(), 90, 90, 5);
        }
        // Dağ özellikleri

        public int getSize_mountain() {
            return size_mountain;
        }
    }

    public static class Winter_Mountain extends Obstacle {
        private int size_mountain = 90;
        public Winter_Mountain() {
            super();
            do {
                location.Location_winter(); // Yeni bir konum atayın.
            } while (location.isLocationOccupied(location.getX(), location.getY(),90, 90));
            location.matrix_map(location.getX(), location.getY(), 90,90, 6);
        }
        // Dağ özellikleri

        public int getSize_mountain() {
            return size_mountain;
        }
    }

}