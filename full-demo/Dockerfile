FROM alpine AS docker-fetch

ARG DOCKER_URL=https://download.docker.com/linux/static/stable/x86_64/docker-17.12.0-ce.tgz
ARG COMPOSE_VERSION=1.18.0

RUN apk add --update curl
RUN cd / && curl -L -so docker.tgz ${DOCKER_URL} && \
    tar -xzf docker.tgz && \
    curl -L -so docker/docker-compose https://github.com/docker/compose/releases/download/${COMPOSE_VERSION}/docker-compose-linux-x86_64 && \
    chmod +x docker/*

FROM jenkins/jenkins:2.89.3-alpine AS jenkins-master
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

ENV JENKINS_ADMIN_USER_FILE "/run/secrets/jenkins_user"
ENV JENKINS_ADMIN_PASSWORD_FILE "/run/secrets/jenkins_password"
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false

COPY master-init.groovy.d/* /usr/share/jenkins/ref/init.groovy.d/


#RUN echo 2.89.3 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state

# HACK to be able to bind-mount home directory
#USER root

FROM jenkins/slave:3.16-1-alpine AS jenkins-agent
USER root
RUN mkdir -p /var/jenkins_workspace
RUN chown -R jenkins:jenkins /var/jenkins_workspace
USER jenkins

COPY --from=docker-fetch /docker/* /usr/bin/

ADD --chown=jenkins:jenkins https://repo.jenkins-ci.org/releases/org/jenkins-ci/plugins/swarm-client/3.8/swarm-client-3.8.jar /home/jenkins/swarm-client.jar
COPY --chown=jenkins:jenkins wait-for-it.sh /home/jenkins/wait-for-it.sh

ENV JENKINS_USER_FILE "/run/secrets/jenkins_user"
ENV JENKINS_PASSWORD_FILE "/run/secrets/jenkins_password"
ENV DOCKER_TLS_VERIFY=1
ENV DOCKER_CERT_PATH="/run/secrets/ucp"
ENV DOCKER_HOST="tcp://ucp.eric.dtcntr.net:10443"

CMD /home/jenkins/wait-for-it.sh -t 0 $MASTER_HOST:$MASTER_JNLP_PORT -- java -jar /home/jenkins/swarm-client.jar -username $(cat ${JENKINS_USER_FILE}) -password $(cat ${JENKINS_PASSWORD_FILE}) -master http://$MASTER_HOST:$MASTER_HTTP_PORT -labels docker -fsroot /var/jenkins_workspace -executors 1
