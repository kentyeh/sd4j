/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springdao;

import lombok.extern.log4j.Log4j2;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import springdao.support.JpqlHelper;
import static springdao.support.JpqlHelper.$q;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import springdao.support.AliasHelper;

/**
 *
 * @author kent
 */
@ContextConfiguration("classpath:testContext.xml")
@Log4j2
public class TestJpqlHeloer extends AbstractTestNGSpringContextTests {

    private String hw = "Hello World!";
    private String $a = AliasHelper.$a(TestJpqlHeloer.class) + ".";
    private String $ea = AliasHelper.$ea(TestJpqlHeloer.class);

    private JpqlHelper $() {
        return new JpqlHelper();
    }

    @Test
    public void test$c() {
        assertThat($().$c().ql(), is(","));

    }

    @Test
    public void testQuot() {
        assertThat($().quot(hw).ql(), is("'" + hw + "'"));

    }

    @Test
    public void test$90() {
        assertThat($().$90(hw).ql(), is("(" + hw + ")"));
    }

    @Test
    public void testSelectDistinct() {
        assertThat($().select().distinct().$($a + hw).from($ea).where().$s90("1=1").or().$s90("2=2").ql(),
                is("SELECT DISTINCT " + $a + hw + " FROM " + $ea + " WHERE (1=1) OR (2=2)"));
    }

    @Test
    public void testSelectDistinct2() {
        assertThat($().select().distinct($a + hw).from($ea).where($a + hw).isNull().ql(),
                is("SELECT DISTINCT " + $a + hw + " FROM " + $ea + " WHERE " + $a + hw + " IS NULL"));
    }

    @Test
    public void testFrom() {
        assertThat($().selectDistinct().$($a + hw).from().$($ea).where($a + hw).isNotNull().ql(),
                is("SELECT DISTINCT " + $a + hw + " FROM " + $ea + " WHERE " + $a + hw + " IS NOT NULL"));
    }

    @Test
    public void testWhere() {
        assertThat($().selectDistinct().$($a + hw).from($ea).where("1=2").ql(), is("SELECT DISTINCT " + $a + hw + " FROM " + $ea + " WHERE 1=2"));
    }

    @Test
    public void testCase() {
        assertThat($().select().$($a + hw).from($ea).where("1=").Case($ea + "field").whenThen($q(hw), "1").elseEnd("0").ql(),
                is("SELECT " + $a + hw + " FROM " + $ea + " WHERE 1= CASE " + $ea + "field WHEN " + $q(hw) + " THEN 1 ELSE 0 END"));
    }

    @Test
    public void testCase2() {
        assertThat($().select().Case().$("field").whenThen("1", "True").elseEnd("False")
                .from($ea).where("field").between().$("1").and().$("10").ql(),
                is("SELECT CASE field WHEN 1 THEN True ELSE False END FROM " + $ea + " WHERE field BETWEEN 1 AND 10"));
    }

    @Test
    public void testCase3() {
        assertThat($().select().$("F1").cCase().$("field").whenThen("1", "True").elseEnd("False")
                .from($ea).where("field").between("1","10").ql(),
                is("SELECT F1 ,CASE field WHEN 1 THEN True ELSE False END FROM " + $ea+
                        " WHERE field BETWEEN 1 AND 10"));
    }

    @Test
    public void testCase4() {
        assertThat($().select().$("F1").cCase("field").whenThen("1", "True").elseEnd("False")
                .from($ea).where("F1").like().$($q("x%")).ql(),
                is("SELECT F1 ,CASE field WHEN 1 THEN True ELSE False END FROM " + $ea+
                        " WHERE F1 LIKE 'x%'"));
    }

    @Test
    public void testCase5() {
        assertThat($().select().$("F1").cCase("field").whenThen("1", "True").Else("False").end()
                .from($ea).where().like("F1", $q("x%")).ql(),
                is("SELECT F1 ,CASE field WHEN 1 THEN True ELSE False END FROM " + $ea
                +" WHERE F1 LIKE 'x%'"));
    }

    @Test
    public void testCase6() {
        assertThat($().select().$("F1").cCase("field").when().$("1").then().$("True").Else("False").end().from($ea).ql(),
                is("SELECT F1 ,CASE field WHEN 1 THEN True ELSE False END FROM " + $ea));
    }

    @Test
    public void testUpdate() {
        assertThat($().update().$("Tab").set().$("nField").eq().$("3").where("bField").eq().True().ql(),
                is("UPDATE Tab SET nField = 3 WHERE bField = True"));
    }

    @Test
    public void testUpdate2() {
        assertThat($().update("Tab").set().$("nField").eq().$("3").where("bField").eq().False().ql(),
                is("UPDATE Tab SET nField = 3 WHERE bField = False"));
    }

    @Test
    public void testDelete() {
        assertThat($().delete().$("Tab").where().$("nField").eq().$("3").where("bField").not().True().ql(),
                is("DELETE FROM Tab WHERE nField = 3 WHERE bField NOT True"));
    }

    @Test
    public void testDelete2() {
        assertThat($().delete("Tab").where().$("nField").eq().$("3").where("1=1").and().$("2=2").ql(),
                is("DELETE FROM Tab WHERE nField = 3 WHERE 1=1 AND 2=2"));
    }

    @Test
    public void testJoin() {
        assertThat($().select().$("F1").from("Tab1").join().$("Tab2").on().eq("Tab1.id", "Tab2.id").where("F1").eq().$("3").or().eq("F1","5").ql(),
                is("SELECT F1 FROM Tab1 JOIN Tab2 ON Tab1.id = Tab2.id WHERE F1 = 3 OR F1 = 5"));
    }

    @Test
    public void testJoin2() {
        assertThat($().select().$("F1").from("Tab1").join("Tab2").on().eq("Tab1.id", "Tab2.id").where("F1").in().$s90("3,5").ql(),
                is("SELECT F1 FROM Tab1 JOIN Tab2 ON Tab1.id = Tab2.id WHERE F1 IN (3,5)"));
    }

    @Test
    public void testInnerJoin() {
        assertThat($().select().$("F1").cAbs("F1").as(" PF1").from("Tab1").innerJoin().$("Tab2").on().eq("Tab1.id", "Tab2.id")
                .where("F1").in("3,5").or().abs("F1").$(">10").ql(),
                is("SELECT F1 ,ABS(F1) AS PF1 FROM Tab1 INNER JOIN Tab2 ON Tab1.id = Tab2.id WHERE F1 IN (3,5) OR ABS(F1) >10"));
    }

    @Test
    public void testInnerJoin2() {
        assertThat($().select().$("F1").from("Tab1").innerJoin("Tab2").on().eq("Tab1.id", "Tab2.id").ql(),
                is("SELECT F1 FROM Tab1 INNER JOIN Tab2 ON Tab1.id = Tab2.id"));
    }

    @Test
    public void testLeftJoin() {
        assertThat($().select().$("F1").from("Tab1").leftJoin().$("Tab2").on().eq("Tab1.id", "Tab2.id").ql(),
                is("SELECT F1 FROM Tab1 LEFT OUTER JOIN Tab2 ON Tab1.id = Tab2.id"));
    }

    @Test
    public void testLeftJoin2() {
        assertThat($().select().$("F1").from("Tab1").leftJoin("Tab2").on().eq("Tab1.id", "Tab2.id").ql(),
                is("SELECT F1 FROM Tab1 LEFT OUTER JOIN Tab2 ON Tab1.id = Tab2.id"));
    }

    @Test
    public void testUnion() {
        assertThat($().select().$("F1").from("Tab1").union().select().$("F1").from("Tab2").ql(),
                is("SELECT F1 FROM Tab1 UNION SELECT F1 FROM Tab2"));
    }

    @Test
    public void testJoinFetch1(){
        assertThat($().select("Member").from("Member").join().fetch().$("Member.phones").where("Member.phones").isNotEmpty()
                .or().$($q("0987654321")).memberOf().$("Member.phones").ql(),
                is("SELECT Member FROM Member JOIN FETCH Member.phones WHERE Member.phones IS NOT EMPTY"
                        + " OR '0987654321' MEMBER OF Member.phones"));
    }
    
    @Test
    public void testJoinFetch2(){
        assertThat($().select("Member").from("Member").join().fetch("Member.phones").where("Member.phones").isEmpty()
                .or().memberOf($q("0987654321"), "Member.phones").ql(),
                is("SELECT Member FROM Member JOIN FETCH Member.phones WHERE Member.phones IS EMPTY"
                        + " OR '0987654321' MEMBER OF Member.phones"));
    }
    @Test
    public void testAggreate() {
        assertThat($().select().$("F1").$c().count().$90("8").as("cnt").$c().abs().$90("F1").$c()
                .avg().$90("F1").$c().max().$90("F1").$c().min().$90("F1")
                .from("Tab1").groupBy().$("F1").having().count().$90("8").$(">1").orderBy().$("1").asc().ql(),
                is("SELECT F1 , COUNT(8) AS cnt , ABS (F1) , AVG (F1) , MAX (F1) , MIN (F1) "
                        + "FROM Tab1 GROUP BY F1 HAVING COUNT(8) >1 ORDER BY 1 ASC"));
    }

    @Test
    public void testAggreate2() {
        assertThat($().select().$("F1").cCount().$90("8").as("cnt").cAbs().$90("F1")
                .cAvg().$90("F1").cMax().$90("F1").cMin().$90("F1")
                .from("Tab1").groupBy("F1").having().count().$90("8").$(">1").orderBy().$("1").asc().ql(),
                is("SELECT F1 ,COUNT(8) AS cnt ,ABS (F1) ,AVG (F1) ,MAX (F1) ,MIN (F1)"
                        + " FROM Tab1 GROUP BY F1 HAVING COUNT(8) >1 ORDER BY 1 ASC"));
    }

    @Test
    public void testAggreate3() {
        assertThat($().select("F1").cCount("8").as("cnt").cAbs("F1").cAvg("F1").cMax("F1").cMin("F1")
                .from("Tab1").groupBy().$("F1").having("COUNT(8) >1").orderBy("1").asc().ql(),
                is("SELECT F1 ,COUNT(8) AS cnt ,ABS(F1) ,AVG(F1) ,MAX(F1) ,MIN(F1)"
                        + " FROM Tab1 GROUP BY F1 HAVING COUNT(8) >1 ORDER BY 1 ASC"));
    }

}
