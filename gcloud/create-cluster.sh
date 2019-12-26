#!/bin/zsh

####
# This script will:
# 1. Join the VPC named $VPC_NAME
# 2. Create a subnet for the nodes named $GKE_CLUSTER_NODE_SUBNET_IP_RANGE.
# 3. Create a subnet for the pods named $GKE_CLUSTER_POD_SUBNET_IP_RANGE.
# 4. Create a subnet for the services named $GKE_CLUSTER_SERVICE_SUBNET_IP_RANGE.
# 5. Create the Kubernetes cluster.
####


# Load common variables for all deployment scripts.
. ./setup-env.sh

#echo $PROJECT_NAME
#echo $CLUSTER_NAME
#echo $SUBNET_NAME

###
# Values below and their purpose
# network            : The VPC that this cluster will reside in.
# cluster-ipv4-cidr  : Secondary ip range for pods.
# services-ipv4-cidr : Secondary ip range for services.
###

gcloud container clusters create "${GKE_CLUSTER_NAME}" \
  --enable-ip-alias \
  --machine-type="${GKE_MACHINE_TYPE}" \
  --num-nodes="${GKE_CLUSTER_NODES}" \
  --network "${VPC_NAME}" \
  --max-pods-per-node="${GKE_CLUSTER_MAX_PODS_PER_NODE}" \
  --create-subnetwork=name="${GKE_SUBNET_NAME}",range="${GKE_CLUSTER_NODE_SUBNET_IP_RANGE}" \
  --cluster-ipv4-cidr="${GKE_CLUSTER_POD_SUBNET_IP_RANGE}" \
  --services-ipv4-cidr="${GKE_CLUSTER_SERVICE_SUBNET_IP_RANGE}" \
  --zone="${ZONE}" \
  --enable-autoupgrade  \
  --enable-autorepair \
  --metadata disable-legacy-endpoints=true

