#!/bin/sh

awslocal sqs create-queue --queue-name image-upload-events
awslocal s3api create-bucket --bucket raw-images
awslocal s3api create-bucket --bucket processed-images

echo "Initialized."
