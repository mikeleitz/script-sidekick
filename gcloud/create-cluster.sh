#!/bin/zsh

###
# TODO: Create VPC and routes/firewall
# TODO: Create docker registry in google
# TODO: Push docker image
# TODO: Add IAM and roles
# TODO: Add to org/folders
# TODO: Lock down k8s cluster from public access: https://github.com/GoogleCloudPlatform/gke-private-cluster-demo
###

###
# This script will run and create:
# 1. A VPC for the project.  There's a VPC for dev and a VPC for prod.
# 2. A single subnet for the VPC.
# 3. A Kubernetes cluster.
###

# Load common variables for all deployment scripts.
. ./setup-env.sh

#echo $PROJECT_NAME
#echo $CLUSTER_NAME
#echo $SUBNET_NAME

###
# Values below and their purpose
# subnetwork         : use existing subnet for this cluster.  This is the primary ip
#                      range and is used for nodes.
# cluster-ipv4-cidr  : Secondary ip range for pods.
# services-ipv4-cidr : Secondary ip range for services.
###

gcloud container clusters create "${GKE_CLUSTER_NAME}" \
  --enable-ip-alias \
  --machine-type="${GKE_MACHINE_TYPE}" \
  --num-nodes="${GKE_CLUSTER_NODES}" \
  --max-pods-per-node="${GKE_CLUSTER_MAX_PODS_PER_NODE}" \
  --create-subnetwork=name="${GKE_SUBNET_NAME}",range="${GKE_CLUSTER_NODE_SUBNET_IP_RANGE}" \
  --cluster-ipv4-cidr="${GKE_CLUSTER_POD_SUBNET_IP_RANGE}" \
  --services-ipv4-cidr="${GKE_CLUSTER_SERVICE_SUBNET_IP_RANGE}" \
  --zone="${ZONE}" \
  --enable-autoupgrade  \
  --enable-autorepair \
  --metadata disable-legacy-endpoints=true

