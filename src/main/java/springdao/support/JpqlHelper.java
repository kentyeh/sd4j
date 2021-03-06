package springdao.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kent Yeh
 */
public class JpqlHelper {

    private static final Logger logger = LogManager.getLogger(JpqlHelper.class);
    private final StringBuilder sb = new StringBuilder();

    /**
     * Factory method to get a instance of JpqlHelper.<br/>
     * 取得一個新建的instance
     *
     * @return {@link JpqlHelper this}
     */
    public static JpqlHelper get() {
        return new JpqlHelper();
    }

    /**
     * Quote string within two single quote &apos;string&apos;.<br/>
     * 字串前後加單引號
     *
     * @param s
     * @return 's'
     */
    public static String $q(String s) {
        return "'" + s + "'";
    }

    public JpqlHelper() {
    }

    /**
     * add String.<br/>
     * 加入字串
     *
     * @param s 字串
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper $(String s) {
        sb.append(" ").append(s).append(" ");
        return this;
    }

    /**
     * append a comma &quot;,&quot;.<br/>
     * 加入一個逗點(,)
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper c$() {
        sb.append(" ,");
        return this;
    }

    /**
     * append string prefix a comma &quot;,&quot;.<br/>
     * 加入一個逗點後再加字串
     *
     * @param s
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper c$(String s) {
        sb.append(",").append(s).append(" ");
        return this;
    }

    /**
     * Quote string within two single quote &apos;string&apos;.<br/>
     * 用兩個單引號夾住字串
     *
     * @param s
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper q$(String s) {
        sb.append(" '").append(s).append("' ");
        return this;
    }

    /**
     * add &quot;(..string..)&quot;.<br/>
     * 將字串置於括號內 &quot;(..string..)&quot;
     *
     * @param s
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper ps$(String s) {
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append("(").append(s).append(") ");
        return this;
    }

    /**
     * add left parentheses &quot;&nbsp;(;.<br/>
     * 加入左括號 &quot;(&quot;
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper lp$() {
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append("(");
        return this;
    }

    /**
     * add right parentheses &quot;)&quot;.<br/>
     * 加入右括號 &quot;)&quot;
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper rp$() {
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 1, sb.length());
        }
        sb.append(")");
        return this;
    }

    /**
     * add
     * &quot;<span style="background-color:yellow">&nbsp;</span>(..string..)&quot;
     *
     * @param s
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper sps$(String s) {
        sb.append(" (").append(s).append(") ");
        return this;
    }

    /**
     * append a string <span style="color:blue">SELECT</span>.<br/>
     * 加入一個<span style="color:blue">SELECT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper select() {
        return $("SELECT");
    }

    /**
     * append a string
     * <span style="color:blue">SELECT</span>&nbsp;<span style="color:#FF8000">fields</span>.<br/>
     * 加入一個<span style="color:blue">SELECT</span>&nbsp;<span style="color:#FF8000">fields</span>字串
     *
     * @param fields
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper select(String fields) {
        return $("SELECT " + fields);
    }

    /**
     * append a string <span style="color:blue">SELECT DISTINCT</span>.<br/>
     * 加入一個<span style="color:blue">SELECT DISTINCT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper selectDistinct() {
        return $("SELECT DISTINCT");
    }

    /**
     * append a string <span style="color:blue">SELECT
     * DISTINCT</span>&nbsp;<span style="color:#FF8000">fields</span>.<br/>
     * 加入一個<span style="color:blue">SELECT
     * DISTINCT</span>&nbsp;<span style="color:#FF8000">fields</span>字串
     *
     * @param fields
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper selectDistinct(String fields) {
        return $("SELECT DISTINCT " + fields);
    }

    /**
     * append a string <span style="color:blue">FROM</span>.<br/>
     * 加入一個<span style="color:blue">FROM</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper from() {
        return $("FROM");
    }

    /**
     * append a string
     * <span style="color:blue">FROM</span>&nbsp;<span style="color:#FF8000">entity</span>.<br/>
     * 加入一個<span style="color:blue">FROM</span>&nbsp;<span style="color:#FF8000">entity</span>字串
     *
     * @param entity
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper from(String entity) {
        return $("FROM " + entity);
    }

    /**
     * append a string <span style="color:blue">WHERE</span>.<br/>
     * 加入一個<span style="color:blue">WHERE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper where() {
        return $("WHERE");
    }

    /**
     * append a string
     * <span style="color:blue">WHERE</span>&nbsp;<span style="color:#FF8000">qlCriteria</span>.<br/>
     * 加入一個<span style="color:blue">WHERE</span>&nbsp;<span style="color:#FF8000">qlCriteria</span>字串
     *
     * @param qlCriteria
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper where(String qlCriteria) {
        return $("WHERE " + qlCriteria);
    }

    /**
     * append a string
     * <span style="color:blue">CASE</span>.<br/>
     * 加入一個<span style="color:blue">CASE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper Case() {
        return $("CASE");
    }

    /**
     * append a string
     * <span style="color:blue">,CASE</span>.<br/>
     * 加入一個<span style="color:blue">,CASE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCase() {
        return $(",CASE");
    }

    /**
     * append a string
     * <span style="color:blue">CASE</span>&nbsp;<span style="color:#FF8000">field</span>.<br/>
     * 加入一個<span style="color:blue">CASE</span>&nbsp;<span style="color:#FF8000">field</span>字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper Case(String field) {
        return $("CASE " + field);
    }

    /**
     * append a string
     * <span style="color:blue">,CASE</span>&nbsp;<span style="color:#FF8000">field</span>.<br/>
     * 加入一個<span style="color:blue">,CASE</span>&nbsp;<span style="color:#FF8000">field</span>字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCase(String field) {
        return $(",CASE " + field);
    }

    /**
     * append a string
     * <span style="color:blue">WHEN</span>.<br/>
     * 加入一個<span style="color:blue">WHEN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper when() {
        return $("WHEN");
    }

    /**
     * append a string
     * <span style="color:blue">THEN</span>.<br/>
     * 加入一個<span style="color:blue">THEN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper then() {
        return $("THEN");
    }

    /**
     * append a string
     * <span style="color:blue">WHEN</span>&nbsp;<span style="color:#FF8000">condition</span>&nbsp;THEN&nbsp;<span style="color:#FF8000">result</span>.<br/>
     * 加入一個<span style="color:blue">WHEN</span>&nbsp;<span style="color:#FF8000">condition</span>&nbsp;THEN&nbsp;<span style="color:#FF8000">result</span>字串
     *
     * @param condition
     * @param result
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper whenThen(String condition, String result) {
        return $("WHEN " + condition + " THEN " + result);
    }

    /**
     * append a string
     * <span style="color:blue">ELSE</span>&nbsp;<span style="color:#FF8000">result</span>.<br/>
     * 加入一個<span style="color:blue">ELSE</span>&nbsp;<<span style="color:#FF8000">result</span>字串
     *
     * @param result
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper Else(String result) {
        return $("ELSE " + result);
    }

    /**
     * append a string
     * <span style="color:blue">ELSE</span>&nbsp;<span style="color:#FF8000">result</span>&nbsp;<span style="color:blue">END</span>.<br/>
     * 加入一個<span style="color:blue">ELSE</span>&nbsp;<<span style="color:#FF8000">result</span>&nbsp;<span style="color:blue">END</span>字串
     *
     * @param result
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper elseEnd(String result) {
        return $("ELSE " + result + " END");
    }

    /**
     * append a string
     * <span style="color:blue">END</span>.<br/>
     * 加入一個<span style="color:blue">END</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper end() {
        return $("END");
    }

    /**
     * append a string <span style="color:blue">UPDATE</span>.<br/>
     * 加入一個<span style="color:blue">UPDATE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper update() {
        return $("UPDATE");
    }

    /**
     * append a string
     * <span style="color:blue">UPDATE</span>&nbsp;<span style="color:#FF8000">entity</span>.<br/>
     * 加入一個<span style="color:blue">UPDATE</span>&nbsp;<span style="color:#FF8000">entity</span>字串
     *
     * @param entity
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper update(String entity) {
        return $("UPDATE " + entity);
    }

    /**
     * append a string <span style="color:blue">DELETE</span>.<br/>
     * 加入一個<span style="color:blue">DELETE&nbsp;FROM</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper delete() {
        return $("DELETE FROM");
    }

    /**
     * append a string
     * <span style="color:blue">DELETE</span>&nbsp;<span style="color:#FF8000">entity</span>.<br/>
     * 加入一個<span style="color:blue">DELETE&nbsp;FROM</span>&nbsp;<span style="color:#FF8000">entity</span>字串
     *
     * @param entity
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper delete(String entity) {
        return $("DELETE FROM " + entity);
    }

    /**
     * append a string <span style="color:blue">JOIN</span>.<br/>
     * 加入一個<span style="color:blue">JOIN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper join() {
        return $("JOIN");
    }

    /**
     * append a string
     * <span style="color:blue">JOIN</span>&nbsp;<span style="color:#FF8000">other</span>.<br/>
     * 加入一個<span style="color:blue">JOIN</span>&nbsp;<span style="color:#FF8000">other</span>字串
     *
     * @param other
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper join(String other) {
        return $("JOIN " + other);
    }

    /**
     * append a string <span style="color:blue">INNER JOIN</span>.<br/>
     * 加入一個<span style="color:blue">INNER JOIN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper innerJoin() {
        return $("INNER JOIN");
    }

    /**
     * append a string <span style="color:blue">INNER
     * JOIN</span>&nbsp;<span style="color:#FF8000">other</span>.<br/>
     * 加入一個<span style="color:blue">INNER
     * JOIN</span>&nbsp;<span style="color:#FF8000">other</span>字串
     *
     * @param other
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper innerJoin(String other) {
        return $("INNER JOIN " + other);
    }

    /**
     * append a string <span style="color:blue">LEFT OUTER JOIN</span>.<br/>
     * 加入一個<span style="color:blue">LEFT OUTER JOIN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper leftJoin() {
        return $("LEFT OUTER JOIN");
    }

    /**
     * append a string <span style="color:blue">LEFT&nbsp;OUTER&nbsp;
     * JOIN</span>&nbsp;<span style="color:#FF8000">other</span>.<br/>
     * 加入一個<span style="color:blue">LEFT&nbsp;OUTER&nbsp;
     * JOIN</span>&nbsp;<span style="color:#FF8000">other</span>字串
     *
     * @param other
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper leftJoin(String other) {
        return $("LEFT OUTER JOIN " + other);
    }

    /**
     * append a string <span style="color:blue">ON</span>.<br/>
     * 加入一個<span style="color:blue">ON</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper on() {
        return $("ON");
    }

    /**
     * append a string <span style="color:blue">UNION</span>.<br/>
     * 加入一個<span style="color:blue">UNION</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper union() {
        return $("UNION");
    }

    /**
     * append a string <span style="color:blue">INTERSECT</span>.<br/>
     * 加入一個<span style="color:blue">INTERSECT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper intersect() {
        return $("INTERSECT");
    }

    /**
     * append a string <span style="color:blue">EXCEPT</span>.<br/>
     * 加入一個<span style="color:blue">EXCEPT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper except() {
        return $("EXCEPT");
    }

    /**
     * append a string <span style="color:blue">GROUP BY</span>.<br/>
     * 加入一個<span style="color:blue">GROUP BY</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper groupBy() {
        return $("GROUP BY");
    }

    /**
     * append a string <span style="color:blue">GROUP
     * BY</span>&nbsp;<span style="color:#FF8000">fields</span>.<br/>
     * 加入一個<span style="color:blue">GROUP
     * BY</span>&nbsp;<span style="color:#FF8000">fields</span>字串
     *
     * @param fields
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper groupBy(String fields) {
        return $("GROUP BY " + fields);
    }

    /**
     * append a string <span style="color:blue">HAVING</span>.<br/>
     * 加入一個<span style="color:blue">HAVING</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper having() {
        return $("HAVING");
    }

    /**
     * append a string
     * <span style="color:blue">HAVING</span>&nbsp;<span style="color:#FF8000">criteria</span>.<br/>
     * 加入一個<span style="color:blue">HAVING</span>&nbsp;<span style="color:#FF8000">criteria</span>字串
     *
     * @param criteria
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper having(String criteria) {
        return $("HAVING " + criteria);
    }

    /**
     * append a string <span style="color:blue">FETCH</span>.<br/>
     * 加入一個<span style="color:blue">FETCH</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper fetch() {
        return $("FETCH");
    }

    /**
     * append a string
     * <span style="color:blue">FETCH</span>&nbsp;<span style="color:#FF8000">field</span>.<br/>
     * 加入一個<span style="color:blue">FETCH</span>&nbsp;<span style="color:#FF8000">field</span>字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper fetch(String field) {
        return $("FETCH " + field);
    }

    /**
     * append a string <span style="color:blue">DISTINCT</span>.<br/>
     * 加入一個<span style="color:blue">DISTINCT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper distinct() {
        return $("DISTINCT");
    }

    /**
     * append a string
     * <span style="color:blue">DISTINCT</span>&nbsp;<span style="color:#FF8000">fields</span>.<br/>
     * 加入一個<span style="color:blue">DISTINCT</span>&nbsp;<span style="color:#FF8000">fields</span>字串
     *
     * @param fields
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper distinct(String fields) {
        return $("DISTINCT " + fields);
    }

    /**
     * append a string <span style="color:blue">IS&nbsp;NULL</span>.<br/>
     * 加入一個<span style="color:blue">IS&nbsp;NULL</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper isNull() {
        return $("IS NULL");
    }

    /**
     * append a string
     * <span style="color:blue">IS&nbsp;NOT&nbsp;NULL</span>.<br/>
     * 加入一個<span style="color:blue">IS&nbsp;NOT&nbsp;NULL</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper isNotNull() {
        return $("IS NOT NULL");
    }

    /**
     * append a string <span style="color:blue">TRUE</span>.<br/>
     * 加入一個<span style="color:blue">TRUE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper True() {
        return $("True");
    }

    /**
     * append a string <span style="color:blue">FALSE</span>.<br/>
     * 加入一個<span style="color:blue">FALSE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper False() {
        return $("False");
    }

    /**
     * append a string <span style="color:blue">NOT</span>.<br/>
     * 加入一個<span style="color:blue">NOT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper not() {
        return $("NOT");
    }

    /**
     * append a string <span style="color:blue">AND</span>.<br/>
     * 加入一個<span style="color:blue">AND</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper and() {
        return $("AND");
    }

    /**
     * append a string <span style="color:blue">OR</span>.<br/>
     * 加入一個<span style="color:blue">OR</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper or() {
        return $("OR");
    }

    /**
     * append a string <span style="color:blue">BETWEEN</span>.<br/>
     * 加入一個<span style="color:blue">BETWEEN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper between() {
        return $("BETWEEN");
    }

    /**
     * append a string
     * <span style="color:blue">BETWEEN</span>&nbsp;<span style="color:#FF8000">from</span>&nbsp;<span style="color:blue">AND</span>&nbsp;<span style="color:#FF8000">to</span>.<br/>
     * 加入一個<span style="color:blue">BETWEEN</span>&nbsp;<span style="color:#FF8000">from</span>&nbsp;<span style="color:blue">AND</span>&nbsp;<span style="color:#FF8000">to</span>字串
     *
     * @param from
     * @param to
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper between(String from, String to) {
        return $("BETWEEN " + from + " AND " + to);
    }

    /**
     * append a string <span style="color:blue">LIKE</span>.<br/>
     * 加入一個<span style="color:blue">LIKE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper like() {
        return $("LIKE");
    }

    /**
     * append a string
     * <span style="color:#FF8000">left</span>&nbsp;<span style="color:blue">LIKE</span>&nbsp;<span style="color:#FF8000">right</span>.<br/>
     * 加入一個<span style="color:#FF8000">left</span>&nbsp;<span style="color:blue">LIKE</span>&nbsp;<span style="color:#FF8000">right</span>字串
     *
     * @param left
     * @param right
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper like(String left, String right) {
        return $(left + " LIKE " + right);
    }

    /**
     * append a string <span style="color:blue">IN</span>.<br/>
     * 加入一個<span style="color:blue">IN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper in() {
        return $("IN");
    }

    /**
     * append a string
     * <span style="color:blue">IN</span>&nbsp;(<span style="color:#FF8000">fields</span>).<br/>
     * 加入一個<span style="color:blue">IN&nbsp;(<span style="color:#FF8000">fields</span>)</span>字串
     *
     * @param fields
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper in(String fields) {
        return $("IN (" + fields + ")");
    }

    /**
     * append a string
     * <span style="color:blue">AS</span>&nbsp;<span style="color:#FF8000">field</span>.<br/>
     * 加入一個<span style="color:blue">AS</span>&nbsp;<span style="color:#FF8000">fields</span>字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper as(String field) {
        return $("AS " + field);
    }

    /**
     * append a string <span style="color:blue">IS&nbsp;EMPTY</span>.<br/>
     * 加入一個<span style="color:blue">IS&nbsp;EMPTY</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper isEmpty() {
        return $("IS EMPTY");
    }

    /**
     * append a string
     * <span style="color:blue">IS&nbsp;NOT&nbsp;EMPTY</span>.<br/>
     * 加入一個<span style="color:blue">IS&nbsp;NOT&nbsp;EMPTY</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper isNotEmpty() {
        return $("IS NOT EMPTY");
    }

    /**
     * append a string <span style="color:blue">MEMBER&nbsp;OF</span>.<br/>
     * 加入一個<span style="color:blue">MEMBER&nbsp;OF</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper memberOf() {
        return $("MEMBER OF");
    }

    /**
     * append a string
     * <span style="color:#FF8000">left</span>&nbsp;<span style="color:blue">MEMBER&nbsp;OF</span>&nbsp;<span style="color:#FF8000">right</span>.<br/>
     * 加入一個<span style="color:#FF8000">left</span>&nbsp;<span style="color:blue">MEMBER&nbsp;OF</span>&nbsp;<span style="color:#FF8000">right</span>字串
     *
     * @param left
     * @param right
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper memberOf(String left, String right) {
        return $(left + " MEMBER OF " + right);
    }

    /**
     * append a string
     * <span style="color:blue">ABS</span>.<br/>
     * 加入一個<span style="color:blue">ABS</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper abs() {
        return $("ABS");
    }

    /**
     * append a string
     * <span style="color:blue">,ABS</span>.<br/>
     * 加入一個<span style="color:blue">,ABS</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cAbs() {
        return $(",ABS");
    }

    /**
     * append a string
     * <span style="color:blue">ABS</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">ABS</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper abs(String field) {
        return $("ABS(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,ABS</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,ABS</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cAbs(String field) {
        return $(",ABS(" + field + ")");
    }

    /**
     * append a string <span style="color:blue">AVG</span>.<br/>
     * 加入一個<span style="color:blue">AVG</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper avg() {
        return $("AVG");
    }

    /**
     * append a string <span style="color:blue">,AVG</span>.<br/>
     * 加入一個<span style="color:blue">,AVG</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cAvg() {
        return $(",AVG");
    }

    /**
     * append a string
     * <span style="color:blue">AVG</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">AVG</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper avg(String field) {
        return $("AVG(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,AVG</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,AVG</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cAvg(String field) {
        return $(",AVG(" + field + ")");
    }

    /**
     * append a string <span style="color:blue">SQRT</span>.<br/>
     * 加入一個<span style="color:blue">SQRT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper sqrt() {
        return $("SQRT");
    }

    /**
     * append a string <span style="color:blue">,SQRT</span>.<br/>
     * 加入一個<span style="color:blue">,SQRT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cSqrt() {
        return $(",SQRT");
    }

    /**
     * append a string
     * <span style="color:blue">SQRT</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">SQRT</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper sqrt(String field) {
        return $("SQRT(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,SQRT</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,SQRT</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cSqrt(String field) {
        return $(",SQRT(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">COALESCE</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">defaultValue</span>).<br/>
     * 加入一個<span style="color:blue">COALESCE</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">defaultValue</span>)字串
     *
     * @param field
     * @param defaultValue
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper coalesce(String field, String defaultValue) {
        return $("COALESCE(" + field + "," + defaultValue + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,COALESCE</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">defaultValue</span>).<br/>
     * 加入一個<span style="color:blue">,COALESCE</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">defaultValue</span>)字串
     *
     * @param field
     * @param defaultValue
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCoalesce(String field, String defaultValue) {
        return $(",COALESCE(" + field + "," + defaultValue + ")");
    }

    /**
     * append a string
     * <span style="color:blue">NULLIF</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">defaultValue</span>).<br/>
     * 加入一個<span style="color:blue">NULLIF</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">defaultValue</span>)字串
     *
     * @param field
     * @param defaultValue
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper nullif(String field, String defaultValue) {
        return $("NULLIF(" + field + "," + defaultValue + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,NULLIF</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">defaultValue</span>).<br/>
     * 加入一個<span style="color:blue">,NULLIF</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">defaultValue</span>)字串
     *
     * @param field
     * @param defaultValue
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cNullif(String field, String defaultValue) {
        return $(",NULLIF(" + field + "," + defaultValue + ")");
    }

    /**
     * append a string <span style="color:blue">MAX</span>.<br/>
     * 加入一個<span style="color:blue">MAX</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper max() {
        return $("MAX");
    }

    /**
     * append a string <span style="color:blue">,MAX</span>.<br/>
     * 加入一個<span style="color:blue">,MAX</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cMax() {
        return $(",MAX");
    }

    /**
     * append a string
     * <span style="color:blue">MAX</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">MAX</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper max(String field) {
        return $("MAX(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,MAX</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,MAX</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cMax(String field) {
        return $(",MAX(" + field + ")");
    }

    /**
     * append a string <span style="color:blue">MIN</span>.<br/>
     * 加入一個<span style="color:blue">MIN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper min() {
        return $("MIN");
    }

    /**
     * append a string <span style="color:blue">,MIN</span>.<br/>
     * 加入一個<span style="color:blue">,MIN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cMin() {
        return $(",MIN");
    }

    /**
     * append a string
     * <span style="color:blue">MIN</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">MIN</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper min(String field) {
        return $("MIN(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,MIN</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,MIN</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cMin(String field) {
        return $(",MIN(" + field + ")");
    }

    /**
     * append a string <span style="color:blue">SUM</span>.<br/>
     * 加入一個<span style="color:blue">SUM</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper sum() {
        return $("SUM");
    }

    /**
     * append a string <span style="color:blue">,SUM</span>.<br/>
     * 加入一個<span style="color:blue">,SUM</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cSum() {
        return $(",SUM");
    }

    /**
     * append a string
     * <span style="color:blue">SUM</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">SUM</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper sum(String field) {
        return $("SUM(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,SUM</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,SUM</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cSum(String field) {
        return $(",SUM(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">COUNT</span>.<br/>
     * 加入一個<span style="color:blue">COUNT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper count() {
        sb.append(" COUNT");
        return this;
    }

    /**
     * append a string
     * <span style="color:blue">,COUNT</span>.<br/>
     * 加入一個<span style="color:blue">,COUNT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCount() {
        sb.append(" ,COUNT");
        return this;
    }

    /**
     * append a string
     * <span style="color:blue">COUNT</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">COUNT</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper count(String field) {
        return $("COUNT(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,COUNT</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,COUNT</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCount(String field) {
        return $(",COUNT(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">COUNT</span>(DISTINCT&nbsp;<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">COUNT</span>(DISTINCT&nbsp;<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper countDistinct(String field) {
        return $("COUNT(DISTINCT " + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,COUNT</span>(DISTINCT&nbsp;<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,COUNT</span>(DISTINCT&nbsp;<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCountDistinct(String field) {
        return $(",COUNT(DISTINCT " + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">ORDER&nbsp;BY</span>.<br/>
     * 加入一個<span style="color:blue">ORDER&nbsp;BY</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper orderBy() {
        return $("ORDER BY");
    }

    /**
     * append a string
     * <span style="color:blue">NULLS&nbsp;FIRST</span>.<br/>
     * 加入一個<span style="color:blue">NULLS&nbsp;FIRST</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper nullsFirst() {
        return $("NULLS FIRST");
    }

    /**
     * append a string
     * <span style="color:blue">ORDER&nbsp;BY&nbsp;<span style="color:#FF8000">fields</span></span>.<br/>
     * 加入一個<span style="color:blue">ORDER&nbsp;BY&nbsp;<span style="color:#FF8000">fields</span></span>字串
     *
     * @param fields
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper orderBy(String fields) {
        return $("ORDER BY " + fields);
    }

    /**
     * append a string
     * <span style="color:blue">ASC</span>.<br/>
     * 加入一個<span style="color:blue">ASC</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper asc() {
        return $("ASC");
    }

    /**
     * append a string
     * <span style="color:blue">DESC</span>.<br/>
     * 加入一個<span style="color:blue">DESC</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper desc() {
        return $("DESC");
    }

    /**
     * append a string
     * <span style="color:blue">MOD</span>(<span style="color:#FF8000">number</span>,<span style="color:#FF8000">divisor</span>).<br/>
     * 加入一個<span style="color:blue">MOD</span>(<span style="color:#FF8000">number</span>,<span style="color:#FF8000">divisor</span>)字串
     *
     * @param number
     * @param divisor
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper mod(String number, String divisor) {
        return $("MOD(" + number + ", " + divisor + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,MOD</span>(<span style="color:#FF8000">number</span>,<span style="color:#FF8000">divisor</span>).<br/>
     * 加入一個<span style="color:blue">mMOD</span>(<span style="color:#FF8000">number</span>,<span style="color:#FF8000">divisor</span>)字串
     *
     * @param number
     * @param divisor
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cMod(String number, String divisor) {
        return $(",MOD(" + number + ", " + divisor + ")");
    }

    /**
     * append a string
     * <span style="color:blue">UPPER</span>.<br/>
     * 加入一個<span style="color:blue">UPPER</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper upper() {
        return $("UPPER");
    }

    /**
     * append a string
     * <span style="color:blue">,UPPER</span>.<br/>
     * 加入一個<span style="color:blue">,UPPER</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cUpper() {
        return $(",UPPER");
    }

    /**
     * append a string
     * <span style="color:blue">UPPER</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">UPPER</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper upper(String field) {
        return $("UPPER(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,UPPER</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,UPPER</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cUpper(String field) {
        return $(",UPPER(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">LOWER</span>.<br/>
     * 加入一個<span style="color:blue">LOWER</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper lower() {
        return $("LOWER");
    }

    /**
     * append a string
     * <span style="color:blue">,LOWER</span>.<br/>
     * 加入一個<span style="color:blue">,LOWER</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cLower() {
        return $(",LOWER");
    }

    /**
     * append a string
     * <span style="color:blue">LOWER</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">LOWER</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper lower(String field) {
        return $("LOWER(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,LOWER</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,LOWER</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cLower(String field) {
        return $(",LOWER(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">TRIM</span>.<br/>
     * 加入一個<span style="color:blue">TRIM</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper trim() {
        return $("TRIM");
    }

    /**
     * append a string
     * <span style="color:blue">,TRIM</span>.<br/>
     * 加入一個<span style="color:blue">,TRIM</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cTrim() {
        return $(",TRIM");
    }

    /**
     * append a string
     * <span style="color:blue">TRIM</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">TRIM</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper trim(String field) {
        return $("TRIM(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,TRIM</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,TRIM</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cTrim(String field) {
        return $(",TRIM(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">CONCAT</span>(<span style="color:#FF8000">first</span>&nbsp;,&nbsp;
     * <span style="color:#FF8000">second</span>).<br/>
     * 加入一個<span style="color:blue">CONCAT</span>(<span style="color:#FF8000">first</span>&nbsp;,&nbsp;
     * <span style="color:#FF8000">second</span>)字串
     *
     * @param first
     * @param second
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper concat(String first, String second) {
        return $("CONCAT(" + first + ", " + second + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,CONCAT</span>(<span style="color:#FF8000">first</span>&nbsp;,&nbsp;
     * <span style="color:#FF8000">second</span>).<br/>
     * 加入一個<span style="color:blue">,CONCAT</span>(<span style="color:#FF8000">first</span>&nbsp;,&nbsp;
     * <span style="color:#FF8000">second</span>)字串
     *
     * @param first
     * @param second
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cConcat(String first, String second) {
        return $(",CONCAT(" + first + ", " + second + ")");
    }

    /**
     * append a string
     * <span style="color:blue">SUBSTRING</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;
     * <span style="color:#FF8000">start</span>&nbsp;,&nbsp;<span style="color:#FF8000">end</span>).<br/>
     * 加入一個<span style="color:blue">SUBSTRING</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;
     * <span style="color:#FF8000">start</span>&nbsp;,&nbsp;<span style="color:#FF8000">end</span>)字串
     *
     * @param field
     * @param start
     * @param end
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper substring(String field, int start, int end) {
        return $("SUBSTRING(" + field + ", " + start + ", " + end + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,SUBSTRING</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;
     * <span style="color:#FF8000">start</span>&nbsp;,&nbsp;<span style="color:#FF8000">end</span>).<br/>
     * 加入一個<span style="color:blue">,SUBSTRING</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;
     * <span style="color:#FF8000">start</span>&nbsp;,&nbsp;<span style="color:#FF8000">end</span>)字串
     *
     * @param field
     * @param start
     * @param end
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cSubstring(String field, int start, int end) {
        return $(",SUBSTRING(" + field + ", " + start + ", " + end + ")");
    }

    /**
     * append a string
     * <span style="color:blue">LOCATE</span>(<span style="color:#FF8000">substr</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">LOCATE</span>(<span style="color:#FF8000">substr</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>)字串
     *
     * @param substr
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper locate(String substr, String field) {
        return $("LOCATE(" + substr + ", " + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,LOCATE</span>(<span style="color:#FF8000">substr</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,LOCATE</span>(<span style="color:#FF8000">substr</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>)字串
     *
     * @param substr
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cLocate(String substr, String field) {
        return $(",LOCATE(" + substr + ", " + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">LENGTH</span>.<br/>
     * 加入一個<span style="color:blue">LENGTH</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper length() {
        return $("LENGTH");
    }

    /**
     * append a string
     * <span style="color:blue">,LENGTH</span>.<br/>
     * 加入一個<span style="color:blue">,LENGTH</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cLength() {
        return $(",LENGTH");
    }

    /**
     * append a string
     * <span style="color:blue">LENGTH</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">LENGTH</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper length(String field) {
        return $("LENGTH(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,LENGTH</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,LENGTH</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cLength(String field) {
        return $(",LENGTH(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">CURRENT_TIME</span>.<br/>
     * 加入一個<span style="color:blue">CURRENT_TIME</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper currTime() {
        return $("CURRENT_TIME");
    }

    /**
     * append a string
     * <span style="color:blue">,CURRENT_TIME</span>.<br/>
     * 加入一個<span style="color:blue">,CURRENT_TIME</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCurrTime() {
        return $(",CURRENT_TIME");
    }

    /**
     * append a string
     * <span style="color:blue">CURRENT_DATE</span>.<br/>
     * 加入一個<span style="color:blue">CURRENT_DATE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper currDate() {
        return $("CURRENT_DATE");
    }

    /**
     * append a string
     * <span style="color:blue">,CURRENT_DATE</span>.<br/>
     * 加入一個<span style="color:blue">,CURRENT_DATE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCurrDate() {
        return $(",CURRENT_DATE");
    }

    /**
     * append a string
     * <span style="color:blue">CURRENT_TIMESTAMP</span>.<br/>
     * 加入一個<span style="color:blue">CURRENT_TIMESTAMP</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper currTimeStamp() {
        return $("CURRENT_TIMESTAMP");
    }

    /**
     * append a string
     * <span style="color:blue">,CURRENT_TIMESTAMP</span>.<br/>
     * 加入一個<span style="color:blue">,CURRENT_TIMESTAMP</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCurrTimeStamp() {
        return $(",CURRENT_TIMESTAMP");
    }

    /**
     * append a string <span style="color:blue">NEW</span>.<br/>
     * 加入一個<span style="color:blue">NEW</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper New() {
        return $("NEW");
    }

    /**
     * append &quot;&nbsp;<span style="color:blue">NEW</span>&nbsp;
     * <span style="color:#FF8000">entityName</span>(<span style="color:#FF8000">field1</span>
     * [,<span style="color:#FF8000">field2</span>])&nbsp;&quot;.<br/>
     * 加入&quot;&nbsp;<span style="color:blue">NEW</span>&nbsp;
     * <span style="color:#FF8000">entityName</span>(<span style="color:#FF8000">field1</span>
     * [,<span style="color:#FF8000">field2</span>])&nbsp;&quot;
     *
     * @param entityName
     * @param fields
     * @return NEW entityName,field1[,field2[,field3[...]]]
     */
    public JpqlHelper New(String entityName, String... fields) {
        sb.append(" NEW ").append(entityName).append("(");
        if (fields != null && fields.length > 0) {
            int i = 0;
            for (String field : fields) {
                sb.append(i++ == 0 ? "" : ", ").append(field);
            }
            sb.append(") ");
        }
        return this;
    }

    /**
     * append a string
     * <span style="color:blue">EXISTS</span>.<br/>
     * 加入一個<span style="color:blue">EXISTS</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper exists() {
        return $("EXISTS");
    }

    /**
     * append a string
     * <span style="color:blue">NOT&nbsp;EXISTS</span>.<br/>
     * 加入一個<span style="color:blue">NOT&nbsp;EXISTS</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper notExists() {
        return $("NOT EXISTS");
    }

    /**
     * append a string
     * <span style="color:blue">ALL</span>.<br/>
     * 加入一個<span style="color:blue">ALL</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper all() {
        return $("ALL");
    }

    /**
     * append a string
     * <span style="color:blue">ALL</span>(<span style="color:#FF8000">subquery</span>).<br/>
     * 加入一個<span style="color:blue">ALL</span>(<span style="color:#FF8000">subquery</span>)字串
     *
     * @param subquery
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper all(String subquery) {
        return $("ALL(" + subquery + ")");
    }

    /**
     * append a string
     * <span style="color:blue">ANY</span>.<br/>
     * 加入一個<span style="color:blue">ANY</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper any() {
        return $("ANY");
    }

    /**
     * append a string
     * <span style="color:blue">ANY</span>(<span style="color:#FF8000">subquery</span>).<br/>
     * 加入一個<span style="color:blue">ANY</span>(<span style="color:#FF8000">subquery</span>)字串
     *
     * @param subquery
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper any(String subquery) {
        return $("ANY(" + subquery + ")");
    }

    /**
     * append a string
     * <span style="color:blue">SOME</span>.<br/>
     * 加入一個<span style="color:blue">SOME</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper some() {
        return $("SOME");
    }

    /**
     * append a string
     * <span style="color:blue">SOME</span>(<span style="color:#FF8000">subquery</span>).<br/>
     * 加入一個<span style="color:blue">SOME</span>(<span style="color:#FF8000">subquery</span>)字串
     *
     * @param subquery
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper some(String subquery) {
        return $("SOME(" + subquery + ")");
    }

    /**
     * append a string
     * <span style="color:blue">SET</span>.<br/>
     * 加入一個<span style="color:blue">SET</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper set() {
        return $("SET");
    }

    /**
     * append a string
     * <span style="color:blue">SET</span>&nbsp;<span style="color:#FF8000">left</span><span style="color:blue">=</span><span style="color:#FF8000">right</span>.<br/>
     * 加入一個<span style="color:blue">SET</span>&nbsp;<span style="color:#FF8000">left</span><span style="color:blue">=</span><span style="color:#FF8000">right</span>字串
     *
     * @param left
     * @param right
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper set(String left, String right) {
        return $("SET " + left + " = " + right);
    }

    /**
     * append a string
     * <span style="color:blue">=</span>.<br/>
     * 加入一個<span style="color:blue">=</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper eq() {
        return $("=");
    }

    /**
     * append a string
     * <span style="color:blue">=</span><span style="color:#FF8000">right</span>.<br/>
     * 加入一個<span style="color:blue">=</span><span style="color:#FF8000">right</span>字串
     *
     * @param right
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper eq(String right) {
        return $(" = " + right);
    }

    /**
     * append a string
     * <span style="color:#FF8000">left</span><span style="color:blue">=</span><span style="color:#FF8000">right</span>.<br/>
     * 加入一個<span style="color:#FF8000">left</span><span style="color:blue">=</span><span style="color:#FF8000">right</span>字串
     *
     * @param left
     * @param right
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper eq(String left, String right) {
        return $(left + " = " + right);
    }

    /**
     *
     * @param args A
     * <a href="http://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html#syntax">format
     * string</a>
     * @return String.format({@link #toString() toString()},args);
     */
    public String format(Object... args) {
        return String.format(toString(), args);
    }

    /**
     * EclipseLink support:<br/>
     * append a string
     * <span style="color:blue">CAST</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">typeValue</span>).<br/>
     * 加入一個<span style="color:blue">CAST</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">typeValue</span>)字串
     *
     * @param field
     * @param typeValue
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cast(String field, String typeValue) {
        return $("CAST(" + field + ", " + typeValue + ")");
    }

    /**
     * EclipseLink support:<br/>
     * append a string
     * <span style="color:blue">EXTRACT</span>(<span style="color:#FF8000">dt</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">EXTRACT</span>(<span style="color:#FF8000">dt</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>)字串
     *
     * @param dt
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper extract(String dt, String field) {
        return $("EXTRACT(" + dt + ", " + field + ")");
    }

    /**
     * EclipseLink support:<br/>
     * append a string
     * <span style="color:blue">,EXTRACT</span>(<span style="color:#FF8000">dt</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,EXTRACT</span>(<span style="color:#FF8000">dt</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>)字串
     *
     * @param dt
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cExtract(String dt, String field) {
        return $(",EXTRACT(" + dt + ", " + field + ")");
    }

    /**
     * append &quot;&nbsp;<span style="color:blue">FUNCTION(</span>
     * <span style="color:#FF8000">name</span>,&nbsp;[<span style="color:#FF8000">value1,
     * [<span style="color:#FF8000">field2</span>]</span>]&quot;.<br/>
     * 加入&quot;&nbsp;<span style="color:blue">FUNCTION(</span>
     * <span style="color:#FF8000">name</span>,&nbsp;[<span style="color:#FF8000">value1,
     * [<span style="color:#FF8000">field2</span>]</span>]&quot;
     *
     * @param name
     * @param values
     * @return FUNCTION('name',valu1[,valu2[,valu3[...]]])
     */
    public JpqlHelper function(String name, String... values) {
        sb.append(" ").append("FUNCTION('").append(name).append("'");
        if (values != null && values.length > 0) {
            for (String value : values) {
                sb.append(", ").append(value);
            }
        }
        sb.append(")");
        return this;
    }

    /**
     * EclipseLink support:<br/>
     * OPERATOR is similar to
     * {@link #function(java.lang.String, java.lang.String...) function()}.<br/>
     * OPERATOR 相似於
     * {@link #function(java.lang.String, java.lang.String...) function()}
     *
     * @param name
     * @param values
     * @return OPERATOR('name',valu1[,valu2[,valu3[...]]])
     */
    public JpqlHelper operator(String name, String... values) {
        sb.append(" ").append("OPERATOR('").append(name).append("'");
        if (values != null && values.length > 0) {
            for (String value : values) {
                sb.append(", ").append(value);
            }
        }
        sb.append(")");
        return this;
    }

    /**
     * append
     * <span style="color:blue">TREAT</span>(<span style="color:#FF8000">entity</span>).<br/>
     * 加入
     * <span style="color:blue">TREAT</span>(<span style="color:#FF8000">entity</span>)
     * 字串
     *
     * @param entity
     * @return TREAT(entity)
     */
    public JpqlHelper treat(String entity) {
        return $("TREAT(" + entity + ")");
    }

    /**
     * EclipseLink support:<br/>
     * append a string <span style="color:blue">REGEXP</span>.<br/>
     * 加入一個<span style="color:blue">REGEXP</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper regexp() {
        return $("REGEXP");
    }

    /**
     * append a string
     * <span style="color:#FF8000">field</span>&nbsp;<span style="color:blue">REGEXP</span>&nbsp;<span style="color:#FF8000">regexpr</span>.<br/>
     * 加入一個<span style="color:#FF8000">field</span>&nbsp;<span style="color:blue">REGEXP</span>&nbsp;<span style="color:#FF8000">regexpr</span>字串
     *
     * @param field
     * @param regexpr
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper regexp(String field, String regexpr) {
        return $(field + " REGEXP " + regexpr);
    }

    /**
     * EclipseLink support:<br/>
     * append a string
     * <span style="color:#FF8000">COLUMN</span>('<span style="color:#FF8000">field</span>,&nbsp;<span style="color:#FF8000">entityOrAlias</span>).<br/>
     * 加入<span style="color:#FF8000">COLUMN</span>('<span style="color:#FF8000">field</span>,&nbsp;<span style="color:#FF8000">entityOrAlias</span>).<br/>字串
     *
     * @param field
     * @param entityOrAlias
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper column(String field, String entityOrAlias) {
        return $("COLUMN('" + field + "', " + entityOrAlias + ")");
    }

    /**
     * EclipseLink support:<br/>
     * append
     * <span style="color:blue">TABLE</span>(<span style="color:#FF8000">name</span>).<br/>
     * 加入
     * <span style="color:blue">TABLE</span>(<span style="color:#FF8000">name</span>)
     * 字串
     *
     * @param name
     * @return TABLE('name')
     */
    public JpqlHelper table(String name) {
        return $("TABLE('" + name + "')");
    }

    @Override
    public String toString() {
        int pos = 0;
        while ((pos = sb.indexOf("  ", pos)) != -1) {
            sb.delete(pos, pos + 1);
        }
        logger.debug("JPQL:{}", sb.toString());
        while (sb.length() > 0 && sb.charAt(0) == ' ') {
            sb.delete(0, 1);
        }
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 1, sb.length());
        }
        return sb.toString();
    }

    /**
     * Alias for {@link #toString() toString()}.<br/>
     * 輸入JPQL
     *
     * @return {@link #toString() toString()}
     */
    public String ql() {
        return toString();
    }

}
