#!/bin/bash
echo "Starting minikube"
minikube start --memory 8192 --vm-driver=docker
minikube addons enable ingress

echo "Launching k8s components"
cd k8s

echo "Launching ActiveMQ..."
cd activemq

kubectl create -f activemq-pv.yaml
kubectl create -f activemq-secret.yaml
kubectl create -f activemq-configmap.yaml
kubectl create -f activemq-deployment.yaml
kubectl create -f activemq-service.yaml
kubectl rollout status deploy/activemq

echo "Launch ActiveMQ completed"
cd ..

echo "Launching PostgreSQL..."
cd postgresql

kubectl create -f postgres-secret.yaml
kubectl create -f postgres-configmap.yaml
kubectl create -f postgres-pv.yaml
kubectl create -f postgres-deployment.yaml
kubectl create -f postgres-service.yaml
kubectl rollout status deploy/postgres

echo "Launch PostgreSQL completed"
cd ..

echo "Launching Liquibase..."
cd liquibase

kubectl create -f liquibase-configmap.yaml
kubectl create -f liquibase-secret.yaml
kubectl create -f liquibase-job.yaml
kubectl wait --for=condition=complete job/liquibase

echo "Launch Liquibase completed"
cd ..

echo "Launching modules"

cd movie
kubectl create -f movie-deployment.yaml
kubectl create -f movie-service.yaml
kubectl rollout status deploy/movie-deployment
cd ..

cd actor
kubectl create -f actor-deployment.yaml
kubectl create -f actor-service.yaml
kubectl rollout status deploy/actor-deployment
cd ..

cd director
kubectl create -f director-deployment.yaml
kubectl create -f director-service.yaml
kubectl rollout status deploy/director-deployment
cd ..

cd ui
kubectl create -f ui-deployment.yaml
kubectl create -f ui-service.yaml
kubectl rollout status deploy/ui-deployment

cd ..
kubectl create -f ingress.yaml

echo "Launch modules completed"
cd ..

echo "Adding .tsv files..."
kubectl get pods -l project=movie -o name | sed 's/pod\///' > abracadabra.txt

for f in $(<abracadabra.txt)
do
  echo "Adding ratings.tsv..."
	kubectl cp ./datasets/ratings.tsv $f:/datasets
	echo "Success!"

	echo "Adding people.tsv..."
	kubectl cp ./datasets/people.tsv $f:/datasets
	echo "Success!"

	echo "Adding movies.tsv..."
	kubectl cp ./datasets/movies.tsv $f:/datasets
	echo "Success!"

	echo "Adding roles.tsv..."
	kubectl cp ./datasets/roles.tsv $f:/datasets
	echo "Success!"
done

echo ".tsv files added"
rm abracadabra.txt