docker stop reward
docker rm reward
docker rmi -f reward:latest
docker build ./.. --file=dockerfile --tag=reward