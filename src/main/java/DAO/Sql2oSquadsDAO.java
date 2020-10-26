package DAO;

import models.Hero;
import models.Squad;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oSquadsDAO implements SquadsDAO {

    private final Sql2o sql2o;

    public Sql2oSquadsDAO(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Squad> getAllSquads() {
        String sql = "SELECT * FROM squads";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Squad.class);
        } catch (Sql2oException ex) {
            System.out.println(ex);
            return null;
        }

    }

    @Override
    public void addMySquad(Squad squad) {
        String sql = "INSERT INTO squads (squadName, squadCause, squadSize) VALUES (:squadName, :squadCause, :squadSize)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true).bind(squad).executeUpdate().getKey();
            squad.getId();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Squad getSquadById(int id) {
        String sql = "SELECT * FROM squads WHERE id=:id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Squad.class);
        } catch (Sql2oException ex) {
            System.out.println(ex);

        }
        return null;
    }

    @Override
    public List<Hero> getHeroSquadsById(int id) {
        String sql = "SELECT * FROM heroes WHERE squadId=:id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetch(Hero.class);
        } catch (Sql2oException ex) {
            System.out.println(ex);

            return null;
        }
    }

    @Override
    public void deleteSquadById(int id) {
        String sql = "DELETE FROM squads WHERE id=:id";
        try(Connection conn = sql2o.open()){
            conn.createQuery(sql).addParameter("id",id).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void squadsUpdate(String squadName, String squadCause, int squadSize) {
        String sql = "UPDATE heroes SET squadName = :squadName, squadCause = :squadCause, squadSize = :squadSize WHERE id=:id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql).addParameter("squadName", squadName).addParameter("squadCause", squadCause).addParameter("squadSize", squadSize)
                    .executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }


    }
    @Override
    public void deleteHeroesWithSquad(int id) {
        String sql = "DELETE FROM heroes WHERE squadId=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}