#!/bin/zsh
###
# The first step in creating the gcloud environment: the VPC.
# The VPC is per project.  Since we have a project for production
# and a project for dev, we have two VPCs.
###

# Load common variables for all deployment scripts.
. ./setup-env.sh

# Create the VPC
gcloud compute --project="${PROJECT_NAME}"-"${ENVIRONMENT}" networks create "${VPC_NAME}" --subnet-mode=custom
