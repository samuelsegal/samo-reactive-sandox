#!/bin/bash
port=${1:-8080}
count=0
amt=5
accessToken=r2ZGQ3l2N2xucEkwaGxyYkFEZE5ld2tqWUNUcnpxMjM2eDlyUkhlUzFVIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULnJsVG1NNy03NURDMFFLMXJLS284bGdENEN1cTlFT1M2UUh4S2xJVWhhWjAiLCJpc3MiOiJodHRwczovL2Rldi01NDg5MTcub2t0YS5jb20vb2F1dGgyL2RlZmF1bHQiLCJhdWQiOiJhcGk6Ly9kZWZhdWx0IiwiaWF0IjoxNTU4NTY2MzYzLCJleHAiOjE1NTg1Njk5NjMsImNpZCI6IjBvYW1ua2xlMVRoWVFHUGl4MzU2IiwidWlkIjoiMDB1bW5ncDByb1UwSHVHdVMzNTYiLCJzY3AiOlsiZW1haWwiLCJwcm9maWxlIiwib3BlbmlkIl0sInN1YiI6InNhbXVlbG1vc2Vzc2VnYWxAZ21haWwuY29tIn0.FwEIbSQa8hjeJbTNrel_8vKsTWnHuJUt9eXukEdVy67Pkq4woAZRwTFB8KLueORHffZFlet_pPN_97twJtIrxmDZFgM3B90B16tfE3BZMoulmUo88vMuL8mAV0G3wZ_BsnZR5FsTw8ZukqffW0agJ4zzhIHrbPoLXABBx1-w-7rwIv7o11L3f0gDhU-Gr3u2LLgxQf0zM0svLgImlc71yYkEizMo9QsyOvn-sP2K5mcQ9hNBtyPnWf4TJqEf3dn4r-QqgM5m33sTBy5sQXR7VV9GsUJQPPn_-PH3iR8LCPy9nhbDdXZ2GSHzepnHyz8Amv-qQH6VcSL0CeeNTaPyMg
vehicle() {
	randomword=$(perl -e 'open IN, "</usr/share/dict/words";rand($.) < 1 && ($n=$_) while <IN>;print $n')
	((count++))
	echo "posting ${randomword}"
	http POST http://localhost:${port}/vehicles/api/create "Authorization: Bearer ${accessToken}" id="${randomword}" name="${randomword}" description="describe me"
	if [ "$count" -gt $amt ] 
		then
		echo "out"
		break
	fi
}

while sleep 1; do vehicle; done
