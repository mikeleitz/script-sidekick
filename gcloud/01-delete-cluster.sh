#!/bin/zsh

# Load project variables.
. ./setup-env.sh

gcloud container clusters delete --zone "${ZONE}" "${GKE_CLUSTER_NAME}"
