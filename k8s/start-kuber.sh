kubectl apply -f namespace.yml
kubectl apply -f config-map.yml
kubectl apply -f secret.yml
kubectl apply -f zookeeper.yml
kubectl apply -f kafka.yml

kubectl apply -f ./crypto-service/crypto-db.yml
kubectl apply -f ./crypto-service/crypto-service.yml
kubectl apply -f ./telegram-service/telegram-service.yml

kubectl config set-context --current --namespace=crypto-info