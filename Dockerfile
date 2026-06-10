FROM tomcat:9-jdk8-temurin

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR
COPY target/school_o2o.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]
