
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
   
    public Sport(String type, double time, double distance, User user) {
        this.id = id;
        this.type = type;
        this.time = time;
        this.distance = distance;
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
    public String getUser() {
        return user.getUsername();
    }
    public double getTime() {
        return time;
    }
    public double getDistance() {
        return distance;
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
