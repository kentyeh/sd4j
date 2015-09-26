

Auto instantiate and inject to annotation with @Dao and @DaoManager for prevent boring configuration. Detail see [TestSource](http://code.google.com/p/sd4j/source/browse/trunk/src/test/java/springdao/TestDao4j.java)<br />
Chinese:自動建立並插入@Dao與@DaoManager以免除煩瑣的設定，使用方式請見[測試程式](http://code.google.com/p/sd4j/source/browse/trunk/src/test/java/springdao/TestDao4j.java)

for maven user, add repository like
```
    <repositories>
        <repository>
            <id>gwtrepo</id>
            <name>Kent's Repository</name>
            <url>http://gwtrepo.googlecode.com/svn/repo</url>
        </repository>
    </repositories>
```
and add dependency(for spring 3.1.0-)
```
    <dependencies>
      <dependency>
        <groupId>springDao</groupId>
        <artifactId>sd4j</artifactId>
        <version>1.0</version>
      </dependency>
    </dependencies>
```
[1.0.jar download](http://gwtrepo.googlecode.com/svn/repo/springDao/sd4j/1.0/sd4j-1.0.jar).

or add dependency(for spring 3.2)
```
    <dependencies>
      <dependency>
        <groupId>springDao</groupId>
        <artifactId>sd4j</artifactId>
        <version>2.0.9</version>
      </dependency>
    </dependencies>
```
[sd4j-2.0.9.jar download](http://gwtrepo.googlecode.com/svn/repo/springDao/sd4j/2.0.5/sd4j-2.0.9.jar)

Don't forget put DaoAnnotationBeanPostProcessor into your spring context.
```
<bean class="springdao.DaoAnnotationBeanPostProcessor"/>
```

Note:
> If it is used in web,I think [OpenEntityManagerInViewFilter](http://static.springsource.org/spring/docs/3.1.x/javadoc-api/org/springframework/orm/hibernate3/support/OpenSessionInViewFilter.html) may be added into your web.xml like
```
    <filter>
        <filter-name>jpaFilter</filter-name>
        <filter-class>org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>jpaFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```
,but before you add it , read [this](http://techblog.bozho.net/?p=645) first.

Another project [sd4h](http://code.google.com/p/sd4h/) using pure Hibernate is compatible.

and project [sd4o](http://code.google.com/p/sd4o/) using pure JDO is compatible.