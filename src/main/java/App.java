import DAO.Sql2oHeroesDAO;
import DAO.Sql2oSquadsDAO;
import models.Hero;

import static spark.Spark.staticFileLocation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ObjDoubleConsumer;

import models.Squad;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

//ec2-18-211-86-133.compute-1.amazonaws.com:5432:d8seknpio0qb3o;
        //anjjxtvbqbubbu //87a777b3175d76cb91b03c71764d8fbc5801514c6f9fa195979721e6703c0ae6
        String connectionString = "jdbc:postgresql://localhost:5432/hero"; //!
       //Sql2o sql2o = new Sql2o(connectionString, "", ""); //!Connection con;
        Sql2o sql2o = new Sql2o(connectionString, "moringa", "Access");
      //  String connectionString = "jdbc:postgresql://ec2-18-211-86-133.compute-1.amazonaws.com:5432/d8seknpio0qb3o";
       // Sql2o sql2o = new Sql2o(connectionString,"anjjxtvbqbubbu","87a777b3175d76cb91b03c71764d8fbc5801514c6f9fa195979721e6703c0ae6");
        Sql2oSquadsDAO squadsDAO = new Sql2oSquadsDAO(sql2o);
        Sql2oHeroesDAO heroesDAO = new Sql2oHeroesDAO(sql2o);
        Map<String, Object> model = new HashMap<>();

        get("/", (request, response) -> {
            model.put("squads", squadsDAO.getAllSquads());
            model.put("heroes", heroesDAO.getAllHeroes());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());


        get("/add-hero", (request, response) -> {
            model.put("heroes", squadsDAO.getAllSquads());
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/add-hero",(req,res)->{
            String name = req.queryParams("name");
            String power = req.queryParams("power");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            int squadId = Integer.parseInt(req.queryParams("squad"));
            Hero anotherHero = new Hero(name, age, power,weakness, squadId);
            heroesDAO.addHero(anotherHero);
            model.put("squads", squadsDAO.getAllSquads());
            model.put("heroes", heroesDAO.getAllHeroes());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
//
        get("/add-squad", (request, response) -> {
            model.put("squads",squadsDAO.getAllSquads());
            return new ModelAndView(model, "squad.hbs");
        }, new HandlebarsTemplateEngine());


        post("/add-squad", (req, res) -> {
            String name = req.queryParams("name");
            String cause = req.queryParams("cause");
            int size = Integer.parseInt(req.queryParams("size"));
            Squad anotherSquad = new Squad(name, cause, size);
            squadsDAO.addMySquad(anotherSquad);
            model.put("squads", squadsDAO.getAllSquads());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/heroes/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            Hero firstHero = heroesDAO.getHeroById(id);
            Squad firstSquad = squadsDAO.getSquadById(firstHero.getSquadId());
            model.put("heroes", heroesDAO.getHeroById(id));
            model.put("squad", firstSquad);
            return new ModelAndView(model, "hero-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/squads/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("heroes", squadsDAO.getHeroSquadsById(id));
            model.put("squads", squadsDAO.getSquadById(id));
            return new ModelAndView(model, "squad-details.hbs");
        }, new HandlebarsTemplateEngine());

        get("/delete-hero/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            heroesDAO.deleteHeroById(id);
            model.put("heroes", heroesDAO.getAllHeroes());
            model.put("squads", squadsDAO.getAllSquads());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/delete-squad/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            squadsDAO.deleteSquadById(id);
            squadsDAO.deleteHeroesWithSquad(id);
            model.put("heroes", heroesDAO.getAllHeroes());
            model.put("squads", squadsDAO.getAllSquads());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/edit-squad/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("editHero", true);
            model.put("heroes", heroesDAO.getHeroById(id));
            model.put("squads", squadsDAO.getAllSquads());
            return new ModelAndView(model, "squad.hbs");
        }, new HandlebarsTemplateEngine());

        get("/edit-hero/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            model.put("editHero", true);
            model.put("heroes", heroesDAO.getHeroById(id));
//            model.put("squads", squadsDAO.getAllSquads());
            return new ModelAndView(model, "hero-form.hbs");
        }, new HandlebarsTemplateEngine());

        post("/edit-hero/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            String name = req.queryParams("name");
            String power = req.queryParams("power");
            String weakness = req.queryParams("weakness");
            int age = Integer.parseInt(req.queryParams("age"));
            int squadId = Integer.parseInt(req.queryParams("squadId"));
            heroesDAO.heroesUpdate(id,name,power,weakness,age,squadId);
            model.put("heroes", heroesDAO.getHeroById(id));
            return new ModelAndView(model, "hero-details.hbs");
        }, new HandlebarsTemplateEngine());

        post("/edit-squad/:id", (req, res) -> {
            int id = Integer.parseInt(req.params("id"));
            String name = req.queryParams("name");
            String cause = req.queryParams("power");
            int size = Integer.parseInt(req.queryParams("size"));
            squadsDAO.squadsUpdate(name,cause,size);
            model.put("squad", squadsDAO.getHeroSquadsById(id));
            return new ModelAndView(model, "hero-details.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
