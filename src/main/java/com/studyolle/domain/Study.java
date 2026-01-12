package com.studyolle.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Study {

    @Id @GeneratedValue
    private Long id;

    @ManyToMany
    private Set<Account> manager = new HashSet<>();//관리자는 중복되면 안 되므로 Set

    @ManyToMany
    private Set<Account> member = new HashSet<>();

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
        this.manager.add(account);
    }
}
