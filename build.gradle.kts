plugins {
    val kotlinVersion = "1.4.32"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    //id("net.mamoe.mirai-console") version "2.6.7"
}

group = "org.yinlianlei"
version = "0.1.0"

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/public") // 阿里云国内代理仓库
    
    mavenCentral()
}

dependencies {
    implementation("net.mamoe:mirai-console:2.6.7:all")
    val miraiVersion = "2.6.7"
    api("net.mamoe", "mirai-core-api", miraiVersion)     // 编译代码使用
    runtimeOnly("net.mamoe", "mirai-core", miraiVersion) // 运行时使用
    //api("net.mamoe:mirai-login-solver-selenium:1.0-dev-16")
    //implementation(fileTree(mapOf("dir" to "libs","include" to listOf("*.jar"))))
    implementation("mysql:mysql-connector-java:8.0.22")
    implementation("com.alibaba:fastjson:1.2.53")
}
