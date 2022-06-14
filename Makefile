rundb:
	docker run --name postgres -p 5431:5432 -e POSTGRES_PASSWORD=9en2w0oc -d postgres
buildapp:
	docker build -t bookstore .
runapp:
	docker run --name bookstore -p 8080:8080 bookstore
