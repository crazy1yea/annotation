# mybatis逆向工程，通过已有的数据库表生成对应基本代码，mysql定制
# 调整好相应参数，cmd到当前目录，执行以下命令
java -jar mybatis-generator-mysql.jar -configfile generatorConfig.xml -overwrite

java -jar mybatis-generator-core-1.3.7.jar -configfile generatorConfig.xml -overwrite