package softuni.exam.service;


import softuni.exam.models.entities.Town;

import java.io.IOException;
import java.util.Optional;

//ToDo - Before start App implement this Service and set areImported to return false
public interface TownService {

    boolean areImported();

    String readTownsFileContent() throws IOException;
	
	String importTowns() throws IOException;

    Optional<Town> findByName(String town);
}
