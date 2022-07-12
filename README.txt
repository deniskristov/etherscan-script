
AWS:

aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 264368363702.dkr.ecr.eu-central-1.amazonaws.com
docker build -t etherscan .
docker tag etherscan:latest 264368363702.dkr.ecr.eu-central-1.amazonaws.com/etherscan:latest
docker push 264368363702.dkr.ecr.eu-central-1.amazonaws.com/etherscan:latest

LOCAL INFO:

docker images
docker network ls
docker inspect <container_id>

LOCAL RUN:

 docker build -t etherscan .

docker network create etherscan-net
docker run --name selenium --net etherscan-net -p 4444:4444 --shm-size="2g" -e SE_NODE_MAX_SESSIONS=10 etherscan-script_selenium
docker start selenium
docker run --net etherscan-net --name=etherscan etherscan
docker start etherscan

docker logs etherscan


###################################
docker network create etherscan-net

docker pull mysql
docker run --name mysql --net etherscan-net -e MYSQL_ROOT_PASSWORD=siden123 -d mysql
docker exec -it mysql bash
mysql -u root -p

docker pull selenium/standalone-chrome
docker run --name selenium --net etherscan-net -p 4444:4444 --shm-size="2g" -e SE_NODE_MAX_SESSIONS=15 selenium/standalone-chrome

docker build -t etherscan .
docker run --name etherscan --net etherscan-net -p 8080:8080 -d etherscan
docker logs -f --tail 20 etherscan

docker pull wernight/ngrok
docker run --rm -it --net etherscan-net --link etherscan wernight/ngrok ngrok http etherscan:8080 --authtoken 1yP79Q8SdY3kjaugAFPEHL2JTr7_3odegVzPdc79A6fKP87PP



