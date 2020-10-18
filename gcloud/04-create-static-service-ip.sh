#!/bin/zsh

. ./setup-env.sh

gcloud compute addresses create lickety-script-ui-ip --region "${REGION}"

gcloud compute addresses describe lickety-script-ui-ip --region "${REGION}"
