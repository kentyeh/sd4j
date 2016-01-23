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
    public JpqlHelper $c() {
        sb.append(",");
        return this;
    }

    /**
     * append string prefix a comma &quot;,&quot;.<br/>
     * 加入一個逗點後再加字串
     *
     * @param s
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper $c(String s) {
        sb.append(",").append(s).append(" ");
        return this;
    }

    /**
     * append string within two single quote &apos;string&apos;.<br/>
     * 用兩個單引號夾住字串
     *
     * @param s
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper quot(String s) {
        sb.append(" '").append(s).append("' ");
        return this;
    }

    /**
     * add (..string..)
     *
     * @param s
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper $90(String s) {
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
     * <span style="color:blue">,WHEN</span>.<br/>
     * 加入一個<span style="color:blue">,WHEN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cWhen() {
        return $(",WHEN");
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
        return $("DELETE FROM" + entity);
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
     * append a string <span style="color:blue">OUTER JOIN</span>.<br/>
     * 加入一個<span style="color:blue">OUTER JOIN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper outeJoin() {
        return $("OUTER JOIN");
    }

    /**
     * append a string <span style="color:blue">OUTER
     * JOIN</span>&nbsp;<span style="color:#FF8000">other</span>.<br/>
     * 加入一個<span style="color:blue">OUTER
     * JOIN</span>&nbsp;<span style="color:#FF8000">other</span>字串
     *
     * @param other
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper outeJoin(String other) {
        return $("OUTER JOIN " + other);
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
     * append a string <span style="color:blue">LEFT JOIN</span>.<br/>
     * 加入一個<span style="color:blue">LEFT JOIN</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper leftJoin() {
        return $("LEFT JOIN");
    }

    /**
     * append a string <span style="color:blue">LEFT
     * JOIN</span>&nbsp;<span style="color:#FF8000">other</span>.<br/>
     * 加入一個<span style="color:blue">LEFT
     * JOIN</span>&nbsp;<span style="color:#FF8000">other</span>字串
     *
     * @param other
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper leftJoin(String other) {
        return $("LEFT JOIN " + other);
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
        return $("TRUE");
    }

    /**
     * append a string <span style="color:blue">FALSE</span>.<br/>
     * 加入一個<span style="color:blue">FALSE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper False() {
        return $("FALSE");
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
    public JpqlHelper Member() {
        return $("MEMBER OF");
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
    public JpqlHelper abs(double field) {
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
    public JpqlHelper cAbs(double field) {
        return $(",ABS(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">ABS</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">ABS</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper abs(float field) {
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
    public JpqlHelper cAbs(float field) {
        return $(",ABS(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">ABS</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">ABS</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper abs(int field) {
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
    public JpqlHelper cAbs(int field) {
        return $(",ABS(" + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">ABS</span>(<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">ABS</span>(<span style="color:#FF8000">field</span>)字串
     *
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper abs(long field) {
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
    public JpqlHelper cAbs(long field) {
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
     * append a string <span style="color:blue">COALESCE</span>.<br/>
     * 加入一個<span style="color:blue">COALESCE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper coalesce() {
        return $("COALESCE");
    }

    /**
     * append a string <span style="color:blue">,COALESCE</span>.<br/>
     * 加入一個<span style="color:blue">,COALESCE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCoalesce() {
        return $(",COALESCE");
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
     * append a string <span style="color:blue">NULLIF</span>.<br/>
     * 加入一個<span style="color:blue">NULLIF</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper nullif() {
        return $("NULLIF");
    }

    /**
     * append a string <span style="color:blue">,NULLIF</span>.<br/>
     * 加入一個<span style="color:blue">,NULLIF</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cNullif() {
        return $(",NULLIF");
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
        return $("COUNT");
    }

    /**
     * append a string
     * <span style="color:blue">,COUNT</span>.<br/>
     * 加入一個<span style="color:blue">,COUNT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCount() {
        return $(",COUNT");
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
     * <span style="color:blue">MOD</span>.<br/>
     * 加入一個<span style="color:blue">MOD</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper mod() {
        return $("MOD");
    }

    /**
     * append a string
     * <span style="color:blue">,MOD</span>.<br/>
     * 加入一個<span style="color:blue">,MOD</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cMod() {
        return $(",MOD");
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
     * <span style="color:blue">CONCAT</span>.<br/>
     * 加入一個<span style="color:blue">CONCAT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper concat() {
        return $("CONCAT");
    }

    /**
     * append a string
     * <span style="color:blue">,CONCAT</span>.<br/>
     * 加入一個<span style="color:blue">,CONCAT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cConcat() {
        return $(",CONCAT");
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
        return $("CONCAT(" + first + " , " + second + " )");
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
        return $(",CONCAT(" + first + " , " + second + " )");
    }

    /**
     * append a string
     * <span style="color:blue">SUBSTRING</span>.<br/>
     * 加入一個<span style="color:blue">SUBSTRING</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper substring() {
        return $("SUBSTRING");
    }

    /**
     * append a string
     * <span style="color:blue">,SUBSTRING</span>.<br/>
     * 加入一個<span style="color:blue">,SUBSTRING</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cSubstring() {
        return $(",SUBSTRING");
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
        return $("SUBSTRING(" + field + " , " + start + " , " + end + " )");
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
        return $(",SUBSTRING(" + field + " , " + start + " , " + end + " )");
    }

    /**
     * append a string <span style="color:blue">LOCATE</span>.<br/>
     * 加入一個<span style="color:blue">LOCATE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper locate() {
        return $("LOCATE");
    }

    /**
     * append a string <span style="color:blue">,LOCATE</span>.<br/>
     * 加入一個<span style="color:blue">,LOCATE</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cLocate() {
        return $(",LOCATE");
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
        return $("LOCATE(" + substr + "," + field + ")");
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
        return $(",LOCATE(" + substr + "," + field + ")");
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
    public JpqlHelper currTimeStame() {
        return $("CURRENT_TIMESTAMP");
    }

    /**
     * append a string
     * <span style="color:blue">,CURRENT_TIMESTAMP</span>.<br/>
     * 加入一個<span style="color:blue">,CURRENT_TIMESTAMP</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCurrTimeStame() {
        return $(",CURRENT_TIMESTAMP");
    }

    /**
     * append a string
     * <span style="color:blue">NEW</span>.<br/>
     * 加入一個<span style="color:blue">NEW</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper New() {
        return $("NEW");
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
     * append a string <span style="color:blue">CAST</span>.<br/>
     * 加入一個<span style="color:blue">CAST</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cast() {
        return $("CAST");
    }

    /**
     * append a string <span style="color:blue">,CAST</span>.<br/>
     * 加入一個<span style="color:blue">,CAST</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCast() {
        return $(",CAST");
    }

    /**
     * append a string
     * <span style="color:blue">CAST</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">typeValue</span>).<br/>
     * 加入一個<span style="color:blue">CAST</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">typeValue</span>)字串
     *
     * @param field
     * @param typeValue
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cast(String field, String typeValue) {
        return $("CAST(" + field + "," + typeValue + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,CAST</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">typeValue</span>).<br/>
     * 加入一個<span style="color:blue">,CAST</span>(<span style="color:#FF8000">field</span>&nbsp;,&nbsp;<span style="color:#FF8000">typeValue</span>)字串
     *
     * @param field
     * @param typeValue
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cCast(String field, String typeValue) {
        return $(",CAST(" + field + "," + typeValue + ")");
    }

    /**
     * append a string <span style="color:blue">EXTRACT</span>.<br/>
     * 加入一個<span style="color:blue">EXTRACT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper extract() {
        return $("EXTRACT");
    }

    /**
     * append a string <span style="color:blue">,EXTRACT</span>.<br/>
     * 加入一個<span style="color:blue">,EXTRACT</span>字串
     *
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cExtract() {
        return $(",EXTRACT");
    }

    /**
     * append a string
     * <span style="color:blue">EXTRACT</span>(<span style="color:#FF8000">dt</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">EXTRACT</span>(<span style="color:#FF8000">dt</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>)字串
     *
     * @param dt
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper extract(String dt, String field) {
        return $("EXTRACT(" + dt + "," + field + ")");
    }

    /**
     * append a string
     * <span style="color:blue">,EXTRACT</span>(<span style="color:#FF8000">dt</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>).<br/>
     * 加入一個<span style="color:blue">,EXTRACT</span>(<span style="color:#FF8000">dt</span>&nbsp;,&nbsp;<span style="color:#FF8000">field</span>)字串
     *
     * @param dt
     * @param field
     * @return {@link JpqlHelper this}
     */
    public JpqlHelper cExtract(String dt, String field) {
        return $(",EXTRACT(" + dt + "," + field + ")");
    }

    /**
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

    @Override
    public String toString() {
        int fi = 0, pos = -1;
        while ((pos = sb.indexOf("  ", fi)) != -1) {
            fi = pos + 1;
            sb.delete(pos, fi);
        }
        logger.debug("JPQL:{}", sb.toString());
        while (sb.charAt(0) == ' ') {
            sb.delete(0, 1);
        }
        while (sb.charAt(sb.length() - 1) == ' ') {
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
