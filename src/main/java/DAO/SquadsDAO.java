package DAO;

import models.Hero;
import models.Squad;

import java.util.List;

public interface SquadsDAO {
    List<Squad> getAllSquads();

    void addMySquad(Squad squad);

    Squad getSquadById(int id);

    List<Hero> getHeroSquadsById(int id);

    void deleteSquadById(int id);

    void deleteHeroesWithSquad(int id);

    void squadsUpdate(String squadName, String squadCause, int squadSize);
}
