Schema:

[ HTTP/GRPC Client ]
        │
        ▼
[ API Gateway ]  ───(Rate Limiting & Token Bucket)
        │
        ▼
[ Payment Gateway Service ] ───(Virtual Thread per Request)
        │
        ├──► [ Idempotency Layer (Redis) ]  ────(Prevents Double Charge)
        │
        ├──► [ Anti-Fraud Service ] ────────────(Structured Concurrency: parallel checks < 10ms)
        │
        ▼
[ Ledger / Account Service ] ───(Strong Consistency - PostgreSQL / YugabyteDB)
        │
        ▼
[ Transactional Outbox Table ]
        │
        ▼ (Debezium / CDC Pipeline)
[ Kafka Event Bus ]
        │
        ├──────────────────────────────┐
        ▼                              ▼
[ Audit & Analytics Engine ]   [ Notification Service ]
 (ScyllaDB / ClickHouse)        (WebSockets / Push)
