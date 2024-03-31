package cn.csu.mypetstore_springboot.Repositories;

import cn.csu.mypetstore_springboot.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE PROFILE SET LANGPREF = #{#profile.langpref}, FAVCATEGORY = #{#profile.favcategory} WHERE USERID = #{#username}", nativeQuery = true)
    void updateProfileByUserId(String username, Profile profile);
}
