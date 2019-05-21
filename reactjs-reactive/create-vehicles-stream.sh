#!/bin/bash
port=${1:-8080}
count=0
amt=5
vehicle() {
	((count++))
	echo "posting ${count}"
	http POST http://localhost:${port}/vehicles/api/create id="random${count}" name="random${count}" description="describe me"
	if [ "$count" -gt $amt ]; then
		echo "out"
		break
	fi
}

while sleep 1; do vehicle; done
