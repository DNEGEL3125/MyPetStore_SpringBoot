package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.PetBreedRepository;
import cn.csu.mypetstore_springboot.Repositories.PetBreedRepositoryC;
import cn.csu.mypetstore_springboot.domain.PetBreed;
import cn.csu.mypetstore_springboot.utils.CamelToSnakeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public ResponseEntity<String> updatePetBreedsByIds(Map<String, PetBreed> changedAttrMap) {

        return ResponseEntity.ok("Update pet breeds successfully");
    }

    public ResponseEntity<String> updatePetBreedImage(MultipartFile image, Long petBreedId) {
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            // Get the filename and extension of the uploaded image
            String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            String fileName = System.currentTimeMillis() + "_" + originalFilename;

            // 获取文件时的路径
            String imageResourcePath = "/images/pet_breed_images/" + fileName;
            String filePathStr = "/Users/dnegel3125/Pictures/MyPetStore" + imageResourcePath;

            // Create directory if it doesn't exist

            Path directory = Paths.get(filePathStr).getParent();
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // Resolve the file path and save the image file
            Path filePath = directory.resolve(fileName);
            Files.copy(image.getInputStream(), filePath);

            petBreedRepository.updateImagePathById(petBreedId, imageResourcePath);

            return ResponseEntity.ok(imageResourcePath);
        } catch (IOException e) {
            logger.error("Failed to upload image: " + e.getMessage());
            return ResponseEntity.badRequest().body("Failed to upload image: " + e.getMessage());
        }
    }
}
