package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Account;
import cn.csu.mypetstore_springboot.domain.PetBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Available
 */
public interface PetBreedRepository extends JpaRepository<PetBreed, Long> {
    List<PetBreed> getPetBreedsByCategoryId(Long categoryId);

    PetBreed getPetBreedById(Long id);

    @Transactional
    @Query(value = """
            select * from pet_breed WHERE lower(name) like :keyword""", nativeQuery = true)
    List<PetBreed> searchPetBreedsByName(String keyword);

    @Transactional
    @Query(value = "SELECT * FROM pet_breed LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<PetBreed> getPetBreedsByLimitAndOffset(int limit, int offset);
}
