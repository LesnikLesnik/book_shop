#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    SELECT 'CREATE DATABASE account_service_db'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'account_service_db')\gexec
    SELECT 'CREATE DATABASE bill_service_db'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'bill_service_db')\gexec
    SELECT 'CREATE DATABASE book_service_db'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'book_service_db')\gexec
EOSQL