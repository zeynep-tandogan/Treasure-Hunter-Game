import java.util.*;

import static java.lang.Math.min;


public class Movement {
    private Random rand = new Random();
    private int[][] map;
    private int[][] fogMap;
    private boolean[][] visited; // Ziyaret edilen noktaları takip etmek için
    private  List<Integer> obstacleList = new ArrayList<>();
    public Movement(int[][] map, int[][] fogMap) {
        this.map = map;
        this.fogMap = fogMap;
        this.visited = new boolean[map.length][map[0].length];
    }
    int score = 0;
    int count = 0;
    public boolean moveCharacter(Character player) {
        if(score < 20) {
            resetVisited();
            List<Integer[]> targets = findTargetsAround(player);
            if (!targets.isEmpty()) {
                Integer[] closestTarget = findClosestTarget(player, targets);
                if(map[closestTarget[0]][closestTarget[1]] >=11 && map[closestTarget[0]][closestTarget[1]] <=14 )
                    count++;
                if (!movePlayerToTarget(player, closestTarget)) {
                    movePlayerTowardsUnexplored(player);
                }
            } else {
                movePlayerTowardsUnexplored(player);
            }
            return true;
        }
        return false;
    }

    private void resetVisited() {
        for (boolean[] row : visited) {
            Arrays.fill(row, false);
        }
    }

    private List<Integer[]> findTargetsAround(Character player) {
        List<Integer[]> targets = new ArrayList<>();
        int playerX = player.getCharacterX() / 10;
        int playerY = player.getCharacterY() / 10;
        boolean check = false;




        for (int y = Math.max(playerY - 5, 0); y <= Math.min(playerY + 5, map.length - 1); y++) {
            for (int x = Math.max(playerX - 5, 0); x <= Math.min(playerX + 5, map[0].length - 1); x++) {
                if (map[y][x] >= 11 && map[y][x] <= 14){
                    targets.add(new Integer[]{y, x});
                    check = true;
                }
                if(fogMap[y][x] == 1 && !visited[y][x] && map[y][x] == 0) {
                    targets.add(new Integer[]{y, x});
                    check = true;
                }
                else if(map[y][x]>=1 && map[y][x]<=6){
                    if(!obstacleList.isEmpty()) {
                        int lastindex = obstacleList.size()-1;
                        if(obstacleList.get(lastindex) != map[y][x]){
                            System.out.println("Engel keşfedildi: " + map[y][x] + " konum:(" + y + "," + x + ")");
                            obstacleList.add(map[y][x]);
                        }
                    }
                    else {//ee şimdi ?
                        obstacleList.add(map[y][x]);
                        System.out.println("Engel keşfedildi: " + map[y][x] + " konum:(" + y + "," + x + ")");
                    }
                }
            }
        }


        if(!check){
            for (int y = Math.max(playerY - 10, 0); y <= min(playerY + 10, map.length - 1); y++) {
                for (int x = Math.max(playerX - 10, 0); x <= min(playerX + 10, map[0].length - 1); x++) {
                    if (map[y][x] >= 11 && map[y][x] <= 14){
                        targets.add(new Integer[]{y, x});
                        check = true;
                    }
                    if(fogMap[y][x] == 1 && !visited[y][x] && map[y][x] == 0) {
                        targets.add(new Integer[]{y, x});
                        check = true;
                    }
                    //else if(map[y][x]>=1 && map[y][x]<=6 ) System.out.println("3Engel keşfedildi: " + map[y][x] + "konum:y,x"+ y + ","+ x);
                }
            }
        }
        if(!check){
            for (int y = Math.max(playerY - 50, 0); y <= min(playerY + 50, map.length - 1); y++) {
                for (int x = Math.max(playerX - 50, 0); x <= min(playerX + 50, map[0].length - 1); x++) {
                    if ((map[y][x] >= 11 && map[y][x] <= 14) || (fogMap[y][x] == 1 && !visited[y][x] && map[y][x] == 0)) {
                        targets.add(new Integer[]{y, x});
                        check = true;
                    }
                    //else if(map[y][x]>=1 && map[y][x]<=6 ) System.out.println("2Engel keşfedildi: " + map[y][x] + "konum:y,x"+ y + ","+ x);
                }
            }
        }
        return targets;
    }

    private Integer[] findClosestTarget(Character player, List<Integer[]> targets) {
        Integer[] closestTarget = null;
        double minDistance = Double.MAX_VALUE;
        int playerX = player.getCharacterX() / 10;
        int playerY = player.getCharacterY() / 10;

        for (Integer[] target : targets) {
            double distance = Math.sqrt(Math.pow(target[1] - playerX, 2) + Math.pow(target[0] - playerY, 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestTarget = target;
            }
        }
        return closestTarget;
    }

    private boolean movePlayerToTarget(Character player, Integer[] target) {
        int playerX = player.getCharacterX() / 10;
        int playerY = player.getCharacterY() / 10;
        int targetX = target[1];
        int targetY = target[0];

        boolean moved = false;

        if (playerX < targetX && (map[playerY][playerX + 1] == 0 || map[playerY][playerX + 1] == 11 || map[playerY][playerX + 1] == 12 || map[playerY][playerX + 1] == 13 || map[playerY][playerX + 1] == 14)) {
            player.setLocation((playerX + 1) * 10, player.getCharacterY());
            moved = true;
        } else if (playerX > targetX && (map[playerY][playerX - 1] == 0 || map[playerY][playerX - 1] == 11 || map[playerY][playerX - 1] == 12 || map[playerY][playerX - 1] == 13 || map[playerY][playerX - 1] == 14)) {
            player.setLocation((playerX - 1) * 10, player.getCharacterY());
            moved = true;
        }

        if (playerY < targetY && (map[playerY + 1][playerX] == 0 || map[playerY + 1][playerX] == 11 || map[playerY + 1][playerX] == 12 || map[playerY + 1][playerX] == 13 || map[playerY + 1][playerX] == 14)) {
            player.setLocation(player.getCharacterX(), (playerY + 1) * 10);
            moved = true;
        } else if (playerY > targetY && (map[playerY - 1][playerX] == 0 || map[playerY - 1][playerX] == 11 || map[playerY - 1][playerX] == 12 || map[playerY - 1][playerX] == 13 || map[playerY - 1][playerX] == 14)) {
            player.setLocation(player.getCharacterX(), (playerY - 1) * 10);
            moved = true;
        }
        checkForRewardAt(player.getCharacterX()/10, player.getCharacterY()/10, count);
        visited[playerY][playerX] = true;
        return moved;
    }
//
    private List<String> rewards = new ArrayList<>();

    private void checkForRewardAt(int x, int y, int steps) {
        // Map üzerindeki ödülün kontrolü
        int rewardId = map[y][x];
        if (rewardId >= 11 && rewardId <= 14) {
            String rewardType = "";
            switch (rewardId) {
                case 11: // Altın Ödül
                    rewardType = "Altın";
                    break;
                case 12: // Bronz Ödül
                    rewardType = "Bronz";
                    break;
                case 13: // Gümüş Ödül
                    rewardType = "Gümüş";
                    break;
                case 14: // Zümrüt Ödül
                    rewardType = "Zümrüt";
                    break;
            }
            String rewardText = String.format("%s Ödül alındı, id no: %d, Koordinatlar = (%d, %d), Adım Sayısı: %d",
                    rewardType, rewardId, x, y, steps);
            int index = 0;
            while (index < rewards.size() && Integer.parseInt(rewards.get(index).split(",")[1].trim().split(" ")[2]) < rewardId) {
                index++;
            }
            rewards.add(index, rewardText);
            score++;
            updateRewardLabel();
            count = 0;
        }
    }
    private void updateRewardLabel() {
        StringBuilder newText = new StringBuilder("<html>");
        for (String reward : rewards) {
            newText.append(reward).append("<br>");
        }
        newText.append("</html>");
        GamePage.rewardLabel.setText(newText.toString());
    }
    private void movePlayerTowardsUnexplored(Character player) {
        int playerX = player.getCharacterX() / 10;
        int playerY = player.getCharacterY() / 10;

        List<Integer[]> possibleMoves = new ArrayList<>();
        checkAndAddMove(playerX - 1, playerY, possibleMoves);
        checkAndAddMove(playerX + 1, playerY, possibleMoves);
        checkAndAddMove(playerX, playerY - 1, possibleMoves);
        checkAndAddMove(playerX, playerY + 1, possibleMoves);

        if (!possibleMoves.isEmpty()) {
            Integer[] move = possibleMoves.get(rand.nextInt(possibleMoves.size()));
            player.setLocation(move[1] * 10, move[0] * 10);
            visited[move[0]][move[1]] = true;
        } else {
            Random rand = new Random();
            int direction = rand.nextInt(4);

            switch (direction) {
                case 0: // Up
                    if (playerY - 1 > 0 && map[playerY - 1][playerX] == 0) {
                        playerY -= 1;
                        player.setLocation(playerX * 10, playerY * 10);//x,y
                    }
                    break;
                case 1: // Down
                    if (playerY + 1 < map.length && map[(playerY  + 1)][playerX] == 0) {
                        playerY += 1;
                        player.setLocation(playerX * 10, playerY * 10);//x,y
                    }
                    break;
                case 2: // Left
                    if (playerX - 1 >= 0 && map[playerY ][playerX - 1] == 0) {
                        playerX -= 1;
                        player.setLocation(playerX * 10, playerY * 10);//x,y
                    }
                    break;
                case 3: // Right
                    if (playerX + 1 < map[0].length && map[playerY][(playerX + 1)] == 0) {
                        playerX += 1;
                        player.setLocation(playerX * 10, playerY * 10);//x,y
                    }
                    break;
            }
        }
    }


    private void checkAndAddMove(int newX, int newY, List<Integer[]> possibleMoves) {
        if (newX >= 0 && newX < map[0].length && newY >= 0 && newY < map.length) {
            if (fogMap[newY][newX] == 1 && !visited[newY][newX] && map[newY][newX] == 0) {
                possibleMoves.add(new Integer[]{newY, newX});
            }
            // else if(map[newY][newX]>=1 && map[newY][newX]<=6 ) System.out.println("1Engel keşfedildi: " + map[newY][newX] + "konum:y,x"+ newY + ","+ newX);

        }
    }
    //
}