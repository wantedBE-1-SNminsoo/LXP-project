# Git Commit Template 사용 가이드

## 개요
이 문서는 프로젝트의 커밋 컨벤션을 자동으로 따를 수 있도록 Git 커밋 템플릿을 설정하고 사용하는 방법을 안내합니다.

## 템플릿 파일
- 파일 위치: `docs/commit/.gitmessage`
- 기준 문서: `docs/commit/commit-convention.md`

## 설정 방법

### 로컬 설정 (현재 프로젝트에만 적용)
```bash
git config commit.template docs/commit/.gitmessage
```

이 명령어는 현재 프로젝트의 `.git/config` 파일에 템플릿 경로를 추가합니다.

### 설정 확인
```bash
git config commit.template
```

설정이 완료되면 `.gitmessage` 경로가 출력됩니다.

## 사용 방법

### 1. 템플릿을 사용한 커밋
```bash
git add .
git commit
```

`-m` 옵션 없이 `git commit`을 실행하면 설정된 에디터가 열리며 `.gitmessage` 템플릿이 자동으로 로드됩니다.

### 2. 템플릿 작성 예시

에디터에서 다음과 같이 작성:

```
feat(course): 코스 목록 조회 API 구현

페이지네이션과 필터링 기능을 포함한
코스 목록 조회 API를 추가

Closes #45
```

- `#`으로 시작하는 주석 줄은 자동으로 제외됩니다
- 필요한 type과 scope를 선택하고 나머지는 삭제합니다
- subject는 50자 이내로 작성합니다
- body와 footer는 필요한 경우에만 작성합니다

### 3. 빠른 커밋 (템플릿 생략)
```bash
git commit -m "fix(payment): 결제 취소 버그 수정"
```

`-m` 옵션을 사용하면 템플릿 없이 즉시 커밋할 수 있습니다.

## Commit Type

| Type | 설명 | 예시 |
|------|------|------|
| `feat` | 새로운 기능 추가 | feat(course): 코스 생성 API 추가 |
| `fix` | 버그 수정 | fix(enrollment): 중복 등록 방지 로직 수정 |
| `docs` | 문서 변경 | docs: API 문서 업데이트 |
| `style` | 코드 포맷팅, 세미콜론 누락 등 | style: 코드 포맷팅 적용 |
| `refactor` | 코드 리팩토링 | refactor(payment): 결제 로직 분리 |
| `test` | 테스트 코드 추가/수정 | test(user): 회원가입 테스트 추가 |
| `chore` | 빌드, 설정 파일 수정 | chore: 의존성 업데이트 |
| `perf` | 성능 개선 | perf(course): 코스 목록 쿼리 최적화 |

## 자주 사용하는 Scope (LXP 도메인)

- `user`: 사용자 관련 (회원가입, 로그인, 프로필)
- `course`: 코스 관련
- `lecture`: 강의 관련
- `enrollment`: 수강 등록 관련
- `payment`: 결제 관련
- `admin`: 관리자 기능
- `db`: 데이터베이스 관련
- `config`: 설정 관련

## 작성 규칙

1. **subject는 명령문으로 작성**
   - ❌ "추가한다", "추가했다"
   - ✅ "추가"

2. **subject 끝에 마침표 금지**
   - ❌ `feat(course): 코스 생성 API 추가.`
   - ✅ `feat(course): 코스 생성 API 추가`

3. **영문 커밋은 첫 글자 소문자**
   - ❌ `feat(course): Add course creation API`
   - ✅ `feat(course): add course creation API`

4. **한 커밋에는 한 가지 변경사항만**
   - 여러 변경사항은 별도 커밋으로 분리

5. **body는 "무엇을", "왜" 변경했는지 작성**
   - "어떻게"보다 "왜"에 집중

## 팀원 온보딩

새로운 팀원이 합류할 때:

1. 프로젝트를 클론합니다
   ```bash
   git clone <repository-url>
   cd LXP-project
   ```

2. 커밋 템플릿을 설정합니다
   ```bash
   git config commit.template .gitmessage
   ```

3. 커밋 컨벤션 문서를 읽습니다
   - `docs/commit-convention.md` 참고

4. 첫 커밋을 작성합니다
   ```bash
   git commit  # 템플릿이 자동으로 로드됨
   ```

## 문제 해결

### 템플릿이 로드되지 않는 경우
1. 설정을 확인합니다
   ```bash
   git config commit.template
   ```

2. 절대 경로로 다시 설정합니다
   ```bash
   git config commit.template {Project_Absolute_Path}/docs/commit/.gitmessage
   ```

## 참고 자료
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Angular Commit Guidelines](https://github.com/angular/angular/blob/main/CONTRIBUTING.md#commit)
- 프로젝트 커밋 컨벤션: `docs/commit-convention.md`

