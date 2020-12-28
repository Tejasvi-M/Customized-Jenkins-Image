FROM jenkins/jenkins:lts
USER root
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"
ARG github_username
ARG github_pass
ARG admin_username
ARG admin_pass
RUN apt-get update
RUN apt-get install git -y
RUN apt-get install unzip -y
RUN mkdir /var/jenkins-creds/
RUN echo "$github_username\n$github_pass\n$admin_username\n$admin_pass" > /var/jenkins-creds/pre-configured-github-credentials.txt
COPY jenkins-plugins.txt /usr/share/jenkins/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/plugins.txt
COPY initial-setup.groovy /usr/share/jenkins/ref/init.groovy.d/initial-setup.groovy
COPY script-security-setup.groovy  /usr/share/jenkins/ref/init.groovy.d/script-security-setup.groovy
COPY  github-creds-setup.groovy  /usr/share/jenkins/ref/init.groovy.d/github-creds-setup.groovy
