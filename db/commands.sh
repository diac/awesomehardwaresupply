docker build -t postgresql_ahs ./
docker run -itd -p 5432:5432 -v ~/postgres_data:/var/lib/postgresql/data --name postgresql_ahs postgresql_ahs