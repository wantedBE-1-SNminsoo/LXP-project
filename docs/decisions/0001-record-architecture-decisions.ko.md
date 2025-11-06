# ADR 0001: 첫 회의 - LXP 프로젝트 초기 설계

날짜: 2025-11-03
상태: 승인됨

## 배경(Context)
LXP(Learning Experience Platform) 프로젝트 초기 설계 및 협업 가이드라인 구축을 위한 첫 회의를 진행했습니다. 팀원 간 일관된 개발 프로세스와 명확한 요구사항 정의가 필요했습니다.

## 결정(Decision)

### 1. 커밋 컨벤션
Conventional Commits 스펙을 따르는 커밋 메시지 규칙을 적용하기로 결정했습니다.
- 상세 내용: [Commit Convention](../commit/commit-convention.md)
- 템플릿 가이드 : [commit-template](../commit/git-commit-template-guide.md)

### 2. PR 프로세스
코드 리뷰 및 Pull Request 작성, 승인, 머지 프로세스를 정립했습니다.
- 최소 2명 이상의 리뷰어 승인 필요
- 상세 내용: [PR Process](../pull-request/pr-process.md)

### 3. 스크럼 주기
전체 2주 이내의 기간을 고려, 데일리 스크럼만 진행하는 방향으로 결정했습니다.
- 상세 내용: [Scrum Guide](../scrum/scrum-guide.md)


## 영향(Consequences)

### 긍정적 영향
- 팀원 간 일관된 코드 스타일과 커밋 히스토리 유지
- 명확한 코드 리뷰 프로세스로 코드 품질 향상
- 체계적인 스크럼 프로세스로 프로젝트 진행 상황 투명화

### 부정적 영향 (및 완화 방안)
- 초기 학습 곡선 존재 → 문서화 및 예시 제공으로 완화
- 프로세스 준수로 인한 개발 속도 일시적 감소 → 장기적으로는 효율성 증가
- 변경 사항 발생 시 여러 문서 업데이트 필요 → 정기적인 문서 리뷰로 관리

## 검토한 대안(Alternatives Considered)

### 도메인 모델
- **고려한 대안**: MVC 패턴
- **선택 이유**: DDD가 각자의 개발 도메인 영역을 나누고 협업하기 적합하다고 판단

## 참고(References)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Scrum Guide](https://scrumguides.org/)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)
- [Google Engineering Practices](https://google.github.io/eng-practices/) 
