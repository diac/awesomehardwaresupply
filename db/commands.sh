docker build -t postgresql ./
docker run -itd -p 5432:5432 -v ~/postgres_data:/var/lib/postgresql/data --name postgres postgresql_ahs