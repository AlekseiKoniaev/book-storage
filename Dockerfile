FROM openjdk:17-alpine
COPY target/book-storage-1.0.jar bookstorage.jar
COPY application-prod.yml .
COPY application-debug.yml .
ENV PORT 8080
ENV PROFILE prod
EXPOSE $PORT
CMD java -jar bookstorage.jar --spring.profiles.active=$PROFILE