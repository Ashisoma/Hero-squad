package DAO;

import models.Hero;

import java.util.List;

public interface HeroesDAO {
    List<Hero> getAllHeroes();
    void addHero(Hero hero);
    Hero getHeroById(int id);
    void deleteHeroById(int id);
    void heroesUpdate(int id, String name, String superPower, String weakness, int age, int squadId);
}
