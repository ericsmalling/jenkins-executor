# Jenkins Swarm based executor Docker image
A basic Docker image that will connect to a Jenkins master using the [Jenkins Swarm Plugin](https://wiki.jenkins.io/display/JENKINS/Swarm+Plugin) and includes a Docker client pre-installed.

For a simple example running on swarm, see the [full_demo](/full_demo) subdirectory here.

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
