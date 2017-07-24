# Jenkins Swarm based executor Docker image
A basic Docker image that will connect to a Jenkins master using the [Jenkins Swarm Plugin](https://wiki.jenkins.io/display/JENKINS/Swarm+Plugin) and includes a Docker client pre-installed.

_TODO: Add Jenkins master setup details here_

### Example usage via docker-compose / stack YAML:
```
...
services:
  executor-node:
    image: ericsmalling/jenkins_executor
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - ./workspace:/var/jenkins_workspace
...
```
