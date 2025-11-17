package com.studyolle.settings;


import com.studyolle.domain.Account;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor //스프링에서 @ModelAttribute로 binding 받을 때 기본생성자에서 setter로 저장하기때문에, 이 애노테이션 필요하다
public class Profile {

    @Length(max = 35)
    private String bio;

    @Length(max = 50)
    private String url;

    @Length(max = 50)
    private String occupation;

    @Length(max = 50)
    private String location;

    private String profileImage;

    public Profile(Account account){
        this.bio = account.getBio();
        this.url = account.getUrl();
        this.occupation = account.getOccupation();
        this.location = account.getLocation();
        this.profileImage = account.getProfileImage();
    }
}
