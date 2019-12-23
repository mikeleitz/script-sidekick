#!/bin/zsh

# First create the VPC then create the relevant subnets.
# The VPC is per project.  Since we have a project for production
# and a project for dev, we have two VPCs.

# Load common variables for all deployment scripts.
. ./setup-env.sh

# Create the VPC
gcloud compute --project=$ENVIRONMENT_PROJECT_NAME networks create $VPC_NAME --subnet-mode=custom
