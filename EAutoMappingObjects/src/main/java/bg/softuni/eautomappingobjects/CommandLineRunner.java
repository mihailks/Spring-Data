package bg.softuni.eautomappingobjects;

import bg.softuni.eautomappingobjects.model.dto.GameAddDTO;
import bg.softuni.eautomappingobjects.model.dto.UserLogInDTO;
import bg.softuni.eautomappingobjects.model.dto.UserRegisterDTO;
import bg.softuni.eautomappingobjects.service.GameService;
import bg.softuni.eautomappingobjects.service.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Scanner;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
    private final UserService userService;
    private final GameService gameService;

    public CommandLineRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {


        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Enter command:");
            String[] commands = scanner.next().split("\\|+");

            switch (commands[0]) {
                case "RegisterUser" -> userService.registerUser(
                        new UserRegisterDTO(commands[1], commands[2]
                                , commands[3], commands[4])
                );

                case "LoginUser" -> userService.logInUser(
                        new UserLogInDTO(commands[1], commands[2]));

                case "Logout" -> userService.logOutUser();

                case "AddGame" -> gameService.addGame(
                        new GameAddDTO(commands[1],
                                new BigDecimal(commands[2]),
                                Double.parseDouble(commands[3]),
                                commands[4],
                                commands[5],
                                commands[6],
                                commands[7])
                );

                case "EditGame" -> gameService.editGame(
                                Long.parseLong(commands[1]),
                                new BigDecimal(commands[2].split("=")[1]),
                                Double.parseDouble(commands[3].split("=")[1]));

                case "DeleteGame" -> gameService.deleteGame(Long.parseLong(commands[1]));

                case "AllGames" -> gameService.viewAllGames();

                case "DetailGame" -> gameService.viewInfoGame(commands[1]);

                // purchase by game name
                case "UserPurchaseGame" -> userService.purchaseGame(commands[1]);

                case "OwnedGames" -> userService.viewOwnedGames();

            }


        }
    }
}
