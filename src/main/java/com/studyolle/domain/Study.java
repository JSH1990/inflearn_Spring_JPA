package com.studyolle.domain;

import com.studyolle.account.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// N+1 문제:
// 1번(Study 조회) + N번(연관 엔티티 각각 조회) 쿼리가 발생하는 성능 문제

// 해결 방법:
// EntityGraph / JOIN FETCH로
// 필요한 연관 엔티티를 한 번의 JOIN 쿼리로 함께 조회


@NamedEntityGraph(name = "Study.withAll", attributeNodes = {
        @NamedAttributeNode("tags"),
        @NamedAttributeNode("zones"),
        @NamedAttributeNode("managers"),
        @NamedAttributeNode("members")
})
@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Study {

    @Id @GeneratedValue
    private Long id;

    @ManyToMany
    private Set<Account> managers = new HashSet<>();//관리자는 중복되면 안 되므로 Set

    @ManyToMany
    private Set<Account> members = new HashSet<>();

    @Column(unique = true)
    private String path;

    private String title;

    private String shortDescription;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String fullDescription; //전체 본문

    @Lob @Basic(fetch = FetchType.EAGER)
    private String image;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    private Set<Zone> zones = new HashSet<>();

    private LocalDateTime publishedDateTime; //스터디 공개시간

    private LocalDateTime closedDateTime; //스터디 종료시간

    private LocalDateTime recruitingUpdatedDateTime; //인원모집시간 제한

    private boolean recruiting; //인원모집중인지 여부

    private boolean published; //공개 여부

    private boolean closed; //종료 여부

    private boolean useBanner; //배너 사용 유무

    public void addManager(Account account){
        this.managers.add(account);
    }

    public boolean isJoinable(UserAccount userAccount) {
        Account account = userAccount.getAccount();
        return this.isPublished() && this.isRecruiting()
                && !this.members.contains(account) && !this.managers.contains(account);

    }

    public boolean isMember(UserAccount userAccount) {
        return this.members.contains(userAccount.getAccount());
    }

    public boolean isManager(UserAccount userAccount) {
        return this.managers.contains(userAccount.getAccount());
    }
}
