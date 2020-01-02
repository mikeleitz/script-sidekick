#!/bin/zsh

PROJECT_NAME=side-script

# dev
ENVIRONMENT=dev

# prod
#ENVIRONMENT=prod

# Physical resources
REGION=us-central1
ZONE=us-central1-a

# Micro doesn't seem to accept any deployments for some reason.
# It creates error unschedulable pods.
#GKE_MACHINE_TYPE=f1-micro
GKE_MACHINE_TYPE=g1-small

# Networking Resources

###
# We have a /24 ip range for our whole dev VPC.  It's divided into two halves.  The first half is usable for
# non-kubernetes addresses.  The upper half is reserved for our kubernetes cluster/nodes/pods/services.
# Whole dev environment: 192.168.3.0/24
###

VPC_NAME=$PROJECT_NAME-vpc-$ENVIRONMENT
NON_GKE_SUBNET_NAME=$PROJECT_NAME-net-sn-$ENVIRONMENT
GKE_SUBNET_NAME=$PROJECT_NAME-gke-cl-net-sn-$ENVIRONMENT

# Dev
NON_GKE_SUBNET_IP_RANGE=10.10.12.0/24
# GKE_CLUSTER_SUBNET_IP_RANGE must account for all nodes, pods, services, etc.  Every
# instance with an ip address within the cluster.
GKE_CLUSTER_NODE_SUBNET_IP_RANGE=10.10.10.0/24
GKE_CLUSTER_POD_SUBNET_IP_RANGE=10.1.0.0/16
GKE_CLUSTER_SERVICE_SUBNET_IP_RANGE=10.10.11.0/24

# Prod
#NON_GKE_SUBNET_IP_RANGE=192.168.4.0/25
#GKE_SUBNET_IP_RANGE=192.168.4.128/25

# Compute Resources

# Kubernetes cluster
GKE_CLUSTER_NAME=$PROJECT_NAME-gke-cl-$ENVIRONMENT
GKE_CLUSTER_NODES=2

###
# With 8 pods per node we need 16 ip addresses for each node.  GKE allocates max pods
# times 2 for the ip range.  So for max 8 pods per node is 8*2=16 ip addresses which
# corresponds to /28 cidr block.
###

GKE_CLUSTER_MAX_PODS_PER_NODE=8



# Storage Resources
