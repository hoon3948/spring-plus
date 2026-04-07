# 📋 Spring Plus - 일정 관리 애플리케이션

> Spring Boot 기반 일정 관리 API 서버

---

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.3.3 |
| ORM | Spring Data JPA, QueryDSL |
| Security | Spring Security, JWT |
| DB (local) | H2 (In-Memory) |
| DB (prod) | MySQL 8.0 |
| Build | Gradle |

---

## 📁 프로젝트 구조

```
src/main/java/org/example/expert
├── aop/                        # AOP (관리자 접근 로깅)
├── client/                     # 외부 API 클라이언트 (날씨)
├── config/                     # 설정 클래스 (JWT, Security, QueryDSL 등)
│   └── spring/
│       └── SecurityConfig.java
├── domain/
│   ├── auth/                   # 회원가입, 로그인
│   ├── comment/                # 댓글
│   ├── common/                 # 공통 (AuthUser, Timestamped 등)
│   ├── manager/                # 담당자, 담당자 로그
│   ├── todo/                   # 일정
│   └── user/                   # 유저
```

---

## 🔑 환경 변수 설정

프로젝트 루트에 `.env` 파일 생성 후 아래 값 설정.

```env
SPRING_PROFILES_ACTIVE=local
JWT_SECRET=your_base64_encoded_secret_key
```

---

## 🚀 실행 방법

```bash
# 빌드
./gradlew build

# 실행
./gradlew bootRun
```

로컬 실행 후 H2 콘솔: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:expert`
- Username: `sa`
- Password: (없음)

---

## 📌 API 명세

### Auth
| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| POST | `/api/auth/signup` | 회원가입 | ❌ |
| POST | `/api/auth/signin` | 로그인 | ❌ |

### User
| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| GET | `/api/users/{userId}` | 유저 단건 조회 | ✅ |
| PUT | `/api/users` | 비밀번호 변경 | ✅ |

### Admin
| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| PATCH | `/api/admin/users/{userId}` | 유저 권한 변경 | ✅ ADMIN |

### Todo
| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| POST | `/api/todos` | 일정 생성 | ✅ |
| GET | `/api/todos` | 일정 목록 조회 | ✅ |
| GET | `/api/todos/{todoId}` | 일정 단건 조회 | ✅ |
| GET | `/api/todos/search` | 일정 검색 (QueryDSL) | ✅ |

### Manager
| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| POST | `/api/todos/{todoId}/managers` | 담당자 등록 | ✅ |
| GET | `/api/todos/{todoId}/managers` | 담당자 목록 조회 | ✅ |
| DELETE | `/api/todos/{todoId}/managers/{managerId}` | 담당자 삭제 | ✅ |

### Comment
| Method | URL | 설명 | 인증 |
|--------|-----|------|------|
| POST | `/api/todos/{todoId}/comments` | 댓글 작성 | ✅ |
| GET | `/api/todos/{todoId}/comments` | 댓글 목록 조회 | ✅ |

---

## ✅ 구현 내용

### Level 1
- [x] `@Transactional` readOnly 오류 수정
- [x] JWT에 nickname 클레임 추가
- [x] JPA QueryDSL로 weather + 기간 검색 구현
- [x] 컨트롤러 테스트 코드 수정
- [x] AOP Pointcut 및 어드바이스 타입 수정

### Level 2
- [x] JPA Cascade로 Manager 자동 등록
- [x] N+1 문제 해결 (JOIN FETCH)
- [x] QueryDSL로 `findByIdWithUser` 변환
- [x] Spring Security 전환 (JWT 기반 인증 유지)

### Level 3 (도전)
- [x] QueryDSL Projections 활용 일정 검색 API
- [x] `REQUIRES_NEW` 전파 옵션으로 매니저 등록 로그 독립 처리

---

## 🔐 인증 방식

JWT Bearer Token 방식 사용.

```
Authorization: Bearer {token}
```

- 토큰 유효시간: 60분
- 권한: `ROLE_USER`, `ROLE_ADMIN`

---

## 📝 초기 데이터

로컬 실행 시 `data.sql`로 아래 계정 자동 생성.

| 이메일 | 비밀번호 | 권한 |
|--------|---------|------|
| admin@example.com | Admin1234! | ADMIN |
| user@example.com | Admin1234! | USER |