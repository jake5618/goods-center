## 项目使用COLA脚手架
```shell
mvn archetype:generate \
    -DgroupId=com.jake \
    -DartifactId=goods-service \
    -Dversion=1.0.0-SNAPSHOT \
    -Dpackage=com.jake.goods\
    -DarchetypeArtifactId=cola-framework-archetype-web \
    -DarchetypeGroupId=com.alibaba.cola \
    -DarchetypeVersion=5.0.0
```