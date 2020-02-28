# Rita

基于[mirai](https://github.com/mamoe/mirai)开发的QQ机器人

服务于某某某群

如遇到网络问题，请将build.gradle中repositories代码块中注释删除以使用阿里云镜像
    
```groovy

buildscript {
    ext {
        //...
    }

    repositories {
        //jcenter()
        maven { url "https://maven.aliyun.com/repository/jcenter" }
    }

    dependencies {
        //...       
    }
}

//...

repositories {
    //mavenCentral()
    maven { url "https://maven.aliyun.com/repository/central" }
    //jcenter()
    maven { url "https://maven.aliyun.com/repository/jcenter" }
}

//...

```