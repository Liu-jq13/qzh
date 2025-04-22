# 构建阶段
FROM maven:3.9.9-amazoncorretto-17 AS build

WORKDIR /app

# 复制文件并忽略不必要的内容（通过 .dockerignore 配置）
COPY . .

# 使用 Maven 构建项目
RUN mvn -B clean package -DskipTests

# 运行阶段
FROM openjdk:17

WORKDIR /app

# 将构建产物复制到运行镜像
COPY --from=build /app/target/*.jar app.jar

# 设置启动命令并暴露端口
CMD ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]

EXPOSE 8080
