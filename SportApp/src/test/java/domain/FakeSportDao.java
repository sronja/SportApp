/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import sportapp.dao.SportDao;
import sportapp.domain.Sport;

/**
 *
 * @author Ronja
 */
public class FakeSportDao implements SportDao {
    List<Sport> sports;
    
    public FakeSportDao() {
        sports = new ArrayList<>();
    }
    private int newId() {
        return sports.size() + 1;
    }
    
    @Override
    public Sport create(Sport sport) {
        sport.setId(newId());
        sports.add(sport);
        return sport;
    }

    @Override
    public List<Sport> getAll() {
        return sports;
    }

    @Override
    public void delete(String username)  {
        List removedList = new ArrayList();
            for (Sport sport: sports) {
                if (sport.getUser().getUsername().equals(username)) {
                    removedList.add(sport);
                }
            }   
            sports.removeAll(removedList);
        }

    @Override
    public void deleteSpecific(String type, double time, double distance, int heartrate, int feeling, String username) {
        for (Sport sport: sports) {
            if (sport.getType().equals(type) && sport.getTime() == time && sport.getDistance() == distance && sport.getHeartrate() == heartrate && sport.getFeeling() == feeling && sport.getUser().getUsername().equals(username)) {
                sports.remove(sport);
            }  
        }
    }
}
