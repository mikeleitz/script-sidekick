#!/bin/zsh

. ./setup-env.sh

gcloud compute addresses create side-script-service-ip --region "${REGION}"

gcloud compute addresses describe side-script-service-ip --region "${REGION}"
