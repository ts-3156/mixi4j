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

import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import mixi4j.RelatedResults;
import mixi4j.ResponseList;
import mixi4j.Status;
import mixi4j.MixiException;
import mixi4j.conf.Configuration;
import mixi4j.internal.http.HttpResponse;

/**
 * A data class representing related_results API response
 *
 * @author Mocel - mocel at guma.jp
 */
/*package*/ final class RelatedResultsJSONImpl extends MixiResponseImpl implements RelatedResults, java.io.Serializable {

    private static final String TWEETS_WITH_CONVERSATION = "TweetsWithConversation";
    private static final String TWEETS_WITH_REPLY = "TweetsWithReply";
    private static final String TWEETS_FROM_USER = "TweetsFromUser";
    private static final long serialVersionUID = -7417061781993004083L;

    private Map<String, ResponseList<Status>> tweetsMap;

    /* package */ RelatedResultsJSONImpl(HttpResponse res, Configuration conf) throws MixiException {
        super(res);
        if (conf.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.clearThreadLocalMap();
        }
        JSONArray jsonArray = res.asJSONArray();
        init(jsonArray, res, conf.isJSONStoreEnabled());
    }

    /* package */ RelatedResultsJSONImpl(JSONArray jsonArray) throws MixiException {
        super();
        init(jsonArray, null, false);

    }

    private void init(JSONArray jsonArray, HttpResponse res, boolean registerRawJSON) throws MixiException {
        tweetsMap = new HashMap<String, ResponseList<Status>>(2);
        try {
            for (int i = 0, listLen = jsonArray.length(); i < listLen; ++i) {
                JSONObject o = jsonArray.getJSONObject(i);
                if (!"Tweet".equals(o.getString("resultType"))) {
                    continue;
                }

                String groupName = o.getString("groupName");
                if (groupName.length() == 0
                        || !(groupName.equals(TWEETS_WITH_CONVERSATION) || groupName.equals(TWEETS_WITH_REPLY) || groupName.equals(TWEETS_FROM_USER))) {
                    continue;
                }

                JSONArray results = o.getJSONArray("results");
                ResponseList<Status> statuses = tweetsMap.get(groupName);
                if (statuses == null) {
                    statuses = new ResponseListImpl<Status>(results.length(), res);
                    tweetsMap.put(groupName, statuses);
                }

                for (int j = 0, resultsLen = results.length(); j < resultsLen; ++j) {
                    JSONObject json = results.getJSONObject(j).getJSONObject("value");
                    Status status = new StatusJSONImpl(json);
                    if (registerRawJSON) {
                        DataObjectFactoryUtil.registerJSONObject(status, json);
                    }
                    statuses.add(status);
                }
                if (registerRawJSON) {
                    DataObjectFactoryUtil.registerJSONObject(statuses, results);
                }
            }
        } catch (JSONException jsone) {
            throw new MixiException(jsone);
        }
    }

    /**
     * {@inheritDoc}
     */
    public ResponseList<Status> getTweetsWithConversation() {
        ResponseList<Status> statuses = this.tweetsMap.get(TWEETS_WITH_CONVERSATION);
        if (statuses != null) {
            return statuses;
        } else {
            return new ResponseListImpl<Status>(0, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    public ResponseList<Status> getTweetsWithReply() {
        ResponseList<Status> statuses = this.tweetsMap.get(TWEETS_WITH_REPLY);
        if (statuses != null) {
            return statuses;
        } else {
            return new ResponseListImpl<Status>(0, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    public ResponseList<Status> getTweetsFromUser() {
        ResponseList<Status> statuses = this.tweetsMap.get(TWEETS_FROM_USER);
        if (statuses != null) {
            return statuses;
        } else {
            return new ResponseListImpl<Status>(0, null);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + tweetsMap.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RelatedResultsJSONImpl) {
            RelatedResultsJSONImpl other = (RelatedResultsJSONImpl) obj;
            if (tweetsMap == null) {
                if (other.tweetsMap != null)
                    return false;
            } else if (!tweetsMap.equals(other.tweetsMap))
                return false;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "RelatedResultsJSONImpl {tweetsMap=" + tweetsMap + "}";
    }
}
