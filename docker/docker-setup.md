docker volume create  ibmmq-server-volume

docker network create --driver=bridge \
    --subnet=192.168.20.0/24 \
    --gateway=192.168.20.1 \
    ibmmq-network