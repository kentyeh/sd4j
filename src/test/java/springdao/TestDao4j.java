package springdao;

import springdao.reposotory.AnnotatedRepositoryManager;
import springdao.model.SupplyChainMember;
import java.util.HashSet;
import springdao.model.Phone;
import springdao.reposotory.AnnotherDaoRepository;
import springdao.reposotory.AnnotatedRepositoryManagerExt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.log4j.Log4j2;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import springdao.model.Member;
import springdao.reposotory.RepositoryManagerExt;
import springdao.support.JpqlHelper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import springdao.reposotory.CustomManager;

/**
 *
 * @author Kent Yeh
 */
@ContextConfiguration("classpath:testContext.xml")
@Log4j2
public class TestDao4j extends AbstractTestNGSpringContextTests {

    @Dao(value = Member.class, name = "annotherDao")
    private AnnotherDaoRepository anotherDao;
    @DaoManager(name = "MM1", daoName = "annotherDao")
    private RepositoryManager<Member> mm1;
    @DaoManager(name = "MM2", baseManagerType = RepositoryManagerExt.class)
    private RepositoryManager<Member> mm2;
    @DaoManager(name = "MM3")
    private RepositoryManagerExt<Member> mm3;
    @DaoManager(name = "MM4", baseManagerType = RepositoryManagerExt.class)
    private RepositoryManagerExt<Member> mm4;
    @DaoManager(name = "MM5", baseManagerType = AnnotatedRepositoryManager.class)
    private RepositoryManager<Member> mm5;
    @DaoManager(name = "MM6", baseManagerType = AnnotatedRepositoryManagerExt.class)
    private RepositoryManager<Member> mm6;
    @DaoManager(name = "MM7")
    private AnnotatedRepositoryManager<Member> mm7;
    @DaoManager(name = "MM8")
    private AnnotatedRepositoryManagerExt<Member> mm8;
    @DaoManager(name = "MM9", baseManagerType = AnnotatedRepositoryManagerExt.class)
    private AnnotatedRepositoryManager<Member> mm9;
    @DaoManager(name = "MMA", baseManagerType = AnnotatedRepositoryManager.class)
    private AnnotatedRepositoryManagerExt<Member> mmA;
    private RepositoryManager<Member> mmB;
    List<RepositoryManager<Member>> mms = new ArrayList<>();
    RepositoryManager<Member>[] mma;
    @DaoManager
    private RepositoryManager<Phone> phoneManager;
    @DaoManager
    private CustomManager<Member> mmC;
    List<Member> lazys = new ArrayList<>();
    private final AtomicInteger cnt = new AtomicInteger(0);

    @Dao(value = Member.class, name = "annotherDao2")
    public void setAnotherDao2(AnnotherDaoRepository anotherDao2) {
        this.anotherDao = anotherDao2;
    }

    @DaoManager(name = "MMB", baseManagerType = AnnotatedRepositoryManager.class)
    public void setMmB(RepositoryManager<Member> mmB) {
        this.mmB = mmB;
    }

    @BeforeClass
    public void setup() {
        assertThat("setter injection failed.", anotherDao, is(notNullValue()));

        assertThat("default RepositoryManager is not assigned.", mm1, is(notNullValue()));
        mms.add(mm1);
        assertThat("mm2 expect RepositoryManagerExt.class.", mm2, is(instanceOf(RepositoryManagerExt.class)));
        mm2.setDao(anotherDao);
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
        assertThat("mmC expect CustomManager.class.", mmC, is(instanceOf(CustomManager.class)));
        mms.add(mmC);
        mma = new RepositoryManager[mms.size()];
        mma = mms.toArray(mma);
    }

    @AfterClass
    public void tearDown() {
        for (RepositoryManager<Member> m : mms) {
            m.clear();
        }
    }

    @Test(invocationCount = 12, threadPoolSize = 5)
    public void testNew() throws InstantiationException, IllegalAccessException {
        int idx = cnt.getAndIncrement();
        RepositoryManager<Member> mm = mma[idx++];
        Member member = mm.instanate();
        member.setName(String.format("testNew%02d", idx));
        mm.save(member);
    }

    @Test(dependsOnMethods = "testNew")
    public void testColelction() throws InstantiationException, IllegalAccessException {
        List<Member> members = new ArrayList();
        Member mA = new Member();
        mA.setName("A");
        members.add(mA);
        Member mB = new Member();
        mB.setName("B");
        members.add(mB);
        Member mC = new Member();
        mC.setName("C");
        members.add(mC);
        RepositoryManager<Member> mgr = mms.get(2);
        Collection<Member> ms = mgr.save(members);
        assertThat("Save Collection failed.", ms, hasSize(3));
        for (Member m : ms) {
            m.setName("x" + m.getName());
        }
        ms = mgr.update(ms);
        assertThat("Update Collection failed.", ms, hasSize(3));

        Member mD = new Member();
        mD.setName("xD");
        ms.add(mD);
        ms = mgr.saveOrUpdate(ms);
        assertThat("SaveOrUpdate Collection failed.", ms, hasSize(4));

        assertThat("bulkUpdate failed.", 4, is(
                mgr.bulkUpdate(JpqlHelper.get().update(mgr.$ea()).set().eq(mgr.$a("userType"), "'V'")
                        .where(mgr.$a("name")).like().quot("x%").ql())));
        List<String> qls = new ArrayList<>();
        qls.add(JpqlHelper.get().update(mgr.$ea()).set().eq(mgr.$a("userType"), "'V'")
                        .where(mgr.$a("name")).like().quot("x%").ql());
        assertThat("bulkUpdate failed.", 4, is(
                mgr.bulkUpdate(qls).get(0)));
    }

    @Test(dependsOnMethods = "testColelction")
    public void testRemoveCollection() {
        RepositoryManager<Member> mgr = mms.get(2);
        Member member = mgr.findFirstByCriteria(JpqlHelper.get().where("name= ?1").ql(), "xD");
        assertThat("Can't find member['xD']", member, is(notNullValue()));
        member = mgr.remove(member);
        assertThat("Rmove member failed", member, is(notNullValue()));
        member = mgr.findFirstByCriteria(JpqlHelper.get().where("name= ?1").ql(), "xC");
        assertThat("Can't find member['xC']", member, is(notNullValue()));
        mgr.delete(member.getId());
        
        member = mgr.findFirstByCriteria(JpqlHelper.get().where("name= ?1").ql(), "xB");
        assertThat("Can't find member['xB']", member, is(notNullValue()));
        List<Long> ids = new ArrayList<>();
        ids.add(member.getId());
        mgr.delete(ids);

        Collection<Member> mms = mgr.findByCriteria(JpqlHelper.get().where("name").like().quot("x%").ql());
        assertThat("Can't find member like 'x%'", mms, hasSize(greaterThan(0)));
        mms = mgr.remove(mms);
        assertThat("Can't remove member like 'x%'", mms, hasSize(greaterThan(0)));
    }

    @Test(dependsOnMethods = "testNew")
    public void checkNew() {
        assertThat("Insert new member faild", mmB.findByCriteria(JpqlHelper.get().where("name like ?1").ql(), "testNew%").size(), is(greaterThanOrEqualTo(mms.size())));
        cnt.set(0);
    }

    @Test(dependsOnMethods = "checkNew")
    public void testSelect() {
        int i = 0;
        for (RepositoryManager<Member> m : mms) {
            Member member = m.findFirstByCriteria(JpqlHelper.get().where("name like ?1").ql(), new StringBuilder("test").append("%").append(String.format("%02d", ++i)).toString());
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

    @Test(dependsOnMethods = "testReattachAndInitCollection")
    public void testFindByPrimaryKey() {
        int i = 0;
        for (Member m : lazys) {
            if (m.getId() % 2 == 0) {
                assertThat("findByPrimaryKey(key) failed.", m, is(equalTo(mms.get(i++).findByPrimaryKey(m.getId()))));
            } else {
                log.error("{}.LOCK member({})", i, m.getId());
                try {
                    assertThat("findByPrimaryKey(key) failed.", m, is(equalTo(mms.get(i++).findByPrimaryKey(m.getId(), DaoManager.LOCK_OPTIMISTIC))));
                } catch (RuntimeException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
    }

    @Test
    public void testReattachAndInitCollection2() {
        Member lazy = mmA.findFirstByCriteria(JpqlHelper.get().where("name=?1").ql(), "Jose");
        mmA.initLazyCollection(lazy, "userstores");
        assertThat("init lazy collection failed", lazy.getUserstores().size(), is(greaterThan(0)));
    }

    @Test(dependsOnMethods = "testReattachAndInitCollection", invocationCount = 12, threadPoolSize = 5)
    public void testModify() {
        int idx = cnt.getAndIncrement();
        RepositoryManager<Member> mm = mma[idx++];
        String ql = JpqlHelper.get().where("name ='testNew%02d'").orderBy("name").format(idx);
        Member member = mm.findFirstByCriteria(ql);
        assertThat("can't find member:" + ql, member, is(notNullValue()));
        member.setName(String.format("testModify%02d", idx));
        if (idx % 2 == 0) {
            mm.update(member);
        } else {
            mm.merge(member);
        }
    }

    @Test(dependsOnMethods = "testModify")
    public void checkModify() {
        assertThat("modify member faild", mmB.findByCriteria(JpqlHelper.get().where("name like 'testModify%'").ql()).size(), is(greaterThanOrEqualTo(mms.size())));
        testSelect();
        cnt.set(0);
    }

    @Test(dependsOnMethods = "checkModify", invocationCount = 12, threadPoolSize = 5)
    public void testRollback() {
        int idx = cnt.getAndIncrement();
        RepositoryManager<Member> mm = mma[idx++];
        String ql = JpqlHelper.get().where("name ='testModify%02d'").orderBy("name").format(idx);
        Member member = mm.findFirstByCriteria(ql);
        assertThat("can't find member:" + ql, member, is(notNullValue()));
        member.setName("RooBeck");
        try {
            if (idx % 2 == 0) {
                mm.update(member);
            } else {
                mm.merge(member);
            }
        } catch (Exception e) {
        }
        assertThat(String.format("test rollback 'testModify%02d' faild", idx), mm.findFirstByCriteria(ql), is(notNullValue()));
    }

    @Test(dependsOnMethods = "testRollback")
    public void afterTestRollback() {
        cnt.set(0);
    }

    @Test(dependsOnMethods = "afterTestRollback", invocationCount = 12, threadPoolSize = 5)
    public void testRemove() {
        int idx = cnt.getAndIncrement();
        RepositoryManager<Member> mm = mma[idx++];
        String ql = JpqlHelper.get().where("name ='testModify%02d'").orderBy("name").format(idx);
        Member member = mm.findFirstByCriteria(ql);
        assertThat("can't find member:" + ql, member, is(notNullValue()));
        mm.delete(member.getId());
        assertThat(String.format("test remove 'testModify%02d' faild", idx), mm.findFirstByCriteria(ql), is(nullValue()));
    }

    @Test
    public void testOrphanRemove() throws InstantiationException, IllegalAccessException {
        String name = "Newbie";
        for (RepositoryManager<Member> m : mms) {
            Member newbie = m.instanate();
            newbie.setName(name);
            if (newbie.getPhones() == null) {
                newbie.setPhones(new HashSet<Phone>());
            }
            newbie.getPhones().add(new Phone("123456789", newbie));
            newbie.getPhones().add(new Phone("098765432", newbie));
            m.save(newbie);
            Member member = m.findFirstByCriteria(JpqlHelper.get().where("name = ?1").ql(), name);
            assertThat("Ophan insert failed", member, is(notNullValue()));
            assertThat("Ophan insert failed", member.getPhones().size(), is(equalTo(2)));
            m.delete(newbie.getId());
            assertThat("Ophan delete failed", phoneManager.findByCriteria(JpqlHelper.get().where("owner").isNull().ql()).size(), is(equalTo(0)));
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

    @Test(dependsOnMethods = "checkNew")
    public void testFindUniqueByQL() {
        for (RepositoryManager<Member> m : mms) {
            Long res = m.findUniqueByQL(Long.class, JpqlHelper.get().select().count(m.$a()).from(m.$ea()).ql());
            assertThat("Users' count must grater zero", res.intValue(), is(greaterThan(0)));
        }
    }

    @Test(dependsOnMethods = "checkNew")
    public void testFindUniqueByQL2() {
        for (RepositoryManager<Member> m : mms) {
            Object[] res = m.findUniqueByQL(null, JpqlHelper.get().select().count(m.$a()).cMax("name").from(m.$ea()).ql());
            assertThat("Multiple field query wrong", res.length, is(equalTo(2)));
        }
    }

    @Test(dependsOnMethods = "checkNew")
    public void testFindListByQL() {
        for (RepositoryManager<Member> m : mms) {
            List<Long> res = m.findListByQL(Long.class, JpqlHelper.get().select(m.$a("id")).from(m.$ea()).ql());
            assertThat("Free QL query wrong", res.size(), is(greaterThan(0)));
            assertThat("Free QL query wrong", res.get(0), is(greaterThanOrEqualTo(0l)));
        }
    }

    @Test(dependsOnMethods = "checkNew")
    public void testFindListByQL2() {
        for (RepositoryManager<Member> m : mms) {
            List<Object[]> res = m.findListByQL(null, JpqlHelper.get().select(m.$a("name")).$c(m.$a("userType")).from(m.$ea()).ql());
            assertThat("Free QL query wrong", res.size(), is(greaterThan(0)));
            assertThat("Free QL query wrong", (SupplyChainMember) res.get(0)[1], is(anyOf(is(SupplyChainMember.C), is(SupplyChainMember.V), is(SupplyChainMember.W))));
        }
    }
}
