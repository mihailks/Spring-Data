package bg.softuni.carDealer.service.impl;

import bg.softuni.carDealer.model.DTO.O1seedDTO.SupplierSeedDTO;
import bg.softuni.carDealer.model.entity.Supplier;
import bg.softuni.carDealer.repository.SupplierRepository;
import bg.softuni.carDealer.service.SuppliersService;
import bg.softuni.carDealer.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static bg.softuni.carDealer.constanst.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class SuppliersServiceImpl implements SuppliersService {
    private static final String SUPPLIERS_FILE_NAME = "suppliers.json";
    private SupplierRepository supplierRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidationUtil validationUtil;

    public SuppliersServiceImpl(SupplierRepository suppliersRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.supplierRepository = suppliersRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedSuppliers() throws IOException {
        if (supplierRepository.count() > 0) {
            return;
        }
        // ot sydyrjanie na JSON
        String fileContent = Files.readString(Path.of(RESOURCES_FILE_PATH + SUPPLIERS_FILE_NAME));

        // otJSON kym DTO
        SupplierSeedDTO[] supplierSeedDTOs = gson.fromJson(fileContent, SupplierSeedDTO[].class);

        //save DTO to DB

        Arrays.stream(supplierSeedDTOs)
                .filter(validationUtil::isValid)
                .map(supplierSeedDTO -> modelMapper.map(supplierSeedDTO, Supplier.class))
                .forEach(supplierRepository::save);






    }
}
