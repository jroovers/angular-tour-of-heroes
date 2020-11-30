package demo.angular.tour.controllers;

import demo.angular.tour.domain.Hero;
import demo.angular.tour.repositories.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("heroes")
public class HeroController {

    @Autowired
    HeroRepository heroDao;

    @GetMapping("")
    public ResponseEntity<Hero[]> getHeroes(){
        List<Hero> heroArrayList = new ArrayList<>();
        heroDao.findAll().forEach(heroArrayList::add);
        return ResponseEntity.ok(heroArrayList.toArray(new Hero[]{}));
    }

    @GetMapping("{id}")
    public ResponseEntity<Hero> getHeroById(@PathVariable Integer id) {
        Optional<Hero> optionalHero = heroDao.findById(id);
        return optionalHero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
