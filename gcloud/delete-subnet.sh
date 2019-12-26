#!/bin/zsh

# Load common variables for all deployment scripts.
. ./setup-env.sh

#gcloud compute --project="${PROJECT_NAME}" networks subnets delete "${GKE_SUBNET_NAME}" --network="${VPC_NAME}" --region="${REGION}"
