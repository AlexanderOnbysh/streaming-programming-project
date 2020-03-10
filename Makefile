PROJECT_NAME := "Streaming programming assignment"
BOLD := \033[1m
RESET := \033[0m

.DEFAULT: help


help:
	@echo "$(BOLD)$(PROJECT_NAME)$(RESET)"
	@echo "test message"

build:
	@echo "📦$(BOLD)Build twitter service$(RESET)"
	docker build -t twitter-service twitter-service/
	@echo "📦$(BOLD)Build stocks service$(RESET)"
	docker build -t stocks-service stocks-service/
	@echo "📦$(BOLD)Build enricher service$(RESET)"
	docker build -t combiner-service combiner-service/

install-kaf:
	curl https://raw.githubusercontent.com/birdayz/kaf/master/godownloader.sh | BINDIR=. bash && export PATH=$PATH:./

kafka-start:
	docker-compose up -d

kafka-stop:
	docker-compose down

kafka-clean:
	rm -rf .zk-single-kafka-single

kafka-create-topics:
	kafkacat -C -t tweets -b 127.0.0.1:9092
	kafkacat -C -t stocks -b 127.0.0.1:9092
	kafkacat -C -t combined -b 127.0.0.1:9092

