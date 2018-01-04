#!/bin/bash

# Send Greeting JSON Template
read -r -d '' GREETING_JSON_TEMPLATE <<EOF
{
   "greeter":"__GREETER__",
   "message":"__MESSAGE__"
}
EOF

function buildGreetingPayload() {
  local GREETER=$1
  local MESSAGE=$2
  local _json=`echo ${GREETING_JSON_TEMPLATE} | \
    sed "s|__GREETER__|${GREETER}|g" | \
    sed "s|__MESSAGE__|${MESSAGE}|g"`
  echo ${_json}
}

GREETERS=(Jack Jack Sam Amy)
MESSAGES=(Hello\ there This\ is\ Jack Hi\ Jack Hello\ Jack)
arraylength=${#GREETERS[@]}
for (( i=1; i<${arraylength}; i++ ));
do
   echo "${GREETERS[$i]} sends a greeting ${MESSAGES[$i]}"
   SEND_GREETING_JSON=$(buildGreetingPayload ${GREETERS[$i]} "${MESSAGES[$i]}")
   SEND_GREETING_RESP=`curl -si -H "Content-Type: application/json" -X POST 'http://localhost:8080/greeting' -d "${SEND_GREETING_JSON}"`
   echo "${SEND_GREETING_RESP}"
done

echo "Get all greetings..."
GET_ALL_GREETINGS_RESP=`curl -sb -H "Content-Type: application/json" -X GET 'http://localhost:8080/greeting'`
echo ${GET_ALL_GREETINGS_RESP}

echo "Get geetings from Jack..."
GET_GREETINGS_RESP=`curl -sb -H "Content-Type: application/json" -X GET 'http://localhost:8080/greeting/Jack'`
echo ${GET_GREETINGS_RESP}



