# rabbitmq-consumer

## Install local docker registry
````
docker run -d -p 5000:5000 --restart=always --name registry registry:2
````

## Build image and push to the local docker registry
````
mvn clean install jib:dockerBuild && docker tag rabbitmq-consumer localhost:5000/rabbitmq-consumer && docker push localhost:5000/rabbitmq-consumer
````

## Run image
````
docker run -it --rm localhost:5000/rabbitmq-consumer
````