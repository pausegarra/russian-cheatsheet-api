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

build: ## Build the application.
	./mvnw clean package -DskipTests

build-native: ## Build the application natively.
	./mvnw clean package -Pnative -DskipTests

stop-db: ## Stop the database.
	docker-compose down

remove-db: ## Remove the database and it's volumes.
	docker-compose down -v

CURRENT_VERSION := $(shell ./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout)
upgrade-major-version: ## Upgrade major version of the POM
	@$(eval NEW_VERSION := $(shell echo $(CURRENT_VERSION) | awk -F. '{$$1+=1; $$2=0; $$3=0; print $$1"."$$2"."$$3}'))
	@echo Setting new version to $(NEW_VERSION)
	@./mvnw versions:set -DnewVersion=$(NEW_VERSION) -DgenerateBackupPoms=false
	git add pom.xml
	git commit -m "chore: upgrade to $(NEW_VERSION)"
	git push

upgrade-minor-version: ## Upgrade minor version of the POM
	@$(eval NEW_VERSION := $(shell echo $(CURRENT_VERSION) | awk -F. '{$$2+=1; $$3=0; print $$1"."$$2"."$$3}'))
	@echo Setting new version to $(NEW_VERSION)
	@./mvnw versions:set -DnewVersion=$(NEW_VERSION) -DgenerateBackupPoms=false
	git add pom.xml
	git commit -m "chore: upgrade to $(NEW_VERSION)"
	git push

upgrade-patch-version: ## Upgrade patch version of the POM
	@$(eval NEW_VERSION := $(shell echo $(CURRENT_VERSION) | awk -F. '{$$3+=1; print $$1"."$$2"."$$3}'))
	@echo Setting new version to $(NEW_VERSION)
	@./mvnw versions:set -DnewVersion=$(NEW_VERSION) -DgenerateBackupPoms=false
	git add pom.xml
	git commit -m "chore: upgrade to $(NEW_VERSION)"
	git push

tag: ## Tag the current version
	git tag $(CURRENT_VERSION)
	git push origin $(CURRENT_VERSION)

.PHONY: start-db stop-db remove-db help build build-native dev run test
