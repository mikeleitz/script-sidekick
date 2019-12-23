#!/bin/zsh

# Load common variables for all deployment scripts.
. ./setup-env.sh

# Create the VPC
gcloud compute --project=$ENVIRONMENT_PROJECT_NAME networks delete $VPC_NAME
