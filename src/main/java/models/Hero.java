package models;

import java.util.ArrayList;
import java.util.List;

public class Hero {

    private String heroesName;
    private String superPower;
    private String weakness;
    private int age;
    private int id;
    private int squadId;

    public Hero(String heroesName, int age, String power, String weakness, int squadId){
     this.heroesName = heroesName;
     this.age = age;
     this.superPower = power;
     this.weakness = weakness;
     this.squadId = squadId;

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setHeroesName(String heroesName) {
        this.heroesName = heroesName;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSquadId() {
        return squadId;
    }

   // public void setSquadId(int squadId) {
     //   this.squadId = squadId;

    public void setSquadId(int squadId) {
        this.squadId = squadId;
    }
    //}

    public String getHeroesName() {
        return heroesName;
    }

    public String getSuperPower() {
        return superPower;
    }

    public String getWeakness() {
        return weakness;
    }

    public int getAge() {
        return age;
    }

    public String Squad(){
      return null;
    }
}
