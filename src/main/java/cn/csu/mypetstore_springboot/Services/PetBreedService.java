package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.PetBreedRepository;
import cn.csu.mypetstore_springboot.Repositories.PetBreedRepositoryC;
import cn.csu.mypetstore_springboot.domain.PetBreed;
import cn.csu.mypetstore_springboot.utils.CamelToSnakeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetBreedService {
    private final static String AVAILABLE_SEARCH_FOR_REGEX = "[a-zA-Z0-9_.]+";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PetBreedRepository petBreedRepository;
    private final PetBreedRepositoryC petBreedRepositoryC;


    public PetBreedService(PetBreedRepository petBreedRepository, PetBreedRepositoryC petBreedRepositoryC) {
        this.petBreedRepository = petBreedRepository;
        this.petBreedRepositoryC = petBreedRepositoryC;
    }

    public List<PetBreed> getPetBreeds() {
        return petBreedRepository.findAll();
    }

    public List<PetBreed> getPetBreeds(Integer page, int limit) {
        if (page == null) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        return petBreedRepository.getPetBreedsByLimitAndOffset(limit, offset);
    }

    public List<PetBreed> searchPetBreeds(Integer page, int limit, String searchFor, String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return getPetBreeds(page, limit);
        }

        if (page == null) {
            page = 1;
        }
        int offset = (page - 1) * limit;

        boolean isValid = searchFor.matches(AVAILABLE_SEARCH_FOR_REGEX);
        if (!isValid) {
            logger.info("searchFor='%s' in searchPetBreeds doesn't match %s"
                    .formatted(searchFor, AVAILABLE_SEARCH_FOR_REGEX));
            return new ArrayList<>();
        }

        String colName = CamelToSnakeConverter.camelToSnake(searchFor);

        return petBreedRepositoryC.searchPetBreedsByContains(colName, keyword, limit, offset);
    }

    public Long getMaxPageNumber(int limit) {
        return (petBreedRepository.count() - 1) / limit + 1;
    }

    public Long getMaxPageNumber(int limit, String keyword, String searchFor) {
        if (keyword == null || keyword.isEmpty()) {
            return getMaxPageNumber(limit);
        }

        boolean isValid = searchFor.matches(AVAILABLE_SEARCH_FOR_REGEX);
        if (!isValid) {
            logger.info("searchFor='%s' in getMaxPageNumber doesn't match %s"
                    .formatted(searchFor, AVAILABLE_SEARCH_FOR_REGEX));
            return 1L;
        }

        String colName = CamelToSnakeConverter.camelToSnake(searchFor);

        Long countedAccountsByContains = petBreedRepositoryC.countPetBreedsByContains(colName, keyword);
        return (countedAccountsByContains - 1) / limit + 1;
    }
}
