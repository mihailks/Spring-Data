package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Long> {

//    Optional<Forecast> findFirstByDayOfWeekAndCity(DayOfWeek dayOfWeek, City city);
    Optional<Forecast> findFirstByCity_IdAndDayOfWeek(Long city_id, DayOfWeek dayOfWeek);


//    List<Forecast> findAllByDayOfWeekAndCity_Population

    Set<Forecast> findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DayOfWeek dayOfWeek, Integer city_population);


//    findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc


}
