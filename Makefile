PROJECT_NAME := "Streaming programming assignment"
BOLD := \033[1m
RESET := \033[0m

.DEFAULT: help


help:
	@echo "$(BOLD)$(PROJECT_NAME)$(RESET)"
	@echo "test message"

kafka-start:
	docker-compose up -d

kafka-stop:
	docker-compose down

kafka-clean:
	rm -rf .zk-single-kafka-single

kafka-list-topics:
	kafkacat -b 127.0.0.1:9092 -L

kafka-create-topics:
	kafkacat -C -t tweets -b 127.0.0.1:9092
	kafkacat -C -t stocks -2 -b 127.0.0.1:9092
	kafkacat -C -t combined -b 127.0.0.1:9092
	kafkacat -C -t ml-out -b 127.0.0.1:9092

