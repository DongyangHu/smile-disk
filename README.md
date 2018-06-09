# smile-disk

#### 项目介绍
Smile Disk项目是使用Java语言进行开发的一个在线网盘系统。在架构时，采用前后端分离的模式，并且将项目的功能模块进行分割，单独切分成后台子工程，能够实现分布式部署。本项目主要实现了用户日常对于文件的基本操作，包括文件上传、文件下载、文件共享、文件删除，文件回收站的功能，实现了一个简陋的站内消息功能。项目所有的功能按照模块进行划分，便于扩展，并且实现了用户权限管理。

#### 主要涉及的框架技术
- 后台技术
    - Spring Framework - [https://spring.io/projects/spring-framework](https://spring.io/projects/spring-framework)
    - Spring-Data-Redis - [https://projects.spring.io/spring-data-redis](https://projects.spring.io/spring-data-redis)
    - Spring Session - [https://spring.io/projects/spring-session](https://spring.io/projects/spring-session)
    - SpringMVC - [https://spring.io/projects/spring-framework](https://spring.io/projects/spring-framework)
    - RESTEasy - [https://resteasy.github.io](https://resteasy.github.io)
    - MyBatis - [http://www.mybatis.org/mybatis-3](http://www.mybatis.org/mybatis-3)
    - MySQL - [https://www.mysql.com](https://www.mysql.com)
    - Redis - [https://redis.io](https://redis.io)
    - Maven - [http://maven.apache.org/](http://maven.apache.org/)
- 前端技术
    - Layui - [http://www.layui.com](http://www.layui.com)
    - Bootstrap - [https://getbootstrap.com](https://getbootstrap.com)
    - JQuery - [https://jquery.com](https://jquery.com)
    - Vue.js - [https://cn.vuejs.org](https://cn.vuejs.org)
    - RequireJS - [http://requirejs.org](http://requirejs.org)
    - zTree - [http://www.treejs.cn](http://www.treejs.cn)
- web容器
    - Nginx - [http://nginx.org](http://nginx.org)
    - Tomcat - [https://tomcat.apache.org](https://tomcat.apache.org)
#### 项目工程介绍
- sd_base:该工程为所有工程的父工程，提供通用的maven依赖管理；
- sd_baseData:该工程为基础数据工程，提供用户管理，系统权限管理，redis初始化等；
- sd_common:该工程为公用工具包工程， 提供公用的方法类；
- sd_fm:该工程为文件管理工程，提供文件上传、下载等与文件相关的功能；
- sd_sso:该工程为单点登录工程；
- sd_web_view:该工程为前端静态资源工程，包括相关页面，JS代码等。

#### 快速开始
- 简介
> 项目采用前后端分离的方式，前端工程与后台工程完全独立。后台工程使用 [Eclipse](https://www.eclipse.org) 进行开发，前端工程使用 [HBuider](http://www.dcloud.io) 进行开发。进行项目部署时，前端工程部署在 [Nginx](http://nginx.org) 服务器中，后台工程部署在 [Tomcat](https://tomcat.apache.org) 服务器中，并且项目后台的运行依赖[MySQL](https://www.mysql.com)、[Redis](https://redis.io)以及[FTP](https://filezilla-project.org)服务，并且需要一个用于系统邮件发送的电子邮箱，需要先行准备。

- 前端工程
> 将前端工程` sd_web_view `放在本机的任意目录，发布到Nginx服务器即可，需要对Nginx的负载均衡服务器进行配置，配置成下面后台工程启动的地址和端口，配置可以参考：[项目Nginx配置](https://github.com/hudongyang123/smile-disk/tree/master/nginx-conf)

- 后台工程
>将项目导入到Eclipse中，项目使用的JDK版本为1.8，Tomcat版本为8.5。

1. 安装配置[MySQL](https://www.mysql.com)、[Redis](https://redis.io)以及[FTP](https://filezilla-project.org)服务；
2. 在MySQL数据库中新建`sddb`数据库，将`DB/sddb.sql`导入数据库中；
3. 打包sd_common工程到本地Maven仓库，此为项目的基础组件包；
4. 将sd_sso工程的` com.sd.sso.filter `、` com.sd.sso.filter.bean `两个包下的内容打成JAR包，并发布到本地Maven仓库，此为项目的用户登录状态拦截器；
5. 打包sd_base项目，发布到Maven仓库中，`pom.xml`文件中的一下内容应该与上述两项打包内容保持一致；
    ```
    <!-- 单点登录filter -->
    <dependency>
        <groupId>com.sd.filter</groupId>
        <artifactId>sd_sso_filter</artifactId>
        <version>0.0.1</version>
    </dependency>
    <!-- 工具包 -->
    <dependency>
        <groupId>com.sd.util</groupId>
        <artifactId>sd_common</artifactId>
        <version>0.0.1</version>
    </dependency>
    ```
6. 对各个后台工程进行配置
    - sd_baseData
        - 配置项目的`Context root`为`/`，即在Tomcat部署中，项目访问从`/`开始；
        - 配置项目resources目录下的`sso.properties`文件中的两项服务器地址为你自己部署的Nginx地址，若采用我提供的配置文件，则不需要更改
    `ssoLoginUrl=http://{你的nginx地址}
    checkEmailUrl=http://{你的nginx地址}/baseData/checkEmail.html`；
        - 配置` sdMail.properties `，此为发送系统邮件邮箱的配置，配置为你的邮箱即可，其中` mailPwd `邮箱密码项需要使用项目公有的方法加密，方法路径为sd_common工程下的` com.sd.common.encryp.EncryptAES `包下面的 ` encryptAES(String content, String encrypKey) ` 方法，加密key为常量 ` com.sd.common.encryp.EncrypConstants.AES_DECRYPT_KEY `；
        - 配置` sdConfig.properties `，此为项目的数据库及redis配置，将其更改为你的连接地址即可，需要注意的是，数据库的用户名和密码同样需要使用上述方式进行加密处理；

    - sd_fm
        - 配置resources目录下的` sdConfig.properties ` 和 ` sso.properties `，与上述配置完全相同
        - 配置` fmConfig.properties `，此为文件上传相关的配置，其中配置了FTP服务的地址和用户信息，需要根据你自己安装的服务配置，` ftp_upload_base_path ` 为文件上传的根路径。*这里还提供了http方式上传文件的配置，因为在代码中曾经使用过http上传的方式，但已经摒弃，如果需要可以在文件上传模块 ` com.sd.fm.controller.FileUploadController `第58行处自行切换*

    - sd_sso
        - 配置resources目录下的` sdConfig.properties ` 和 ` sso.properties `，与上述配置相同，需要主要的是这里只需要配置 ` ssoLoginUrl ` 项即可。

7. 将上述 ` sd_sso `、` sd_fm `、 ` sd_baseData ` 项目部署到Tomcat中，指定不同的端口号，并进行记录。将每个工程的地址配置到Nginx配置文件响应的` upstream ` 处

- 启动前端工程与后台工程，通过nginx进行访问即可

#### LICENSE
- [LICENSE](https://github.com/hudongyang123/smile-disk/blob/master/LICENSE)
