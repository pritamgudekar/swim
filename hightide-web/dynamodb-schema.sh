#!/usr/bin/env bash
aws dynamodb create-table \
    --endpoint-url http://localhost:8000 \
    --table-name hightide-users \
    --attribute-definitions \
        AttributeName=username,AttributeType=S \
    --key-schema AttributeName=username,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1

aws dynamodb delete-table \
    --endpoint-url http://localhost:8000 \
    --table-name hightide-users


aws dynamodb create-table \
    --endpoint-url http://localhost:8000 \
    --table-name hightide-students \
    --attribute-definitions \
        AttributeName=username,AttributeType=S \
        AttributeName=studentname,AttributeType=S \
    --key-schema AttributeName=studentname,KeyType=HASH \
           AttributeName=username,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1