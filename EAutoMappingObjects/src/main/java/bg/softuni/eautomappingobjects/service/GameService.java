package bg.softuni.eautomappingobjects.service;

import bg.softuni.eautomappingobjects.model.dto.GameAddDTO;

import java.math.BigDecimal;

public interface GameService {


    void addGame(GameAddDTO gameAddDTO);


    void editGame(Long id, BigDecimal price, double size);


    void deleteGame(long id);

    void viewAllGames();

    void viewInfoGame(String title);

}
