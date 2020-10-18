#!/bin/zsh

. ./setup-env.sh

gcloud compute addresses create lickety-script-service-ip --region "${REGION}"

gcloud compute addresses describe lickety-script-service-ip --region "${REGION}"
