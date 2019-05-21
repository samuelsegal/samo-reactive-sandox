#!/bin/bash
port=${1:-8080}
count=0
amt=5
vehicle() {
	randomword=`perl -e 'open IN, "</usr/share/dict/words";rand($.) < 1 && ($n=$_) while <IN>;print $n'`
	((count++))
	echo "posting ${randomword}"
	http POST http://localhost:${port}/vehicles/api/create id="${randomword}" name="${randomword}" description="describe me"
	if [ "$count" -gt $amt ] 
		then
		echo "out"
		break
	fi
}

while sleep 1; do vehicle; done
