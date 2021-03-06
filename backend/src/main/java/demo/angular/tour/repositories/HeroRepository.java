package demo.angular.tour.repositories;

import demo.angular.tour.domain.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends CrudRepository<Hero, Integer> {

    public Iterable<Hero> findByNameContainingIgnoreCase(String name);
}
