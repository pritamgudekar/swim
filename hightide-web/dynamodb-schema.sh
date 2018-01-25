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
