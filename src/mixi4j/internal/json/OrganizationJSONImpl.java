package mixi4j.internal.json;

import static mixi4j.internal.util.z_M4JInternalParseUtil.getRawString;
import mixi4j.MixiException;
import mixi4j.conf.Configuration;
import mixi4j.internal.http.HttpResponse;
import mixi4j.internal.org.json.JSONObject;
import mixi4j.profle.Organization;

/*package*/ final class OrganizationJSONImpl implements Organization, java.io.Serializable {
///*package*/ final class OrganizationJSONImpl extends MixiResponseImpl implements Organization, java.io.Serializable {
    private static final long serialVersionUID = 7548618898682727465L;

    private String name;

    /*package*/OrganizationJSONImpl(HttpResponse res, Configuration conf) throws MixiException {
//        super(res);
        JSONObject json = res.asJSONObject();
        throw new AssertionError("未実装 OrganizationJSONImpl");
//        init(json);
//        if (conf.isJSONStoreEnabled()) {
//            DataObjectFactoryUtil.clearThreadLocalMap();
//            DataObjectFactoryUtil.registerJSONObject(this, json);
//        }
    }

//    /*package*/ OrganizationJSONImpl(JSONObject json) throws MixiException {
//        super();
//        init(json);
//    }

    /*package*/ OrganizationJSONImpl(JSONObject json) throws MixiException {
        super();
        init(json);
    }

    private void init(JSONObject json) throws MixiException {
    	name = getRawString("name", json);
    }

    public int compareTo(Organization that) {
//        long delta = this.id - that.getId();
//        if (delta < Integer.MIN_VALUE) {
//            return Integer.MIN_VALUE;
//        } else if (delta > Integer.MAX_VALUE) {
//            return Integer.MAX_VALUE;
//        }
//        return (int) delta;
        return -1;
    }

    @Override
    public int hashCode() {
//        return (int) id;
    	return -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
//        return obj instanceof Organization && ((Organization) obj).getId() == this.id;
        return false;
    }

    @Override
    public String toString() {
        return "OrganizationJSONImpl{" +
                "name=" + name +
                '}';
    }

	@Override
	public String getName() {
		return name;
	}

}
