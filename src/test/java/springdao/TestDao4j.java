package springdao;

import java.util.HashSet;
import springdao.model.Phone;
import springdao.reposotory.AnnotherDaoRepository;
import springdao.reposotory.AnnotatedRepositoryManagerExt;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import springdao.model.Member;
import springdao.model.UserStore;
import springdao.reposotory.RepositoryManagerExt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;

/**
 *
 * @author Kent Yeh
 */
@ContextConfiguration("classpath:testContext.xml")
public class TestDao4j extends AbstractTestNGSpringContextTests {

    private static Logger logger = LoggerFactory.getLogger(TestDao4j.class);
    @Dao(value = Member.class, name = "annotherDao1")
    private AnnotherDaoRepository anotherDao1;
    private AnnotherDaoRepository anotherDao2;
    @DaoManager(value = Member.class, name = "MM1", daoName = "annotherDao1")
    private RepositoryManager<Member> mm1;
    @DaoManager(value = Member.class, name = "MM2", baseManagerType = RepositoryManagerExt.class)
    private RepositoryManager<Member> mm2;
    @DaoManager(value = Member.class, name = "MM3")
    private RepositoryManagerExt<Member> mm3;
    @DaoManager(value = Member.class, name = "MM4", baseManagerType = RepositoryManagerExt.class)
    private RepositoryManagerExt<Member> mm4;
    @DaoManager(value = Member.class, name = "MM5", baseManagerType = AnnotatedRepositoryManager.class)
    private RepositoryManager<Member> mm5;
    @DaoManager(value = Member.class, name = "MM6", baseManagerType = AnnotatedRepositoryManagerExt.class)
    private RepositoryManager<Member> mm6;
    @DaoManager(value = Member.class, name = "MM7")
    private AnnotatedRepositoryManager<Member> mm7;
    @DaoManager(value = Member.class, name = "MM8")
    private AnnotatedRepositoryManagerExt<Member> mm8;
    @DaoManager(value = Member.class, name = "MM9", baseManagerType = AnnotatedRepositoryManagerExt.class)
    private AnnotatedRepositoryManager<Member> mm9;
    @DaoManager(value = Member.class, name = "MMA", baseManagerType = AnnotatedRepositoryManager.class)
    private AnnotatedRepositoryManagerExt<Member> mmA;
    private RepositoryManager<Member> mmB;
    List<RepositoryManager<Member>> mms = new ArrayList<RepositoryManager<Member>>();
    @DaoManager(Phone.class)
    private RepositoryManager<Phone> phoneManager;
    List<Member> lazys = new ArrayList<Member>();

    @Dao(value = Member.class, name = "annotherDao2")
    public void setAnotherDao2(AnnotherDaoRepository anotherDao2) {
        this.anotherDao2 = anotherDao2;
    }

    @DaoManager(value = Member.class, name = "MMB", baseManagerType = AnnotatedRepositoryManager.class)
    public void setMmB(RepositoryManager<Member> mmB) {
        this.mmB = mmB;
    }

    @BeforeClass
    public void setup() {
        assertThat("setter injection failed.", anotherDao2, is(notNullValue()));

        assertThat("default RepositoryManager is not assigned.", mm1, is(notNullValue()));
        mms.add(mm1);
        assertThat("mm2 expect RepositoryManagerExt.class.", mm2, is(instanceOf(RepositoryManagerExt.class)));
        mm2.setDao(anotherDao2);
        mms.add(mm2);
        assertThat("mm3 expect RepositoryManagerExt.class.", mm3, is(instanceOf(RepositoryManagerExt.class)));
        mms.add(mm3);
        assertThat("mm4 expect RepositoryManagerExt.class.", mm4, is(instanceOf(RepositoryManagerExt.class)));
        mms.add(mm4);
        assertThat("mm5 expect RepositoryManager.class.", mm5, is(instanceOf(AnnotatedRepositoryManager.class)));
        mms.add(mm5);
        assertThat("mm6 expect RepositoryManagerExt.class.", mm6, is(instanceOf(AnnotatedRepositoryManagerExt.class)));
        mms.add(mm6);
        assertThat("mm7 expect RepositoryManager.class.", mm7, is(instanceOf(AnnotatedRepositoryManager.class)));
        mms.add(mm7);
        assertThat("mm8 expect RepositoryManagerExt.class.", mm8, is(instanceOf(AnnotatedRepositoryManagerExt.class)));
        mms.add(mm8);
        assertThat("mm9 expect RepositoryManagerExt.class.", mm9, is(instanceOf(AnnotatedRepositoryManagerExt.class)));
        mms.add(mm9);
        assertThat("mmA expect RepositoryManagerExt.class.", mmA, is(instanceOf(AnnotatedRepositoryManagerExt.class)));
        mms.add(mmA);
        assertThat("mmB expect RepositoryManagerExt.class.", mmB, is(instanceOf(AnnotatedRepositoryManager.class)));
        mms.add(mmB);
    }

    @AfterClass
    public void tearDown() {
    }

    @Test
    public void testNew() {
        int i = 0;
        for (RepositoryManager<Member> m : mms) {
            Member member = new Member();
            member.setName(String.format("testNew%02d", ++i));
            m.save(member);
        }
        assertThat("Insert new member faild", mmB.findByCriteria("WHERE name like ?1", "testNew%").size(), is(greaterThanOrEqualTo(mms.size())));
    }

    @Test(dependsOnMethods = "testNew")
    public void testSelect() {
        int i = 0;
        for (RepositoryManager<Member> m : mms) {
            Member member = m.findFirstByCriteria("WHERE name like ?1", new StringBuilder("test").append("%").append(String.format("%02d", ++i)).toString());
            assertThat("select memberm failed", member, is(notNullValue()));
            lazys.add(member);
        }
    }

    @Test(dependsOnMethods = "testSelect")
    public void testReattachAndInitCollection() {
        int i = 0;
        for (RepositoryManager<Member> m : mms) {
            Member lazy = lazys.get(i++);
            m.initLazyCollection(lazy, "userstores");
            assertThat("init lazy collection failed", lazy.getUserstores().size(), is(greaterThanOrEqualTo(0)));
        }
    }

    @Test
    public void testReattachAndInitCollection2() {
        Member lazy = mmA.findFirstByCriteria("WHERE name=?1", "Jose");
        mmA.initLazyCollection(lazy, "userstores");
        assertThat("init lazy collection failed", lazy.getUserstores().size(), is(greaterThan(0)));
    }

    @Test(dependsOnMethods = "testReattachAndInitCollection")
    public void testModify() {
        List<Member> members = mmB.findByCriteria("WHERE name like 'testNew%' ORDER BY name");
        int i = 0;
        for (Member member : members) {
            member.setName(String.format("testModify%02d", ++i));
            if (i % 2 == 0) {
                mms.get(i - 1).update(member);
            } else {
                mms.get(i - 1).merge(member);
            }
        }
        assertThat("modify member faild", mmB.findByCriteria("WHERE name like 'testModify%'").size(), is(greaterThanOrEqualTo(mms.size())));
        testSelect();
    }

    @Test(dependsOnMethods = "testModify")
    public void testRollback() {
        List<Member> members = mmB.findByCriteria("WHERE name like 'testModify%' ORDER BY name");
        int i = 0;
        for (Member member : members) {
            member.setName("RooBeck");
            try {
                if (i % 2 == 0) {
                    mms.get(i++).update(member);
                } else {
                    mms.get(i++).merge(member);
                }
            } catch (Exception e) {
            }
        }
        assertThat("test rollback member faild", mmB.findByCriteria("WHERE name like 'testModify%'").size(), is(greaterThanOrEqualTo(mms.size())));
    }

    @Test(dependsOnMethods = "testRollback")
    public void testRemove() {
        List<Member> members = mmB.findByCriteria("WHERE name like 'testModify%' ORDER BY name");
        int i = 0;
        for (Member member : members) {
            mms.get(i++).remove(member);
        }
        assertThat("remove member faild", mmB.findByCriteria("WHERE name like 'testModify%'").size(), is(equalTo(0)));
    }

    @Test
    public void testOrphanRemove() {
        String name = "Newbie";
        for (RepositoryManager<Member> m : mms) {
            Member newbie = new Member();
            newbie.setName(name);
            if (newbie.getPhones() == null) {
                newbie.setPhones(new HashSet<Phone>());
            }
            newbie.getPhones().add(new Phone(newbie, "123456789"));
            newbie.getPhones().add(new Phone(newbie, "098765432"));
            m.save(newbie);
            Member member = m.findFirstByCriteria("WHERE name = ?1", name);
            assertThat("Ophan insert failed", member, is(notNullValue()));
            assertThat("Ophan insert failed", member.getPhones().size(), is(equalTo(2)));
            m.remove(newbie);
            assertThat("Ophan delete failed", phoneManager.findByCriteria("WHERE owner is NULL").size(), is(equalTo(0)));
        }
    }

    @Test
    public void testNameQuery() {
        for (RepositoryManager<Member> m : mms) {
            List<Member> members = m.findByNamedQuery("Member.findByName", "%os%");
            assertThat("Can't find by named query.", members.size(), is(greaterThan(0)));
            members = m.findByNamedQuery("Member.findNativeByName", "%os%");
            assertThat("Can't find by named query.", members.size(), is(greaterThan(0)));
        }
    }
}
