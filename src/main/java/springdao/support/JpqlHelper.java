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
     * @return
     */
    public JpqlHelper $(String s) {
        sb.append(" ").append(s).append(" ");
        return this;
    }

    /**
     * add (..string..)
     *
     * @param s
     * @return
     */
    public JpqlHelper $90(String s) {
        sb.append(" (").append(s).append(") ");
        return this;
    }

    public JpqlHelper select() {
        return $("SELECT");
    }

    public JpqlHelper select(String fields) {
        return $("SELECT " + fields);
    }

    public JpqlHelper selectDistinct() {
        return $("SELECT DISTINCT");
    }

    public JpqlHelper selectDistinct(String fields) {
        return $("SELECT DISTINCT " + fields);
    }

    public JpqlHelper from() {
        return $("FROM");
    }

    public JpqlHelper from(String entity) {
        return $("FROM " + entity);
    }

    public JpqlHelper where() {
        return $("WHERE");
    }

    public JpqlHelper where(String qlCriteria) {
        return $("WHERE " + qlCriteria);
    }

    public JpqlHelper update() {
        return $("UPDATE");
    }

    public JpqlHelper update(String entity) {
        return $("UPDATE " + entity);
    }

    public JpqlHelper delete() {
        return $("DELETE");
    }

    public JpqlHelper delete(String entity) {
        return $("DELETE " + entity);
    }

    public JpqlHelper join() {
        return $("JOIN");
    }

    public JpqlHelper outeJoin() {
        return $("OUTER JOIN");
    }

    public JpqlHelper innerJoin() {
        return $("INNER JOIN");
    }

    public JpqlHelper leftJoin() {
        return $("LEFT JOIN ");
    }

    public JpqlHelper groupBy() {
        return $("GROUP BY");
    }

    public JpqlHelper groupBy(String fields) {
        return $("GROUP BY " + fields);
    }

    public JpqlHelper having() {
        return $("HAVING");
    }

    public JpqlHelper having(String criteria) {
        return $("HAVING "+criteria);
    }

    public JpqlHelper fetch() {
        return $("FETCH");
    }

    public JpqlHelper distinct() {
        return $("DISTINCT");
    }

    public JpqlHelper distinct(String fields) {
        return $("DISTINCT " + fields);
    }

    public JpqlHelper isNull() {
        return $("IS NULL");
    }

    public JpqlHelper isNotNull() {
        return $("IS Not NULL");
    }

    public JpqlHelper True() {
        return $("TRUE");
    }

    public JpqlHelper False() {
        return $("FALSE");
    }

    public JpqlHelper not() {
        return $("NOT");
    }

    public JpqlHelper and() {
        return $("AND");
    }

    public JpqlHelper or() {
        return $("OR");
    }

    public JpqlHelper between() {
        return $("BETWEEN");
    }

    public JpqlHelper between(String from, String to) {
        return $("BETWEEN " + from + " TO " + to);
    }

    public JpqlHelper like() {
        return $("LIKE");
    }

    public JpqlHelper in() {
        return $("IN");
    }

    public JpqlHelper in(String fields) {
        return $("IN(" + fields + ")");
    }

    public JpqlHelper as(String fields) {
        return $("AS " + fields);
    }

    public JpqlHelper isEmpty() {
        return $("IS EMPTY");
    }

    public JpqlHelper isNotEmpty() {
        return $("IS NOT EMPTY");
    }

    public JpqlHelper Member() {
        return $("MEMBER OF");
    }

    public JpqlHelper avg() {
        return $("AVG");
    }

    public JpqlHelper avg(String field) {
        return $("AVG(" + field + ")");
    }

    public JpqlHelper max() {
        return $("MAX");
    }

    public JpqlHelper max(String field) {
        return $("MAX(" + field + ")");
    }

    public JpqlHelper min() {
        return $("MIN");
    }

    public JpqlHelper min(String field) {
        return $("MIN(" + field + ")");
    }

    public JpqlHelper sum() {
        return $("SUM");
    }

    public JpqlHelper sum(String field) {
        return $("SUM(" + field + ")");
    }

    public JpqlHelper count() {
        return $("COUNT");
    }

    public JpqlHelper count(String field) {
        return $("COUNT(" + field + ")");
    }

    public JpqlHelper countDistinct(String field) {
        return $("COUNT(DISTINCT " + field + ")");
    }

    public JpqlHelper orderBy() {
        return $("ORDER BY");
    }

    public JpqlHelper orderBy(String fields) {
        return $("ORDER BY " + fields);
    }

    public JpqlHelper asc() {
        return $("ASC");
    }

    public JpqlHelper desc() {
        return $("DESC");
    }

    public JpqlHelper mod() {
        return $("MOD");
    }

    public JpqlHelper mod(String number, String divisor) {
        return $("MOD(" + number + ", " + divisor + ")");
    }

    public JpqlHelper upper() {
        return $("UPPER");
    }

    public JpqlHelper upper(String field) {
        return $("UPPER(" + field + ")");
    }

    public JpqlHelper lower() {
        return $("LOWER");
    }

    public JpqlHelper lower(String field) {
        return $("LOWER(" + field + ")");
    }

    public JpqlHelper trim() {
        return $("TRIM");
    }

    public JpqlHelper trim(String field) {
        return $("TRIM(" + field + ")");
    }

    public JpqlHelper currTime() {
        return $("CURRENT_TIME");
    }

    public JpqlHelper currDate() {
        return $("CURRENT_DATE");
    }

    public JpqlHelper currTimeStame() {
        return $("CURRENT_TIMESTAMP");
    }

    public JpqlHelper New() {
        return $("NEW");
    }

    public JpqlHelper exists() {
        return $("EXISTS");
    }

    public JpqlHelper notExists() {
        return $("NOT EXISTS");
    }

    public JpqlHelper all() {
        return $("ALL");
    }

    public JpqlHelper all(String subquery) {
        return $("ALL(" + subquery + ")");
    }

    public JpqlHelper any() {
        return $("ANY");
    }

    public JpqlHelper any(String subquery) {
        return $("ANY(" + subquery + ")");
    }

    public JpqlHelper some() {
        return $("SOME");
    }

    public JpqlHelper some(String subquery) {
        return $("SOME(" + subquery + ")");
    }

    @Override
    public String toString() {
        int fi = 0, pos = -1;
        while ((pos = sb.indexOf("  ", fi)) != -1) {
            fi = pos + 1;
            sb.delete(pos, fi);
        }
        logger.debug("JPQL:{}", sb.toString());
        return sb.toString();
    }

    /**
     * Alias for {@link #toString() toString()}
     *
     * @return
     */
    public String ql() {
        return toString();
    }

}
