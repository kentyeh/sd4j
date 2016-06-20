package springdao;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.hibernate.HazelcastLocalCacheRegionFactory;

/**
 *
 * @author Kent Yeh
 */
public class HazelcastCacheRegionFactory extends HazelcastLocalCacheRegionFactory {

    private static final long serialVersionUID = 7284085043001380085L;

    public HazelcastCacheRegionFactory() {
        super(Hazelcast.getHazelcastInstanceByName("springHibernate"));
    }

}
