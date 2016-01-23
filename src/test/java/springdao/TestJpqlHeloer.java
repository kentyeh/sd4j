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
    public void test$90(String s) {
        assertThat($().$90(hw).ql(), is("(" + hw + ")"));
    }

    @Test
    public void testSelectDistinct() {
        assertThat($().selectDistinct().$($a + hw).from($ea).ql(), is("SELECT DISTINCT " + $a + hw + " FROM " + $ea));
    }

    @Test
    public void testSelectDistinct2() {
        assertThat($().selectDistinct($a + hw).from($ea).ql(), is("SELECT DISTINCT " + $a + hw + " FROM " + $ea));
    }

    @Test
    public void testFrom() {
        assertThat($().selectDistinct().$($a + hw).from().$($ea).ql(), is("SELECT DISTINCT " + $a + hw + " FROM " + $ea));
    }
    
    @Test
    public void testWhere() {
        assertThat($().selectDistinct().$($a + hw).from($ea).where("1=2").ql(), is("SELECT DISTINCT " + $a + hw + " FROM " + $ea+" WHERE 1=2"));
    }

}
