# 인기 게시글 / 인기 가게 설계 (미구현)

> 트래픽이 많은 환경에서 1시간 단위 실시간 랭킹 100개를 효율적으로 제공하기 위한 설계 노트.
> 코드는 아직 구현되지 않았으며, 본 문서는 학습 의도와 접근 방향을 기록한다.

## 요구사항
- 대용량 트래픽 처리
- 실시간(1시간 기준) 인기 100개
- 인기 기준: 1시간 동안의 조회수 증가량

## 데이터 구조 — Redis Sorted Set
| 키 | 의미 | 타입 |
|----|------|------|
| `board:time:view` | 1분 단위 조회수 누적 캐시 (`<key=postId, val=조회수>`) | Sorted Set |
| `board:topview` | 계산된 상위 100개 결과 (1시간 동안 그대로 노출) | List/Sorted Set |

## 처리 흐름

### 1. 조회 시
1. 게시글 ID에 해당하는 캐싱 데이터를 가져온다.
2. `board:time:view` 키에 조회수 +1 한다.
3. 게시글 본 데이터(상세)는 별도 캐싱 또는 DB.

### 2. 인기 게시글 조회 (1시간 배치)
1. 1시간마다 배치로 갱신.
2. `board:time:view`의 sorted set에서 상위 100개를 추출.
3. 추출한 100개 ID를 DB에서 조회 → 결과를 `board:topview`에 캐싱.
4. 사용자는 1시간 동안 `board:topview`만 읽어가므로 계산 비용 0.
5. 갱신 후 누적 cache는 cleanup.

### 3. 동시성 / 트랜잭션 이슈
- **타임뷰 삭제 시점에 중간 조회가 들어오면?**
  - 멀티 익스큐트(`MULTI/EXEC`) 또는 Lua script로 원자성 보장.
  - 파이프라인은 atomicity 보장 안 됨, 단순 round-trip 최적화이므로 부적합.

### 4. Redis 복구 전략
- AOF: 각 명령을 append-only로 영속화 → 데이터 유실 적음, 성능 영향 있음
- RDB: 주기적 스냅샷 → 빠르지만 마지막 스냅샷 ~ 장애 시점까지 데이터 유실 가능
- 권장: AOF + RDB 동시 사용

### 5. 어뷰징 처리
- 조회 이벤트 자체를 별도 토픽으로 발행 → 사용자 단위로 rate-limit 또는 dedup 후 카운트 반영.

## 향후 작업 체크리스트
- [ ] `board:time:view` 키에 조회수 누적하는 ViewCountService
- [ ] 1시간 배치 (`@Scheduled` + ShedLock으로 분산 락)
- [ ] `board:topview` 캐시 응답 컨트롤러
- [ ] Lua script로 atomic swap
- [ ] SSE를 활용한 사장님 알림 (근방 가게 조회 급증 알림)
