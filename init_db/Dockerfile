FROM postgres:16.2-alpine

COPY init-databases.sh /docker-entrypoint-initdb.d/

ENV POSTGRES_USER=qwerty
ENV POSTGRES_PASSWORD=qwerty
ENV POSTGRES_DB=postgres

EXPOSE 5432
