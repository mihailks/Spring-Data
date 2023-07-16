package bg.softuni.carDealer;

import bg.softuni.carDealer.service.SuppliersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private SuppliersService suppliersService;

    public CommandLineRunnerImpl(SuppliersService suppliersService) {
        this.suppliersService = suppliersService;
    }

    @Override
    public void run(String... args) throws Exception {
        
        //seedData();

    }

    private void seedData() throws IOException {
        suppliersService.seedSuppliers();
    }


}
