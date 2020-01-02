#!/bin/zsh

# Load project variables.
. ./setup-env.sh

gcloud container clusters delete "${GKE_CLUSTER_NAME}"
