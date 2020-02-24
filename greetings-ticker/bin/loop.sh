#!/bin/bash

set -eu 
set -o pipefail 

trap '{ echo "" ; exit 1; }' INT

cd $PROJECT_HOME/earth
eval $(minikube -p earth docker-env)
export KUBECONFIG=$PROJECT_HOME/earth/.kube/config

LINGUA_GREETER_INGRESS_URL="$(kubectl get -n demo ingress lingua-greeter -o jsonpath='{.spec.rules[0].host}')"
echo "Using server $LINGUA_GREETER_INGRESS_URL"


while true
do 
  http -v --stream -f $LINGUA_GREETER_INGRESS_URL/greetings/stream 2>/dev/null \
    | while read greeting; do echo "$greeting"; done;
done
