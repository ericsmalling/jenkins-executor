TODO: Document this full example

Quickstart:

Given you have a Docker swarm running with version 17.05 CE or newer (or 17.06 EE)
```
# Edit docker-compose.build.yml and docker-compose.yml with your Docker registry account and image names (ie: change "ericsmalling" to your DockerHub account)
$ docker-compose -f docker-compose.build.yml build
$ docker-compose -f docker-compose.build.yml push
$ docker stack deploy -c docker-compose.yml ci
Creating secret ci_jenkins_user
Creating secret ci_jenkins_password
Creating service ci_master
Creating service ci_agent
```
Open http://localhost:8080 (substitute the host running the Jenkins master if not on localhost)

