services=$(docker compose config --services)

tolog=$services
for e in "${exclude[@]}"; do
    tolog=${tolog//$e/}
done

docker-compose logs -f $tolog