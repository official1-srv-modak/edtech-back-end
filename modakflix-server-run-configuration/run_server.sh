#!/bin/bash

# File to store port numbers and PIDs
PORT_FILE="ports.conf"
PID_FILE="pids.conf"

# Global array to store PIDs
declare -a PIDS=()

# Function to kill a process using a specific port
kill_port() {
    local port=$1
    local pid
    pid=$(lsof -t -i:"$port" 2>/dev/null)
    if [[ -n $pid ]]; then
        echo "Port $port is occupied by PID $pid. Killing process..."
        kill -9 "$pid" && echo "Killed process $pid on port $port."
    else
        echo "Port $port is free."
    fi
}

# Function to perform a force git pull
git_pull_all() {
    echo "Performing a force git pull --all in $1..."
    cd "$1" || { echo "$1 not found"; exit 1; }
    git fetch --all || { echo "Failed to fetch changes in $1"; exit 1; }
    git reset --hard origin/$(git rev-parse --abbrev-ref HEAD) || { echo "Failed to reset in $1"; exit 1; }
    cd ..
}

# Function to start a service and save its PID
start_service() {
    local dir=$1
    local cmd=$2
    local port=$3
    echo "Starting $dir..."

    # Perform force git pull --all before starting the service
    git_pull_all "$dir"

    # Free the port before starting the service
    if [[ -n $port ]]; then
        kill_port "$port"
    fi

    cd "$dir" || { echo "$dir not found"; exit 1; }
    nohup $cmd > "../logs/$dir.log" 2>&1 &  # Run in background, redirect logs
    local pid=$!
    PIDS+=($pid)  # Store the PID of the background process
    echo "$dir started with PID: $pid"
    echo "$dir=$pid" >> "../$PID_FILE"  # Save PID to file
    cd ..
}

# Function to read or set ports
read_or_set_ports() {
    if [[ -f $PORT_FILE ]]; then
        echo "Reading ports from $PORT_FILE..."
        source $PORT_FILE
    else
        echo "No port configuration file found. Setting ports now..."
        read -p "Enter port for modakflix-test-module: " PORT1
        read -p "Enter port for modakflix-auth: " PORT2
        read -p "Enter port for modakflix-server-management: " PORT3
        read -p "Enter port for modakflix-commentsection: " PORT4
        read -p "Enter port for modakflix-stream: " PORT5
        read -p "Enter port for OTT_Server_final_react: " PORT6

        # Save ports to the configuration file
        echo "PORT1=$PORT1" > $PORT_FILE
        echo "PORT2=$PORT2" >> $PORT_FILE
        echo "PORT3=$PORT3" >> $PORT_FILE
        echo "PORT4=$PORT4" >> $PORT_FILE
        echo "PORT5=$PORT5" >> $PORT_FILE
        echo "PORT6=$PORT6" >> $PORT_FILE
    fi
}

# Function to stop all services
stop_all() {
    echo "Stopping all services..."
    if [[ -f $PID_FILE ]]; then
        while IFS= read -r line; do
            service=$(echo "$line" | cut -d'=' -f1)
            pid=$(echo "$line" | cut -d'=' -f2)
            if kill -9 "$pid" > /dev/null 2>&1; then
                echo "Stopped $service with PID: $pid"
            else
                echo "Failed to stop $service with PID: $pid or process already stopped"
            fi
        done < "$PID_FILE"
        rm -f "$PID_FILE"  # Remove PID file after stopping all services
    else
        echo "No running services found."
    fi
    exit 0
}

# Trap SIGINT (Ctrl + C) to stop all services
trap 'stop_all' SIGINT

# Main start function
start_all() {
    # Ensure logs directory exists
    mkdir -p logs

    # Clear the PID file
    > $PID_FILE

    # Start services with stored or new ports
    start_service "modakflix-test-module" "mvn spring-boot:run" "$PORT1"
    start_service "modakflix-auth" "mvn spring-boot:run" "$PORT2"
    start_service "modakflix-server-management" "mvn spring-boot:run" "$PORT3"
    start_service "modakflix-commentsection" "mvn spring-boot:run" "$PORT4"
    start_service "modakflix-stream" "mvn spring-boot:run" "$PORT5"
    start_service "OTT_Server_final_react" "sudo npm run dev -- --host" "$PORT6"

    # Wait for all background processes (keeps the script running)
    wait
}

# Reset ports if "reset" argument is passed
if [[ $1 == "reset" ]]; then
    echo "Resetting port configuration..."
    rm -f $PORT_FILE
    echo "Port configuration reset. Run the script again to set new ports."
    exit 0
fi

# Stop services if "stop" argument is passed
if [[ $1 == "stop" ]]; then
    stop_all
fi

# Start services if "start" argument is passed
if [[ $1 == "start" ]]; then
    read_or_set_ports
    start_all
else
    echo "Usage: $0 start | stop | reset"
fi
