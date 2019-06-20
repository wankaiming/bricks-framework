# bricks-framework
积木式（或者说模块式\插件式）网站框架，可以合并部署，也可以分开部署，页面样式标准化，引入数据库sql版本管理，统一配置服务，统一日志收集服务，引入防止频繁访问机制，部署方式容器化

# Nacos 安装
到 https://github.com/alibaba/nacos/releases 下载最新版，下载后解压即安装完成，然后进入解压目录后的bin目录执行startup.cmd或startup.sh启动Nacos。访问Nacos服务 http://127.0.0.1:8848/nacos/#/login 默认情况用户名密码都是nacos。

# ElasticSearch 安装
到 https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.6.12.zip 下载，下载后解压即安装完成，然后进入解压目录后的bin目录执行elasticsearch.bat或elasticsearch启动ElasticSearch。访问 http://127.0.0.1:9200 则可返回一些基本信息。
## 备注：使用的ElasticSearch服务版本最好要跟spring boot里面使用的ElasticSearch版本一致。

# 启动命令可用的参数
--spring.profiles.active=test 激活测试环境的配置
--spring.config.location=xx 外部配置文件路径
-Xms512m JVM初始分配的堆内存大小，默认是物理内存的1/64。
-Xmx2048m JVM最大分配的堆内存大小，默认是物理内存的1/4。
-Dfile.encoding=UTF-8 指定jvm默认编码

# 配置中心需要建立两个配置文件
## 第一个配置文件
Data ID: application.yml 
Group: DEFAULT_GROUP
yaml内容:
mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  type-aliases-package: org.bricks.framework.**.entity;
server:
  context-path: /
  port: 8280
  tomcat:
    uri-encoding: UTF-8
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/bricks_framework_db?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=UTC
    username: root
    password: root
    # 使用druid数据源
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      filters: stat
      max-active: 100
      initial-size: 1
      max-wait: 60000
      min-idle: 1
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 300000
      validation-query: select 1
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false
      pool-prepared-statements: true
      max-open-prepared-statements: 50
      max-pool-prepared-statement-per-connection-size: 20
      stat-view-servlet:
        enabled: true
        login-username: admin
        login-password: admin
  mvc:
    favicon:
      enabled: false
    view:
      prefix: /WEB-INF/pages/
      suffix: .jsp
  redis: 
    host: 192.168.101.100
    port: 6379
    database: 0
    timeout: 5s
    jedis:
      pool:
        max-idle: 500
        min-idle: 10
        max-wait: 10s
        max-active: 500
  rabbitmq:
    host: 192.168.101.200
    port: 5672
    username: admin
    password: admin123
    virtual-host: /bricks-framework
  resources:
    chain:
      strategy:
        content:
          paths: /**
          enabled: true
  flyway: 
     clean-disabled: true 
     enabled: true
     baseline-on-migrate: true
     baseline-version: 1
     locations: classpath:/db
     out-of-order: true
  data:
    elasticsearch:
      cluster-nodes: 127.0.0.1:9300
      repositories:
        enable: true
      cluster-name: elasticsearch
httpLog:
  url: http://127.0.0.1:8000/log
logging:
    config: classpath:logback-custom.xml



## 第二个配置文件
Data ID: common.yml
Group: DEFAULT_GROUP
yaml内容:
name: 呵呵dev