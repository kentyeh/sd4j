#Spring Dao Libray for easy using JPA

SD4J is a easy way to using JPA in spring framework.

## maven
```
<dependency>
    <groupId>com.github.kentyeh</groupId>
    <artifactId>sd4j</artifactId>
    <version>1.0</version>
</dependency>
```
or download from [maven site](http://search.maven.org/#search%7Cga%7C1%7Cg:%22com.github.kentyeh%22%20AND%20a:%22sd4j%22)

## Configure
### XML Configure
```
<!-- processing @Dao and @DaoManager-->
<bean class="springdao.DaoAnnotationBeanPostProcessor"/>
```
or
### JAVA Configure
```
@Configuration
public class ApplicationContext{
    @Bean
    public DaoAnnotationBeanPostProce daoAnnotationBeanPostProce() {
        return new DaoAnnotationBeanPostProce();
    }
}
```
## Examples
### ex.1 Inject direct.
```
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
```
@Transactional(readOnly = true)
public class CustomManager<E> extends RepositoryManager<E>{
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void busiMethod(...){
       ....bla bla
    }
}
```
```
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
```
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
