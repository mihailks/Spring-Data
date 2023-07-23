package softuni.exam.service;

import java.io.IOException;

public interface TeamService {

    String importTeams() throws IOException;

    boolean areImported();

    String readTeamsXmlFile();
}
