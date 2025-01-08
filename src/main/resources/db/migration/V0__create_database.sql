SELECT 'CREATE DATABASE dudemy'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'dudemy')
