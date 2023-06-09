### Secrets ###
# Authentication DB Secret
kubectl apply -f authentication-db-secret.yml

# JWT Secret
kubectl apply -f jwt-secret.yml

# Knowledgebase Secret
kubectl apply -f knowledgebase-db-secret.yml

# Price Schedule Secret
kubectl apply -f price-schedule-db-secret.yml

### Config Maps ###
# Authentication DB Config Map
kubectl apply -f authentication-db-configmap.yml

# Gateway Config Map
kubectl apply -f gateway-configmap.yml

# Knowledgebase Config Map
kubectl apply -f knowledgebase-db-configmap.yml

# Price Schedule Config Map
kubectl apply -f price-schedule-db-configmap.yml

### Deployments ###
# Authentication Deployment
kubectl apply -f authentication-deployment.yml

# Gateway Deployment
kubectl apply -f gateway-deployment.yml

# Knowledgebase Deployment
kubectl apply -f knowledgebase-deployment.yml

# Price Schedule Deployment
kubectl apply -f price-schedule-deployment.yml