#指定基础镜像，在其上进行定制
FROM java:8

#维护者信息
MAINTAINER zhengyansheng <944699017@qq.com>

VOLUME /tmp
ADD blog-0.0.1-SNAPSHOT.jar app.jar
#RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
           EXPOSE 8989

测试代码


    zhengyansheng