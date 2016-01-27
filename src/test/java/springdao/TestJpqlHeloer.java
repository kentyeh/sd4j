/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package springdao;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import springdao.support.JpqlHelper;
import static springdao.support.JpqlHelper.$q;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import springdao.support.AliasHelper;

/**
 *
 * @author kent
 */
@Log4j2
public class TestJpqlHeloer {

    private final String hw = "Hello World!";
    private final String $a = AliasHelper.$a(TestJpqlHeloer.class) + ".";
    private final String $ea = AliasHelper.$ea(TestJpqlHeloer.class);

    private JpqlHelper $() {
        return JpqlHelper.get();
    }

    @Test
    public void test$c() {
        assertThat($().c$().ql(), is(","));
    }

    @Test
    public void test$c2() {
        assertThat($().c$(hw).ql(), is("," + hw));
    }

    @Test
    public void testQuot() {
        assertThat($().q$(hw).ql(), is("'" + hw + "'"));

    }

    @Test
    public void test$ps() {
        assertThat($().ps$("%s").format(hw), is("(" + hw + ")"));
    }

    @Test
    public void testSelectDistinct() {
        assertThat($().select().distinct().$($a + hw).from($ea).where().sps$("1=1").or().sps$("2=2").ql(),
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
        assertThat($().selectDistinct($a + hw).from($ea).where("1=2").ql(), is("SELECT DISTINCT " + $a + hw + " FROM " + $ea + " WHERE 1=2"));
    }

    @Test
    public void testCase() {
        assertThat($().select().$($a + hw).from($ea).where("1=").Case($ea + "field").whenThen($q(hw), "1").elseEnd("0").ql(),
                is("SELECT " + $a + hw + " FROM " + $ea + " WHERE 1= CASE " + $ea + "field WHEN " + $q(hw) + " THEN 1 ELSE 0 END"));
    }

    @Test
    public void testCase2() {
        assertThat($().select("F1").c$().sqrt().ps$("F1")
                .c$().Case().$("field").whenThen("1", "True").elseEnd("False")
                .from($ea).where("field").between().$("1").and().$("10").ql(),
                is("SELECT F1 , SQRT(F1) , CASE field WHEN 1 THEN True ELSE False END FROM "
                        + $ea + " WHERE field BETWEEN 1 AND 10"));
    }

    @Test
    public void testCase3() {
        assertThat($().select().$("F1").cSqrt().ps$("F1")
                .cCase().$("field").whenThen("1", "True").elseEnd("False")
                .from($ea).where("field").between("1", "10").ql(),
                is("SELECT F1 ,SQRT(F1) ,CASE field WHEN 1 THEN True ELSE False END FROM " + $ea
                        + " WHERE field BETWEEN 1 AND 10"));
    }

    @Test
    public void testCase4() {
        assertThat($().select().$("F1").c$().mod("F1", "3").c$().sqrt("F1").c$().coalesce("F2", $q("VALUE")).c$().nullif("F3", $q("F3Val"))
                .cCase("field").whenThen("1", "True").elseEnd("False")
                .from($ea).where("F1").like().$($q("x%")).ql(),
                is("SELECT F1 , MOD(F1, 3) , SQRT(F1) , COALESCE(F2,'VALUE') , NULLIF(F3,'F3Val') "
                        + ",CASE field WHEN 1 THEN True ELSE False END FROM " + $ea
                        + " WHERE F1 LIKE 'x%'"));
    }

    @Test
    public void testCase5() {
        assertThat($().select().$("F1").cMod("F1", "3").cSqrt("F1").cCoalesce("F2", $q("VALUE")).cNullif("F3", $q("F3Val"))
                .cCase("field").whenThen("1", "True").Else("False").end()
                .from($ea).where().like("F1", $q("x%")).ql(),
                is("SELECT F1 ,MOD(F1, 3) ,SQRT(F1) ,COALESCE(F2,'VALUE') ,NULLIF(F3,'F3Val') "
                        + ",CASE field WHEN 1 THEN True ELSE False END FROM " + $ea
                        + " WHERE F1 LIKE 'x%'"));
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
        assertThat($().update("Tab").set("nField", "3").where("bField").eq().False().ql(),
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
        assertThat($().select().$("F1").from("Tab1").join().$("Tab2").on().eq("Tab1.id", "Tab2.id").where("F1").eq().$("3").or().eq("F1", "5").ql(),
                is("SELECT F1 FROM Tab1 JOIN Tab2 ON Tab1.id = Tab2.id WHERE F1 = 3 OR F1 = 5"));
    }

    @Test
    public void testJoin2() {
        assertThat($().select().$("F1").from("Tab1").join("Tab2").on().eq("Tab1.id", "Tab2.id").where("F1").in().sps$("3,5").ql(),
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
    public void testIntersect() {
        assertThat($().select("e").from("Employee").as("e").join("e.phones").as("p")
                .where().eq("p.areaCode", ":areaCode1").intersect()
                .select("e").from("Employee").as("e").join("e.phones").as("p")
                .where().eq("p.areaCode", ":areaCode2").ql(),
                is("SELECT e FROM Employee AS e JOIN e.phones AS p WHERE p.areaCode = :areaCode1"
                        + " INTERSECT SELECT e FROM Employee AS e JOIN e.phones AS p WHERE p.areaCode = :areaCode2"));
    }
    
    @Test
    public void testExcept(){
        assertThat($().select("e").from("Employee").as("e").except().select("e")
                .from("Employee").as("e").where("e.salary").$("> e.manager.salary").ql(), 
                is("SELECT e FROM Employee AS e EXCEPT SELECT e FROM Employee AS e WHERE e.salary > e.manager.salary"));
    }

    @Test
    public void testJoinFetch1() {
        assertThat($().select("Member").from("Member").join().fetch().$("Member.phones").where("Member.phones").isNotEmpty()
                .or().$($q("0987654321")).memberOf().$("Member.phones").ql(),
                is("SELECT Member FROM Member JOIN FETCH Member.phones WHERE Member.phones IS NOT EMPTY"
                        + " OR '0987654321' MEMBER OF Member.phones"));
    }

    @Test
    public void testJoinFetch2() {
        assertThat($().select("Member").from("Member").join().fetch("Member.phones").where("Member.phones").isEmpty()
                .or().memberOf($q("0987654321"), "Member.phones").ql(),
                is("SELECT Member FROM Member JOIN FETCH Member.phones WHERE Member.phones IS EMPTY"
                        + " OR '0987654321' MEMBER OF Member.phones"));
    }

    @Test
    public void testAggreate() {
        assertThat($().select().$("F1").c$().count().ps$("8").as("cnt").c$().abs().ps$("F1").c$()
                .avg().ps$("F1").c$().avg("F2").c$().max().ps$("F1").c$().min().ps$("F1").c$().sum().ps$("F1")
                .from("Tab1").groupBy().$("F1").having().count().ps$("8").$(">1").orderBy().$("1").asc().nullsFirst().ql(),
                is("SELECT F1 , COUNT(8) AS cnt , ABS(F1) , AVG(F1) , AVG(F2) , MAX(F1) , MIN(F1) , SUM(F1) FROM Tab1 "
                        + "GROUP BY F1 HAVING COUNT(8) >1 ORDER BY 1 ASC NULLS FIRST"));
    }

    @Test
    public void testAggreate2() {
        assertThat($().select().$("F1").c$().count("8").as("cnt").c$().countDistinct("F1").c$().abs().ps$("F1").c$()
                .avg().ps$("F1").c$().avg("F2").c$().max("F1").c$().min("F1").c$().sum("F1")
                .from("Tab1").groupBy().$("F1").having().count().ps$("8").$(">1").orderBy().$("1").asc().ql(),
                is("SELECT F1 , COUNT(8) AS cnt , COUNT(DISTINCT F1) , ABS(F1) , AVG(F1) , AVG(F2) , MAX(F1) , MIN(F1) , SUM(F1) FROM Tab1 "
                        + "GROUP BY F1 HAVING COUNT(8) >1 ORDER BY 1 ASC"));
    }

    @Test
    public void testAggreate3() {
        assertThat($().select().$("F1").cCount().ps$("8").as("cnt").cCountDistinct("F1").cAbs().ps$("F1")
                .cAvg().ps$("F1").cMax().ps$("F1").cMin().ps$("F1").cSum().ps$("F1")
                .from("Tab1").groupBy("F1").having().count().ps$("8").$(">1").orderBy().$("1").desc().ql(),
                is("SELECT F1 ,COUNT(8) AS cnt ,COUNT(DISTINCT F1) ,ABS(F1) ,AVG(F1) ,MAX(F1) ,MIN(F1) ,SUM(F1) FROM Tab1 "
                        + "GROUP BY F1 HAVING COUNT(8) >1 ORDER BY 1 DESC"));
    }

    @Test
    public void testAggreate4() {
        assertThat($().select("F1").cCount("8").as("cnt").cAbs("F1").cAvg("F1").cMax("F1").cMin("F1").cSum("F1")
                .from("Tab1").groupBy().$("F1").having("COUNT(8) >1").orderBy("1").asc().ql(),
                is("SELECT F1 ,COUNT(8) AS cnt ,ABS(F1) ,AVG(F1) ,MAX(F1) ,MIN(F1) ,SUM(F1)"
                        + " FROM Tab1 GROUP BY F1 HAVING COUNT(8) >1 ORDER BY 1 ASC"));
    }

    @Test
    public void testStringFun() {
        assertThat($().select().upper().ps$("F1").c$().lower().ps$("F1").c$().trim().ps$("F1")
                .c$().length().ps$("F1").c$().currTime().c$().currDate().c$().currTimeStamp()
                .from("Tab").ql(),
                is("SELECT UPPER(F1) , LOWER(F1) , TRIM(F1) , LENGTH(F1) , CURRENT_TIME ,"
                        + " CURRENT_DATE , CURRENT_TIMESTAMP FROM Tab"));
    }

    @Test
    public void testStringFun2() {
        assertThat($().select("F1").cUpper().ps$("F1").cLower().ps$("F1").cTrim().ps$("F1")
                .cLength().ps$("F1").cCurrTime().cCurrDate().cCurrTimeStamp()
                .from("Tab").ql(),
                is("SELECT F1 ,UPPER(F1) ,LOWER(F1) ,TRIM(F1) ,LENGTH(F1) "
                        + ",CURRENT_TIME ,CURRENT_DATE ,CURRENT_TIMESTAMP FROM Tab"));
    }

    @Test
    public void testStringFun3() {
        assertThat($().select().upper("F1").c$().lower("F1").c$().trim("F1").c$().concat("F1", "F2")
                .c$().substring("F1", 1, 3).c$().locate($q("abc"), "F1").c$().length("F1")
                .from("Tab").ql(),
                is("SELECT UPPER(F1) , LOWER(F1) , TRIM(F1) , CONCAT(F1, F2) , SUBSTRING(F1, 1, 3) "
                        + ", LOCATE('abc', F1) , LENGTH(F1) FROM Tab"));
    }

    @Test
    public void testStringFun4() {
        assertThat($().select("F1").cUpper("F1").cLower("F1").cTrim("F1").cConcat("F1", "F2")
                .cSubstring("F1", 1, 3).cLocate($q("abc"), "F1").cLength("F1")
                .from("Tab").ql(),
                is("SELECT F1 ,UPPER(F1) ,LOWER(F1) ,TRIM(F1) ,CONCAT(F1, F2) ,SUBSTRING(F1, 1, 3) "
                        + ",LOCATE('abc', F1) ,LENGTH(F1) FROM Tab"));
    }

    @Test
    public void testNew() {
        assertThat($().select().New().$("com.company.PublisherInfo").ps$("pub.id, pub.revenue, mag.price")
                .from("Publisher").as("pub").join("pub.magazines").as("mag").where("mag.price > 5.00").ql(),
                is("SELECT NEW com.company.PublisherInfo(pub.id, pub.revenue, mag.price) "
                        + "FROM Publisher AS pub JOIN pub.magazines AS mag WHERE mag.price > 5.00"));
    }

    @Test
    public void testNew2() {
        assertThat($().select().New("com.company.PublisherInfo", "pub.id", "pub.revenue", "mag.price")
                .from("Publisher").as("pub").join("pub.magazines").as("mag").where("mag.price > 5.00").ql(),
                is("SELECT NEW com.company.PublisherInfo(pub.id, pub.revenue, mag.price) "
                        + "FROM Publisher AS pub JOIN pub.magazines AS mag WHERE mag.price > 5.00"));
    }

    @Test
    public void testExists() {
        assertThat($().select("e").from("Professor").as("e").where().exists().lp$().select("p")
                .from("Phone").as("p").where("p.employee = e").rp$().ql(),
                is("SELECT e FROM Professor AS e WHERE EXISTS( SELECT p FROM Phone AS p WHERE p.employee = e)"));
    }

    @Test
    public void testNotExists() {
        assertThat($().select("e").from("Professor").as("e").where().notExists().lp$().select("p")
                .from("Phone").as("p").where("p.employee = e").rp$().ql(),
                is("SELECT e FROM Professor AS e WHERE NOT EXISTS( SELECT p FROM Phone AS p WHERE p.employee = e)"));
    }

    @Test
    public void testAll() {
        assertThat($().select("auth").from("Author").as("auth").where("auth.salary >=").all().lp$()
                .select("a.salary").from("Author").as("a").where().eq("a.magazine", "auth.magazine").rp$().ql(),
                is("SELECT auth FROM Author AS auth WHERE auth.salary >= ALL( SELECT a.salary FROM Author AS a "
                        + "WHERE a.magazine = auth.magazine)"));
    }

    @Test
    public void testAll2() {
        assertThat($().select("auth").from("Author").as("auth").where("auth.salary >=").all(
                $().select("a.salary").from("Author").as("a").where().eq("a.magazine", "auth.magazine").ql())
                .ql(),
                is("SELECT auth FROM Author AS auth WHERE auth.salary >= ALL(SELECT a.salary FROM Author AS a "
                        + "WHERE a.magazine = auth.magazine)"));
    }

    @Test
    public void testAny() {
        assertThat($().select("auth").from("Author").as("auth").where("auth.salary >=").any().lp$()
                .select("a.salary").from("Author").as("a").where().eq("a.magazine", "auth.magazine").rp$().ql(),
                is("SELECT auth FROM Author AS auth WHERE auth.salary >= ANY( SELECT a.salary FROM Author AS a "
                        + "WHERE a.magazine = auth.magazine)"));
    }

    @Test
    public void testAny2() {
        assertThat($().select("auth").from("Author").as("auth").where("auth.salary >=").any(
                $().select("a.salary").from("Author").as("a").where().eq("a.magazine", "auth.magazine").ql())
                .ql(),
                is("SELECT auth FROM Author AS auth WHERE auth.salary >= ANY(SELECT a.salary FROM Author AS a "
                        + "WHERE a.magazine = auth.magazine)"));
    }

    @Test
    public void testSome() {
        assertThat($().select("auth").from("Author").as("auth").where("auth.salary >=").some().lp$()
                .select("a.salary").from("Author").as("a").where().eq("a.magazine", "auth.magazine").rp$().ql(),
                is("SELECT auth FROM Author AS auth WHERE auth.salary >= SOME( SELECT a.salary FROM Author AS a "
                        + "WHERE a.magazine = auth.magazine)"));
    }

    @Test
    public void testSome2() {
        assertThat($().select("auth").from("Author").as("auth").where("auth.salary >=").some(
                $().select("a.salary").from("Author").as("a").where().eq("a.magazine", "auth.magazine").ql())
                .ql(),
                is("SELECT auth FROM Author AS auth WHERE auth.salary >= SOME(SELECT a.salary FROM Author AS a "
                        + "WHERE a.magazine = auth.magazine)"));
    }

    @Test
    public void testCast() {
        assertThat($().select("f").from("Foo").as("f").join().cast("f.bars", "BarSubclass")
                .as("b").where().eq("b.subclassAttribute", $q("value")).ql(),
                is("SELECT f FROM Foo AS f JOIN CAST(f.bars, BarSubclass) AS b WHERE b.subclassAttribute = 'value'"));
    }

    @Test
    public void testExtract() {
        assertThat($().select().extract("YEAR", "e.startDate").as("e.year").cExtract("MONTH", "e.startDate")
                .as("e.month").from("Employee").as("e").ql(),
                is("SELECT EXTRACT(YEAR, e.startDate) AS e.year ,EXTRACT(MONTH, e.startDate) AS e.month FROM Employee AS e"));
    }

    @Test
    public void testFucntion() {
        assertThat($().select("s").from("SimpleSpatial").as("s").where()
                .function("MDSYS.SDO_RELATE", "s.jGeometry", ":otherGeometry", ":params")
                .eq().$($q("TRUE")).orderBy("s.id").asc().ql(),
                is("SELECT s FROM SimpleSpatial AS s WHERE FUNCTION('MDSYS.SDO_RELATE', s.jGeometry, :otherGeometry, :params) = 'TRUE' ORDER BY s.id ASC"));
    }
    @Test
    public void testOperator() {
        assertThat($().select("e").from("Employee").as("e").where()
                .operator("MDSYS.ExtractXml", "e.resume", $q("@years-experience"))
                .eq().$($q("TRUE")).orderBy("s.id").asc().ql(),
                is("SELECT e FROM Employee AS e WHERE OPERATOR('MDSYS.ExtractXml', e.resume, '@years-experience') = 'TRUE' ORDER BY s.id ASC"));
    }

    @Test
    public void testTreat() {
        assertThat($().select().count("m").from("MainEntity").join().treat("m.list AS Derived1").as("der1")
                .join().treat("m.list AS Derived2").as("der2").ql(),
                is("SELECT COUNT(m) FROM MainEntity JOIN TREAT(m.list AS Derived1) AS der1 JOIN TREAT(m.list AS Derived2) AS der2"));
    }

    @Test
    public void testRegex() {
        assertThat($().select("m").from("Member").as("m").where("firstName").regexp().$($q("Dr\\.*"))
                .or().regexp("lastName", $q("Dr\\.*")).ql(),
                is("SELECT m FROM Member AS m WHERE firstName REGEXP 'Dr\\.*' OR lastName REGEXP 'Dr\\.*'"));
    }
    
    @Test
    public void testColumn(){
        assertThat($().select("e").from("Employee").as("e").where().column("ROWID", "e").$("= :id").ql(), 
                is("SELECT e FROM Employee AS e WHERE COLUMN('ROWID', e) = :id"));
    }
    
    @Test
    public void testTable(){
        assertThat($().select("e").c$("a.LAST_UPDATE_USER").from("Employee").as("e").c$()
                .table("AUDIT").as("a").where().eq("a.TABLE",$q("EMPLOYEE")).and().
                $("a.ROWID =").column("ROWID", "e").ql(), 
                is("SELECT e ,a.LAST_UPDATE_USER FROM Employee AS e , TABLE('AUDIT') AS a "
                        + "WHERE a.TABLE = 'EMPLOYEE' AND a.ROWID = COLUMN('ROWID', e)"));
    }

}
