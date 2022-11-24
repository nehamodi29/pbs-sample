FROM dhaks/mule4.3.0

COPY */target/*.jar /opt/mule/apps/

ENV key=mymulesoft

EXPOSE 8081

CMD [ "/opt/mule/bin/mule"]
