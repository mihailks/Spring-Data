package hiberspring.service.impl;

import com.google.gson.Gson;

import hiberspring.domain.dtos.BranchSeedDTO;
import hiberspring.domain.entities.Branch;
import hiberspring.repository.BranchRepository;
import hiberspring.service.BranchService;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static hiberspring.common.Constants.INCORRECT_DATA_MESSAGE;
import static hiberspring.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class BranchServiceImpl implements BranchService {

    public static final String BRANCHES_FILE_PATH = "C:\\Users\\mihai\\Documents\\Spring-Data\\EXAMS\\JavaDBAdvancedExam20Dec2018\\src\\main\\resources\\files\\branches.json";
    private BranchRepository branchRepository;
    private Gson gson;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private TownService townService;

    public BranchServiceImpl(BranchRepository branchRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, TownService townService) {
        this.branchRepository = branchRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.townService = townService;
    }

    @Override
    public Boolean branchesAreImported() {
        return branchRepository.count() > 0;
    }

    @Override
    public String readBranchesJsonFile() throws IOException {
        return Files.readString(Path.of(BRANCHES_FILE_PATH));
    }

    @Override
    public String importBranches(String branchesFileContent) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        BranchSeedDTO[] branchSeedDTOs = gson
                .fromJson(readBranchesJsonFile(), BranchSeedDTO[].class);

        for (BranchSeedDTO branchSeedDTO : branchSeedDTOs) {
            stringBuilder.append(System.lineSeparator());
            if (!validationUtil.isValid(branchSeedDTO)) {
                stringBuilder.append(INCORRECT_DATA_MESSAGE);
            } else {
                Branch branch = modelMapper.map(branchSeedDTO, Branch.class);
                branch.setTown(townService.findByName(branchSeedDTO.getTown()));
                branchRepository.save(branch);
                stringBuilder.append(String.format("Successfully imported Town %s", branch.getName()));
            }


        }

//        Arrays.stream(gson
//                        .fromJson(readBranchesJsonFile(), BranchSeedDTO[].class))
//                .filter(BranchSeedDTO -> {
//                    boolean isValid = validationUtil.isValid(BranchSeedDTO);
//                    stringBuilder.append(isValid
//                                    ? (String.format(SUCCESSFUL_IMPORT_MESSAGE, BranchSeedDTO.getName()))
//                                    : INCORRECT_DATA_MESSAGE)
//                            .append(System.lineSeparator());
//                    return isValid;
//                })
//                .map(BranchSeedDTO -> modelMapper.map(BranchSeedDTO, Branch.class))
//                .forEach(branchRepository::save);


        return stringBuilder.toString().trim();
    }

    @Override
    public Branch findByName(String branchName) {

        return branchRepository.findFirstByNameIs(branchName);
    }
}













