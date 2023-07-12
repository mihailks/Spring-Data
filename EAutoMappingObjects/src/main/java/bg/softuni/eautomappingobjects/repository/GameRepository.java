package bg.softuni.eautomappingobjects.repository;

import bg.softuni.eautomappingobjects.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
