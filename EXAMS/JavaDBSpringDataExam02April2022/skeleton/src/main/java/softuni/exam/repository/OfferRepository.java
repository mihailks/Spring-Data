package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.ApartmentType;
import softuni.exam.models.entity.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {


//    List<Offer> findAllByApartment_ApartmentTypeOrderByApartment_ApartmentAreaDescApartment_Apartment_price(ApartmentType apartmentType);
//    List<Offer> findAllByApartment_ApartmentType(ApartmentType apartmentType);
    List<Offer> findAllByApartment_ApartmentTypeOrderByApartment_AreaDescPrice(ApartmentType apartment_apartmentType);


}
