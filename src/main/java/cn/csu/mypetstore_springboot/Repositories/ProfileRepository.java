package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Transactional
    @Modifying
    @Query(value = """
            UPDATE PROFILE INNER JOIN account
            ON account.username = :#{#username}
            SET langpref = :#{#profile.langpref},
            FAVCATEGORY = :#{#profile.favcategory}
            WHERE profile.id = account.profile_id""", nativeQuery = true)
    void updateProfileByUserId(@Param("username") String username, Profile profile);
}
