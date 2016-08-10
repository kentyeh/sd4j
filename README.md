#Spring Dao Libray for easy using JPA

[![Build Status](https://travis-ci.org/kentyeh/sd4j.svg?branch=master)](https://travis-ci.org/kentyeh/sd4j)
[![Coverage Status](http://img.shields.io/coveralls/kentyeh/sd4j/master.svg?style=flat-square)](https://coveralls.io/github/kentyeh/sd4j)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.kentyeh/sd4j/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.kentyeh/sd4j)
[![Javadocs](http://www.javadoc.io/badge/com.github.kentyeh/sd4j.svg?color=blue)](http://www.javadoc.io/doc/com.github.kentyeh/sd4j)
[![Contributor](http://wsbadge.herokuapp.com/badge/Developer-Kent%20Yeh-oragnle.svg)](https://github.com/kentyeh)

SD4J is a easy way to using JPA in spring framework.

## maven
```xml
<dependency>
    <groupId>com.github.kentyeh</groupId>
    <artifactId>sd4j</artifactId>
    <version>1.0.1</version>
</dependency>
```
or download from [maven site](http://search.maven.org/#search%7Cga%7C1%7Cg:%22com.github.kentyeh%22%20AND%20a:%22sd4j%22)

## Configure
### XML Configure
```xml
<!-- processing @Dao and @DaoManager-->
<bean class="springdao.DaoAnnotationBeanPostProcessor"/>
```
or
### JAVA Configure
```java
@Configuration
public class ApplicationContext{
    @Bean
    public DaoAnnotationBeanPostProcessor daoAnnotationBeanPostProce() {
        return new DaoAnnotationBeanPostProcessor();
    }
}
```
## Examples
### ex.1 Inject direct.
```java
public String App(){
    // Where Phone is a jpa entity
    @DaoManager(Phone.class)
    private RepositoryManager<Phone> phoneManager;
    public List<Phone> findOrphanPhones(){
        return phoneManager.findByCriteria(JpqlHelper.get().where("owner").isNull().ql());
    }
}
```
### ex2. Custom business manager
```java
@Transactional(readOnly = true)
public class CustomManager<E> extends RepositoryManager<E>{
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void busiMethod(...){
       ....bla bla
    }
}
```
```java
public String App(){
  @DaoManager
  private CustomManager<Phone> phoneManager;
  public void doSomething(){
     phoneManager.busiMethod();
     ... bla bla
  }
}
```
Other example look [TestDao4j.java](https://github.com/kentyeh/sd4j/blob/master/src/test/java/springdao/TestDao4j.java).

# Tips
Spring web mvc help type conversion.

for example: convert primitive String or int  to jpa entity.
```java
import springdao.DaoManager;
import springdao.RepositoryManager;
import springdao.support.DaoPropertyEditor;

@Controller
public class WebController {
  @DaoManager
    private RepositoryManager<Member> memberManager;
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(Member.class, new DaoPropertyEditor(memberManager));
  }
  @RequestMapping("/admin/user/{account}")
  public String userinfo(@PathVariable Member account, HttpServletRequest request) {
    account....;
  }
}
```
When I access http://localhost/admin/user/kent ,
It will convert account name: "kent" to Member Object.
