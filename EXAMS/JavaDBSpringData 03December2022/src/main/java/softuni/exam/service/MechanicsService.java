package softuni.exam.service;


import softuni.exam.models.dto.MechanicSeedDTO;
import softuni.exam.models.entity.Mechanic;

import java.io.IOException;
import java.util.Optional;

// TODO: Implement all methods
public interface MechanicsService {

    boolean areImported();

    String readMechanicsFromFile() throws IOException;

    String importMechanics() throws IOException;

    Optional<Mechanic> findByName(String firstName);
}
