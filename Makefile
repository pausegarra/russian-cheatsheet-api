help: ## Show this help.
	@awk 'BEGIN {FS = ":.*?## "} /[a-zA-Z_-]+:.*?## / {sub("\\\\n",sprintf("\n%22c"," "), $$2);printf " \033[36m%-20s\033[0m  %s\n", $$1, $$2}' $(MAKEFILE_LIST)

dev: ## Run the application in dev mode.
	./mvnw clean quarkus:dev --debug -DskipTests

run: ## Run the application.
	./mvnw clean package quarkus:run -DskipTests

test: ## Run the tests.
	./mvnw clean test -Dquarkus.profile=test

start-db: ## Start the database.
	docker-compose up -d

stop-db: ## Stop the database.
	docker-compose down

remove-db: ## Remove the database and it's volumes.
	docker-compose down -v

.PHONY: start-db stop-db remove-db help