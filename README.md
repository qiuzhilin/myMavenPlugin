# myMavenPlugin
自定义maven插件，主要实现编译后，可以对指定文件进行内部字符串替换，可以根据不同的编译环境，替换不同的字符串值。

# maven 自定义插件的关键步骤
继承`org.apache.maven.plugin.AbstractMojo`这个抽象类，同时需要在类上面使用@Mojo注解

# 使用例子
![image](https://user-images.githubusercontent.com/17595316/221070861-73d5da39-c420-4150-b17f-f23cbf3fc59f.png)

# 关键字段说明
<phase> 表示该插件执行时机是compile阶段
<goal>表示执行插件包中的类，跟@Mojo 的name 值关联，如果有多个执行类，那就配置多个goal
<configuration> 下面的是自定义的参数，
changeFiles 是需要执行替换操作文件的全路径，如果是多个文件，可以用逗号分隔
sourceStrings 是替换的源字符，多个可以用逗号分隔，个数要与targetStrings保持一致
targetStrings 是替换的模板字符，多个可以用个逗号分隔，个数要与sourceStrings 保持一致
