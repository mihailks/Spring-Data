package bg.softuni.eautomappingobjects.service.impl;

import bg.softuni.eautomappingobjects.model.dto.GameAddDTO;
import bg.softuni.eautomappingobjects.model.entity.Game;
import bg.softuni.eautomappingobjects.repository.GameRepository;
import bg.softuni.eautomappingobjects.service.GameService;
import bg.softuni.eautomappingobjects.service.UserService;
import bg.softuni.eautomappingobjects.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Override
    public void addGame(GameAddDTO gameAddDTO) {

//        if (userService.hasLoggedInUser()){
//
//        }

        Set<ConstraintViolation<GameAddDTO>> violations = validationUtil.violation(gameAddDTO);

        if (!violations.isEmpty()) {
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        Game game = modelMapper.map(gameAddDTO, Game.class);
        //game.setReleaseDate(LocalDate.parse(gameAddDTO.getReleaseDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        gameRepository.save(game);
        System.out.println("Added game " + gameAddDTO.getTitle());

    }

    @Override
    public void editGame(Long id, BigDecimal price, double size) {
        Game game = gameRepository.findById(id).orElse(null);
        if (game == null) {
            System.out.println("wrong game ID");
            return;
        }
        game.setPrice(price);
        game.setSize(size);

        gameRepository.save(game);
        System.out.println("Edited " + game.getTitle());
    }

    @Override
    public void deleteGame(long id) {
        Game game = gameRepository.findById(id).orElse(null);
        if (game == null) {
            System.out.println("wrong game ID");
            return;
        }
        gameRepository.delete(game);
        System.out.println("Deleted " + game.getTitle());
    }
}
