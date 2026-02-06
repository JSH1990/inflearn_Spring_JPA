package com.studyolle.study;

import com.studyolle.domain.Study;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
public interface StudyRepository extends JpaRepository<Study, Long> {

    boolean existsByPath(String path);

    // EAGER : 엔티티 조회 시 연관 엔티티 즉시 로딩 (쿼리 제어 불가, 위험)
    // LAZY  : 연관 엔티티 접근 시 로딩 (기본값 권장, N+1은 EntityGraph로 해결)
    // path로 Study를 조회하면서
    // Study.withAll 엔티티 그래프를 적용하여
    // 연관 엔티티들을 함께 로딩하되, 기존 EAGER 설정도 유지
    @EntityGraph(value = "Study.withAll", type = EntityGraph.EntityGraphType.LOAD)
    Study findByPath(String path);

    @EntityGraph(value = "Study.withTagsAndManagers", type = EntityGraph.EntityGraphType.FETCH)
    Study findAccountWithTagsByPath(String path);

    @EntityGraph(value = "Study.withZonesAndManagers", type = EntityGraph.EntityGraphType.FETCH)
    Study findAccountWithZonesByPath(String path);
}
