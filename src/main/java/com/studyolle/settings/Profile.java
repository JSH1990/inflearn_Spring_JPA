package com.studyolle.settings;


import com.studyolle.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor //스프링에서 @ModelAttribute로 binding 받을 때 기본생성자에서 setter로 저장하기때문에, 이 애노테이션 필요하다
public class Profile {

    private String bio;
    private String url;
    private String occupation;
    private String location;

    public Profile(Account account){
        this.bio = account.getBio();
        this.url = account.getUrl();
        this.occupation = account.getOccupation();
        this.location = account.getLocation();
    }
}
