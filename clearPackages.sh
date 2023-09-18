#!/bin/bash

# GitHub username and repository name
USERNAME="nekzabirov"
REPO="Firebase_KMM"

# Personal Access Token with delete:packages, read:packages, and write:packages scope
TOKEN="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"

# List all package versions
curl -X GET -H "Authorization: token $TOKEN" "https://api.github.com/user/packages?package_type=maven" | jq -r '.[].name' | while read -r PACKAGE_ID; do
  # Delete each package version
 curl -X DELETE -H "Authorization: token $TOKEN" "https://api.github.com/user/packages/maven/$PACKAGE_ID"
done
