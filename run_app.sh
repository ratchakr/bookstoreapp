#!/bin/bash
echo "Starting application run script..........."

sleep 10


echo "couchbase is running on "$HOST

echo "bucket to check is "$BUCKET_NAME

wget http://stedolan.github.io/jq/download/linux64/jq

chmod +x ./jq

cp jq /usr/bin

echo "jq installed"

echo "calling cb bucket Http Get"

#response=$(curl -s -u Administrator:password "http://"$HOST":8091/pools/default/buckets/books" | jq -r '.name')

response=''
#$(curl -s -u Administrator:password "http://"$HOST":8091/pools/default/buckets/"$BUCKET_NAME | jq -r '.name')


while [ -z $response ]
do 
  response=$(curl -s -u Administrator:password "http://"$HOST":8091/pools/default/buckets/"$BUCKET_NAME | jq -r '.name')
  echo "response from cb "$response
  echo "************************************************"
  echo "************************************************"
  sleep 5
done  

echo "bucket is now ready bucket name "$response

echo "Run application container now"

echo "************************************************"

sleep 2

java -Djava.security.egd=file:/dev/./urandom -jar /app.jar

echo "************************************************"

echo "exit"
