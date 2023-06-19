JMetro、ControlsFX、JFoenix和TornadoFX都是流行的JavaFX框架，用于创建现代化的用户界面。它们之间的区别和特点如下：
1. JMetro：这是一个由Pixel Duke开发的JavaFX主题库，提供了多种颜色方案和样式，旨在为您的应用程序提供现代化的外观和感觉。
2. ControlsFX：这是一个通用的JavaFX控件库，提供了大量的UI控件和效果来增强您的应用程序。它包括一些实用的工具类和常规控件，
如通知、对话框、树表格等。
3. JFoenix：这是一个基于Material Design语言的JavaFX框架，提供了一组自定义控件和样式，可以帮助您创建富有活力的用户界面。
它还支持响应式设计、状态管理和主题定制等功能。
4. TornadoFX：这是一个基于Kotlin语言的JavaFX框架，旨在提供一组简单易用的API和DSL，以便更轻松地编写类型安全的代码。
它还提供了许多有用的功能，如依赖注入、状态管理、消息传递和测试支持等。
总之，这四个JavaFX框架都提供了许多有用的功能和控件，可以帮助您更轻松地创建现代化的用户界面。如果您需要快速实现外观和感觉，
可以使用JMetro或JFoenix。如果您需要更广泛的控件和效果，则可以使用ControlsFX。如果您更喜欢类型安全的DSL编程范式，
则可以尝试使用TornadoFX。


以下是对应于JDK 1.8的Maven依赖：

1. JMetro：

```
<dependency>
    <groupId>com.pixelduke</groupId>
    <artifactId>jmetro</artifactId>
    <version>11.9.0</version>
</dependency>
```

2. ControlsFX：

```
<dependency>
    <groupId>org.controlsfx</groupId>
    <artifactId>controlsfx</artifactId>
    <version>11.1.0</version>
</dependency>
```

3. JFoenix：

```
<dependency>
    <groupId>com.jfoenix</groupId>
    <artifactId>jfoenix</artifactId>
    <version>8.0.10</version>
</dependency>
```

4. TornadoFX：

```
<dependency>
    <groupId>no.tornado</groupId>
    <artifactId>tornadofx</artifactId>
    <version>1.7.20</version>
</dependency>
```

请注意，这些依赖关系可能因版本而异，建议使用最新版本。此外，还需要在项目中引入JavaFX库作为依赖项。



https://github.com/sialcasa/mvvmFX.git


RuoYi - 若依
http://www.ruoyi.vip/
宇道
https://gitee.com/zhijiantianya/ruoyi-vue-pro?_from=gitee_search



在Maven中，有一个名为"scope"的参数，用于描述依赖项的作用域。该参数指定依赖项应该在哪个阶段及其后续阶段(编译、测试、运行时等)可用。
以下是各种scope参数的详解：
1. compile (默认值)：compile scope 表示依赖项在所有阶段都需要使用，包括编译、测试和运行时。
2. provided：provided scope 表示依赖项只在编译和测试阶段可用，而不在运行时可用。例如，Servlet API就是一个
经常使用provided scope的依赖项。
3. runtime：runtime scope 表示依赖项仅在运行时可用，而不在编译阶段可用。例如，JDBC驱动程序通常被配置为具有runtime scope。
4. test：test scope 表示依赖项仅在测试阶段和运行测试时可用。
5. system：system scope 表示依赖项与项目一起提供，并且必须手动在本地系统上安装，因此Maven将不会在任何位置查找它。
6. import：import scope 用于POM项目之间的依赖项管理，以及在父POM和子POM之间共享依赖项版本号。
总之，使用scope参数可以让您更好地管理您的项目依赖项，并确保正确地在各个阶段使用它们。