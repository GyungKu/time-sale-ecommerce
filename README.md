# 타임 세일 커머스 API 서버 (Time-Sale Commerce API)

대규모 트래픽이 집중되는 선착순 타임 세일(한정판 판매) 환경을 가정하여, **동시성 제어**와 **시스템 결합도 완화**에 초점을 맞추어 개발한 백엔드 API 서버입니다.

## Tech Stack

- **Backend**: Java 21, Spring Boot 4.0.3, Spring Data JPA, Multi-Module
- **Database**: PostgreSQL, Redis
- **Message Broker & RPC**: Kafka, gRPC

## System Architecture

<img width="891" height="492" alt="아키텍처" src="https://github.com/user-attachments/assets/ea6d36ef-75c9-446d-bed2-3671659442ce" />

## 주요 시스템 특징 (Core Features)

- **멀티 모듈 아키텍처 설계:** 프로젝트 규모 확장을 고려하여 계층별(API, Domain, Application, Infrastructure)로 모듈을 분리하고 도메인 로직의 독립성을 확보했습니다.

## 기술적 의사결정 (Technical Decisions)

**1. 동시성 제어 환경 구축 (비관적 락 → Redis 분산 락)**

- **도입 배경:**
    - 타임 세일 시 재고 차감 동시성 이슈를 제어하기 위해 적용 초기에는 데이터 정합성이 확실한 DB 비관적 락(Pessimistic Lock)을 적용
    - DB 커넥션 점유 문제를 고려하여 인 메모리 기반의 Redis 분산 락으로 전환
- **이점 및 한계:**
    - 락 관리 주체를 분리해 DB 자원 부담을 줄였음
    - Redis 서버 자체가 단일 장애점(SPOF)이 될 수 있는 인프라적 한계

**2. 서버 간 내부 통신 최적화 (gRPC 도입)**

- **도입 배경:** 분리된 서버 간 내부 통신이 필요할 때 기존 REST 통신의 오버헤드를 줄이고 데이터 전송 효율을 높이기 위해 도입
- **이점 및 한계:**
    - 바이너리 직렬화로 파싱 속도를 높임
    - 텍스트 기반 API 툴(Postman 등)로 디버깅하기 까다롭고 초기 스펙 정의 비용이 발생

**3. 비동기 이벤트 처리 및 메시지 유실 방지 (Kafka + Outbox 패턴)**

- **도입 배경:**
    - 주문 트랜잭션과 부가 로직을 비동기로 분리하고자 Kafka를 도입
    - DB 저장과 메시지 발행 간의 이중 쓰기(Dual Write) 문제를 해결하기 위해 Outbox 패턴을 적용
- **이점 및 한계:**
    - 도메인 간 결합을 끊고 메시지의 최소 1회 전송(At-least-once delivery)을 보장
    - 중복 전송 가능성이 존재하여 Consumer 측에 멱등성 보장 로직이 필수
