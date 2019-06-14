# bricks-framework
积木式（或者说模块式\插件式）网站框架，可以合并部署，也可以分开部署，页面样式标准化，引入数据库sql版本管理，统一配置服务，统一日志收集服务，引入防止频繁访问机制，部署方式容器化

# Nacos 安装
到 https://github.com/alibaba/nacos/releases 下载最新版，下载后解压即安装完成，然后进入解压目录后的bin目录执行startup.cmd或startup.sh启动Nacos。访问Nacos服务 http://127.0.0.1:8848/nacos/#/login 默认情况用户名密码都是nacos。

# ElasticSearch 安装
到 https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.6.12.zip 下载，下载后解压即安装完成，然后进入解压目录后的bin目录执行elasticsearch.bat或elasticsearch启动ElasticSearch。访问 http://127.0.0.1:9200 则可返回一些基本信息。
## 备注：使用的ElasticSearch服务版本最好要跟spring boot里面使用的ElasticSearch版本一致。
