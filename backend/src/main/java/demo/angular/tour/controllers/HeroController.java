package demo.angular.tour.controllers;

import demo.angular.tour.domain.Hero;
import demo.angular.tour.repositories.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("heroes")
@CrossOrigin(origins = "*")
public class HeroController {

    Logger LOG = LoggerFactory.getLogger(HeroController.class);

    @Autowired
    HeroRepository heroDao;

    @GetMapping("")
    public ResponseEntity<Hero[]> getHeroes(@RequestParam(required = false) String name) {
        if(name == null || name.isBlank()) {
            LOG.info("GET all heroes");
            List<Hero> heroArrayList = new ArrayList<>();
            heroDao.findAll().forEach(heroArrayList::add);
            return ResponseEntity.ok(heroArrayList.toArray(new Hero[]{}));
        }
        else{
            LOG.info("GET all heroes with name {}", name);
            List<Hero> heroArrayList = new ArrayList<>();
            heroDao.findByNameContains(name).forEach(heroArrayList::add);
            return ResponseEntity.ok(heroArrayList.toArray(new Hero[]{}));
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Hero> getHeroById(@PathVariable Integer id) {
        LOG.info("GET hero {}", id);
        Optional<Hero> optionalHero = heroDao.findById(id);
        return optionalHero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteHeroById(@PathVariable Integer id){
        LOG.info("DELETE hero {}", id);
        heroDao.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("")
    public ResponseEntity<Hero> updateHero(@RequestBody Hero hero) {
        LOG.info("UPDATE hero {}", hero.getId());
        if (hero.getId() != null) {
            Hero existing = heroDao.findById(hero.getId()).orElse(null);
            if (existing == null) {
                return ResponseEntity.notFound().build();
            } else {
                Hero saved = heroDao.save(hero);
                return ResponseEntity.ok(saved);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Hero> saveHero(@RequestBody Hero hero) {
        Hero toSave = new Hero();
        toSave.setName(hero.getName());
        Hero saved = heroDao.save(toSave);
        LOG.info("CREATE hero {}", saved.getId());
        return ResponseEntity.ok(saved);
    }




}
