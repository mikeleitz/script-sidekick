#!/bin/zsh

# Load common variables for all deployment scripts.
. ./setup-env.sh

# Create the VPC
gcloud compute --project="${PROJECT_NAME}"-"${ENVIRONMENT}" networks delete "${VPC_NAME}"
