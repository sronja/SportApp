
package sportapp.domain;

/**
 * Yksittäistä liikuntasuoritusta kuvaava luokka
 */

public class Sport {
    
    private int id;
    private String type;
    private double time;
    private double distance;
    private User user;
    private int heartrate;
    private int feeling;
    
    public Sport(int id, String type, double time, double distance, int heartrate, int feeling, User user) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.distance = distance;
        this.heartrate = heartrate;
        this.feeling = feeling;
        this.user = user;
    }
    public Sport(String type, double time, double distance, int heartrate, int feeling, User user) {
        this.type = type;
        this.time = time;
        this.distance = distance;
        this.heartrate = heartrate;
        this.feeling = feeling;
        this.user = user;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public User getUser() {
        return user;
    }
    public double getTime() {
        return time;
    }
    public double getDistance() {
        return distance;
    }
    public int getHeartrate() {
        return heartrate;
    }
    public int getFeeling() {
        return feeling;
    }
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Sport)) {
            return false;
        }
        Sport other = (Sport) object;
        return id == other.id;
    }
}
