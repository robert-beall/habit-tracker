index: 
	@echo "app-up: Build and start services"
	@echo "app-start: Start services"
	@echo "app-stop: Stop services"
	@echo "app-down: Stop and teardown services"

app-up: 
	@cd src; docker compose up -d --build

app-start:
	@cd src; docker compose start 

app-stop:
	@cd src; docker compose stop

app-down: 
	@cd src; docker compose down

free: 
	@docker system prune -fa --volumes