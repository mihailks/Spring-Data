package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.ExportPassengerDto;
import softuni.exam.models.entities.Passenger;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByEmail(String email);


    @Query("select distinct p from Passenger as p " +
            "join fetch p.tickets as t " +
            "order by size(p.tickets) desc , p.email")
    List<Passenger> findAllByTicketsCount();


    @Query("select new softuni.exam.models.dto.ExportPassengerDto(" +
            " p.firstName, p.lastName, p.email, p.phoneNumber, t.serialNumber, count(t.passenger))" +
            " from Passenger as p" +
            " join Ticket as t on p.id = t.passenger.id" +
            " group by p.email" +
            " order by count(t.passenger) desc, p.email")
    List<ExportPassengerDto> exportPassenger();

//    @Query(value = "select p.first_name,p.last_name, p.email, p.phone_number,count(t.passenger_id) from passengers as p " +
//            "join tickets as t on p.id = t.passenger_id " +
//            "group by t.passenger_id, p.email " +
//            "order by count(t.passenger_id) desc , p.email"
//            ,nativeQuery = true)


}
