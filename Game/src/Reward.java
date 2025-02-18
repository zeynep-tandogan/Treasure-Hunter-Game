public class Reward {
    public Location location;
    private int size_reward = 20;


    public int getSize_reward() {
        return size_reward;
    }

    public static class Gold_Reward extends Reward{
        public Gold_Reward() {
            do {
                this.location = new Location();
            } while (location.isLocationOccupied(location.getX(), location.getY(), getSize_reward(), getSize_reward()));
            location.matrix_map(location.getX(), location.getY(), getSize_reward(), getSize_reward(), 11);
        }
    }

    public static class Bronze_Reward extends Reward{
        public Bronze_Reward() {
            do {
                this.location = new Location();
            } while (location.isLocationOccupied(location.getX(), location.getY(), getSize_reward(), getSize_reward()));
            location.matrix_map(location.getX(), location.getY(), getSize_reward(), getSize_reward(), 12);
        }
    }

    public static class Silver_Reward extends Reward{
        public Silver_Reward() {
            do {
                this.location = new Location();
            } while (location.isLocationOccupied(location.getX(), location.getY(), getSize_reward(), getSize_reward()));
            location.matrix_map(location.getX(), location.getY(),getSize_reward(), getSize_reward(), 13);
        }
    }

    public static class Emerald_Reward extends Reward{
        public Emerald_Reward() {
            do {
                this.location = new Location();
            } while (location.isLocationOccupied(location.getX(), location.getY(), getSize_reward(), getSize_reward()));
            location.matrix_map(location.getX(), location.getY(), getSize_reward(), getSize_reward(), 14);
        }
    }
}
