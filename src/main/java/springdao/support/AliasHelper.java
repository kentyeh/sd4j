package springdao.support;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kent Yeh
 */
public class AliasHelper {

    static final List<Integer> reservedWord = new ArrayList<Integer>();

    static {
        reservedWord.add("abs".hashCode());
        reservedWord.add("all".hashCode());
        reservedWord.add("and".hashCode());
        reservedWord.add("any".hashCode());
        reservedWord.add("as".hashCode());
        reservedWord.add("asc".hashCode());
        reservedWord.add("ascending".hashCode());
        reservedWord.add("avg".hashCode());
        reservedWord.add("between".hashCode());
        reservedWord.add("bit_length".hashCode());
        reservedWord.add("by".hashCode());
        reservedWord.add("case".hashCode());
        reservedWord.add("cast".hashCode());
        reservedWord.add("coalesce".hashCode());
        reservedWord.add("concat".hashCode());
        reservedWord.add("count".hashCode());
        reservedWord.add("current_date".hashCode());
        reservedWord.add("current_time".hashCode());
        reservedWord.add("current_timestamp".hashCode());
        reservedWord.add("day".hashCode());
        reservedWord.add("desc".hashCode());
        reservedWord.add("descending".hashCode());
        reservedWord.add("distinct".hashCode());
        reservedWord.add("element".hashCode());
        reservedWord.add("else".hashCode());
        reservedWord.add("end".hashCode());
        reservedWord.add("exclude".hashCode());
        reservedWord.add("extract".hashCode());
        reservedWord.add("exists".hashCode());
        reservedWord.add("from".hashCode());
        reservedWord.add("group".hashCode());
        reservedWord.add("having".hashCode());
        reservedWord.add("hour".hashCode());
        reservedWord.add("in".hashCode());
        reservedWord.add("inner".hashCode());
        reservedWord.add("index".hashCode());
        reservedWord.add("indices".hashCode());
        reservedWord.add("into".hashCode());
        reservedWord.add("imports".hashCode());
        reservedWord.add("is".hashCode());
        reservedWord.add("join".hashCode());
        reservedWord.add("length".hashCode());
        reservedWord.add("like".hashCode());
        reservedWord.add("length".hashCode());
        reservedWord.add("lower".hashCode());
        reservedWord.add("max".hashCode());
        reservedWord.add("maxelement".hashCode());
        reservedWord.add("member".hashCode());
        reservedWord.add("min".hashCode());
        reservedWord.add("minindex".hashCode());
        reservedWord.add("minelement".hashCode());
        reservedWord.add("minindex".hashCode());
        reservedWord.add("minute".hashCode());
        reservedWord.add("mod".hashCode());
        reservedWord.add("month".hashCode());
        reservedWord.add("nopt".hashCode());
        reservedWord.add("not".hashCode());
        reservedWord.add("null".hashCode());
        reservedWord.add("nullif".hashCode());
        reservedWord.add("object".hashCode());
        reservedWord.add("of".hashCode());
        reservedWord.add("or".hashCode());
        reservedWord.add("order".hashCode());
        reservedWord.add("parameters".hashCode());
        reservedWord.add("range".hashCode());
        reservedWord.add("rtrim".hashCode());
        reservedWord.add("second".hashCode());
        reservedWord.add("select".hashCode());
        reservedWord.add("sign".hashCode());
        reservedWord.add("sin".hashCode());
        reservedWord.add("size".hashCode());
        reservedWord.add("some".hashCode());
        reservedWord.add("sqrt".hashCode());
        reservedWord.add("str".hashCode());
        reservedWord.add("substring".hashCode());
        reservedWord.add("subclasses".hashCode());
        reservedWord.add("sum".hashCode());
        reservedWord.add("then".hashCode());
        reservedWord.add("to".hashCode());
        reservedWord.add("trim".hashCode());
        reservedWord.add("trunc".hashCode());
        reservedWord.add("union".hashCode());
        reservedWord.add("unique".hashCode());
        reservedWord.add("values".hashCode());
        reservedWord.add("variables".hashCode());
        reservedWord.add("when".hashCode());
        reservedWord.add("where".hashCode());
        reservedWord.add("year".hashCode());
    }

    /**
     * get Alias Name
     *
     * @param clazz
     * @return aliasName
     */
    public static String $a(Class<?> clazz) {
        String aliasName = clazz.getSimpleName();
        aliasName = aliasName.substring(0, 1).toLowerCase() + aliasName.substring(1);
        if (reservedWord.contains(aliasName.toLowerCase().hashCode())) {
            aliasName = "_" + aliasName;
        }
        return aliasName;
    }

    /**
     * get entityName + &quot;AS&quot; + aliasName
     *
     * @param clazz
     * @return {@link Class#getName() clazz.getName()} + &quot;AS&quot; +
     * {@link AliasHelper#$a(java.lang.Class) $a(clazz)}
     */
    public static String $ea(Class<?> clazz) {
        return clazz.getName() + " AS " + $a(clazz);
    }
}
