FROM java:8
#将文件夹下的 jar 包全部拷贝到 Docker 容器中并命名为 app.jar
COPY *.jar /eamon.jar

CMD ["--server.port=8080"]

EXPOSE 8080

ENTRYPOINT ["java","-jar","/eamon.jar"]