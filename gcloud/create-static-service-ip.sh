#!/bin/zsh

. ./setup-env.sh

gcloud compute addresses create side-script-ui-ip --region "${REGION}"

gcloud compute addresses describe side-script-ui-ip --region "${REGION}"
