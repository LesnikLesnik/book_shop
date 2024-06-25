#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE DATABASE account_service_db;
  CREATE DATABASE bill_service_db;
  CREATE DATABASE book_service_db;
EOSQL
