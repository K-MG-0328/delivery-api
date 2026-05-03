# 포인트 시스템 설계 (미구현)

> 적립/사용/취소/만료 상태를 가진 포인트 기능과, 만료기한 기반 FIFO 차감 알고리즘에 대한 설계 노트.
> 코드는 아직 구현되지 않았으며, 본 문서는 학습 의도와 접근 방향을 기록한다.

## 요구사항
- 포인트 상태: `ADDED`(적립) / `USED`(사용) / `CANCELED`(취소) / `EXPIRED`(만료)
- 포인트 만료기한 존재 (예: 적립 후 N일)
- 사용 시 만료가 빠른 것부터 차감 (FIFO by expiry)

## 데이터 모델

### `users` 테이블 (확장)
- `current_point_balance`: 현재 누적 포인트 (모든 적립 - 사용 - 만료)

### `point` 테이블
| 컬럼 | 설명 |
|------|------|
| point_id | PK |
| user_id | FK |
| order_id | FK (선택, 적립을 발생시킨 주문) |
| amount | 적립/사용 금액 |
| status | ADDED / USED / CANCELED / EXPIRED |
| created_at | 적립 시점 |
| expired_at | 만료 시점 |
| remaining | 남은 포인트 (적립 row에서만 0~amount) |

### `point_history` 테이블
| 컬럼 | 설명 |
|------|------|
| history_id | PK |
| point_id | FK (point.point_id, 어떤 적립건에서 차감했는지) |
| used_at | 사용 일자 |
| used_amount | 사용 금액 |

## 시나리오 예시

### 적립
```
point_id=1, amount=100, status=ADDED, expired=4/17, created=3/02, remaining=100
point_id=2, amount=100, status=ADDED, expired=4/17, created=3/02, remaining=100
```

### 사용 (180원, 03/07)
1. 적립 row 중 status=ADDED, expired_at > now()인 것을 created_at 오름차순으로 조회.
2. `useVal = 180`
3. 반복:
   - point#1 (remaining 100) → 100 차감, remaining=0, status=USED
   - point#2 (remaining 100) → 80 차감, remaining=20, status는 ADDED 유지 (잔액 남음)
   - useVal=0이면 종료
4. 새 사용 row 생성: `point#3, amount=180, status=USED, expired=null, created=3/07, remaining=0`
5. `point_history` insert: `(point#1, 3/07, 100)`, `(point#2, 3/07, 80)`

### 만료 일정 (4/17)
- expired_at <= now()인 ADDED 상태 row → status=EXPIRED 일괄 변경
- 사용자 `current_point_balance`에서 차감

## 핵심 알고리즘 (의사 코드)

```java
// 사용할 포인트 row 추출
List<Point> available = pointRepo.findActiveByExpiryAsc(userId, now);

int useVal = requestedAmount;
for (Point p : available) {
    if (useVal <= 0) break;

    int deduct = Math.min(p.getRemaining(), useVal);
    p.setRemaining(p.getRemaining() - deduct);
    if (p.getRemaining() == 0) {
        p.setStatus(USED);
    }
    useVal -= deduct;

    history.insert(p.getId(), now, deduct);
}

if (useVal > 0) {
    throw new InsufficientPointException();
}
```

## 향후 작업 체크리스트
- [ ] Point 도메인 추가 (헥사고날, cart 패턴 따라)
- [ ] `PointService.use(userId, amount)` — 동시성 제어 필요 (사용자 단위 분산 락)
- [ ] 만료 배치 — `@Scheduled` + ShedLock
- [ ] 주문 결제 흐름에 포인트 사용 통합 (`OrderPaidEvent` 직전/직후)
- [ ] 주문 취소 시 포인트 환불 (`status=CANCELED` 처리)
