package cn.csu.mypetstore_springboot.domain;

import jakarta.persistence.*;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String langpref;
    private String favcategory;
    private boolean mylistopt;
    private boolean banneropt;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public String getLangpref() {
        return langpref;
    }

    public void setLangpref(String langpref) {
        this.langpref = langpref;
    }

    public String getFavcategory() {
        return favcategory;
    }

    public void setFavcategory(String favcategory) {
        this.favcategory = favcategory;
    }

    public boolean isBanneropt() {
        return banneropt;
    }

    public void setBanneropt(boolean banneropt) {
        this.banneropt = banneropt;
    }

    public boolean isMylistopt() {
        return mylistopt;
    }

    public void setMylistopt(boolean mylistopt) {
        this.mylistopt = mylistopt;
    }
}
