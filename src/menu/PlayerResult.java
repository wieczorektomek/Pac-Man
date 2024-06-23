package menu;

import java.io.Serializable;

public class PlayerResult implements Serializable {
    private final String userName;
    private final int points;

    public PlayerResult(String userName, int points) {
        this.userName = userName;
        this.points = points;
    }

    public String getUserName() {
        return userName;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "[" + userName + "]" + " Score: " + points;
    }
}
