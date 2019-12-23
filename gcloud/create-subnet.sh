#!/bin/zsh

# First create the VPC then create the relevant subnets.
# The VPC is per project.  Since we have a project for production
# and a project for dev, we have two VPCs.

# Load common variables for all deployment scripts.
. ./setup-env.sh

gcloud compute --project=$ENVIRONMENT_PROJECT_NAME networks subnets create $ENVIRONMENT_SUBNET_NAME --network=$VPC_NAME --region=$ENVIRONMENT_REGION --range=$ENVIRONMENT_IP_RANGE

# Need to open firewall otherwise no traffic will enter subnet.

# gcloud compute firewall-rules create <FIREWALL_NAME> --network leitz-vpc --allow tcp,udp,icmp --source-ranges <IP_RANGE>
# gcloud compute firewall-rules create <FIREWALL_NAME> --network leitz-vpc --allow tcp:22,tcp:3389,icmp
