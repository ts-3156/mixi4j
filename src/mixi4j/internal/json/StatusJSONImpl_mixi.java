/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mixi4j.internal.json;

import static mixi4j.internal.util.z_M4JInternalParseUtil.getRawString;

import java.util.ArrayList;

import mixi4j.MixiException;
import mixi4j.Status_mixi;
import mixi4j.conf.Configuration;
import mixi4j.internal.http.HttpResponse;
import mixi4j.internal.org.json.JSONArray;
import mixi4j.internal.org.json.JSONException;
import mixi4j.internal.org.json.JSONObject;
import mixi4j.profle.User_mixi;

/**
 * A data class representing one single status of a user.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
/*package*/ final class StatusJSONImpl_mixi implements Status_mixi, java.io.Serializable {
///*package*/ final class StatusJSONImpl_mixi extends MixiResponseImpl implements Status_mixi, java.io.Serializable {
    private static final long serialVersionUID = 7548618898682727465L;

    private User_mixi user;
    private String link;
    private String postedTime;
    private String text;

    /*package*/StatusJSONImpl_mixi(HttpResponse res, Configuration conf) throws MixiException {
//        super(res);
        JSONObject json = res.asJSONObject();
        init(json);
//        if (conf.isJSONStoreEnabled()) {
//            DataObjectFactoryUtil.clearThreadLocalMap();
//            DataObjectFactoryUtil.registerJSONObject(this, json);
//        }
    }

    /*package*/ StatusJSONImpl_mixi(JSONObject json) throws MixiException {
        super();
        init(json);
    }

    private void init(JSONObject json) throws MixiException {
    	link = getRawString("link", json);
    	postedTime = getRawString("postedTime", json);

    	// People APIを利用した時はこっち
    	text = getRawString("text", json);

    	// Updates APIを利用した時はこっち
    	if(text == null){
        	text = getRawString("title", json);
        	if(!json.isNull("actor")){
        		try {
					JSONObject actorJSON = json.getJSONObject("actor");
					String actorUserId = getRawString("id", actorJSON);
					if(actorUserId != null && actorUserId.indexOf("=") != -1)
						actorUserId = actorUserId.split("=")[1];
					else
						actorUserId = null;

					String actorProfileURL = getRawString("link", actorJSON);
					String actorDisplayName = getRawString("displayName", actorJSON);

					JSONObject imageJSON = actorJSON.getJSONObject("image");
					String imageURL = getRawString("url", imageJSON);

					User_mixi user = new UserJSONImpl_mixi(actorUserId, actorDisplayName, actorProfileURL, imageURL);
					this.user = user;
				} catch (JSONException e) {
					e.printStackTrace();
				}
        	}
    	}
    }

    public int compareTo(Status_mixi that) {
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
//        return obj instanceof Status && ((Status) obj).getId() == this.id;
        return false;
    }

    @Override
    public String toString() {
        return "StatusJSONImpl_mixi{\n" +
        		"  user: " + user + "\n" +
        		"  link: " + link + "\n" +
                "  postedTime: " + postedTime + "\n" +
                "  text=" + text + "\n" +
                '}';
    }

	@Override
	public String getUser() {
		return link;
	}

	@Override
	public String getLink() {
		return link;
	}

	@Override
	public String getPostedTime() {
		return postedTime;
	}

	@Override
	public String getText() {
		return text;
	}

	/**
	 * 今のところ、Updates APIを利用した時のみ呼ばれる
	 * @param res
	 * @param conf
	 * @return
	 * @throws MixiException
	 */
    static ArrayList<Status_mixi> createStatusArrayList(HttpResponse res, Configuration conf) throws MixiException {
        try {
            JSONObject json = res.asJSONObject();

            JSONArray list = json.getJSONArray("items");
            int size = list.length();
            ArrayList<Status_mixi> statuses = new ArrayList<Status_mixi>(size);
            for (int i = 0; i < size; i++) {
                JSONObject statusJson = list.getJSONObject(i);
                Status_mixi status = new StatusJSONImpl_mixi(statusJson);
                statuses.add(status);
            }
            return statuses;
        } catch (JSONException jsone) {
            throw new MixiException(jsone);
        } catch (MixiException me) {
            throw me;
        }
    }


}
