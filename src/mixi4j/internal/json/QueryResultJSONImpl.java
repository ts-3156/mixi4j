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

import java.util.ArrayList;
import java.util.List;

import mixi4j.Query;
import mixi4j.QueryResult;
import mixi4j.Tweet;
import mixi4j.MixiException;
import mixi4j.conf.Configuration;
import mixi4j.internal.http.HttpResponse;

import static mixi4j.internal.util.z_T4JInternalParseUtil.getDouble;
import static mixi4j.internal.util.z_T4JInternalParseUtil.getInt;
import static mixi4j.internal.util.z_T4JInternalParseUtil.getLong;
import static mixi4j.internal.util.z_T4JInternalParseUtil.getRawString;
import static mixi4j.internal.util.z_T4JInternalParseUtil.getURLDecodedString;
import static mixi4j.internal.util.z_T4JInternalParseUtil.getUnescapedString;

/**
 * A data class representing search API response
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
/*package*/ final class QueryResultJSONImpl implements QueryResult, java.io.Serializable {

    private long sinceId;
    private long maxId;
    private String refreshUrl;
    private int resultsPerPage;
    private String warning;
    private double completedIn;
    private int page;
    private String query;
    private List<Tweet> tweets;
    private static final long serialVersionUID = -9059136565234613286L;

    /*package*/ QueryResultJSONImpl(HttpResponse res, Configuration conf) throws MixiException {
        JSONObject json = res.asJSONObject();
        try {
            sinceId = getLong("since_id", json);
            maxId = getLong("max_id", json);
            refreshUrl = getUnescapedString("refresh_url", json);

            resultsPerPage = getInt("results_per_page", json);
            warning = getRawString("warning", json);
            completedIn = getDouble("completed_in", json);
            page = getInt("page", json);
            query = getURLDecodedString("query", json);
            JSONArray array = json.getJSONArray("results");
            tweets = new ArrayList<Tweet>(array.length());
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.clearThreadLocalMap();
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject tweet = array.getJSONObject(i);
                tweets.add(new TweetJSONImpl(tweet, conf));
            }
        } catch (JSONException jsone) {
            throw new MixiException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    /*package*/ QueryResultJSONImpl(Query query) {
        super();
        sinceId = query.getSinceId();
        resultsPerPage = query.getRpp();
        page = query.getPage();
        tweets = new ArrayList<Tweet>(0);
    }

    /**
     * {@inheritDoc}
     */
    public long getSinceId() {
        return sinceId;
    }

    /**
     * {@inheritDoc}
     */
    public long getMaxId() {
        return maxId;
    }

    /**
     * {@inheritDoc}
     */
    public String getRefreshUrl() {
        return refreshUrl;
    }

    /**
     * {@inheritDoc}
     */
    public int getResultsPerPage() {
        return resultsPerPage;
    }

    /**
     * {@inheritDoc}
     */
    public String getWarning() {
        return warning;
    }

    /**
     * {@inheritDoc}
     */
    public double getCompletedIn() {
        return completedIn;
    }

    /**
     * {@inheritDoc}
     */
    public int getPage() {
        return page;
    }

    /**
     * {@inheritDoc}
     */
    public String getQuery() {
        return query;
    }

    /**
     * {@inheritDoc}
     */
    public List<Tweet> getTweets() {
        return tweets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryResult that = (QueryResult) o;

        if (Double.compare(that.getCompletedIn(), completedIn) != 0)
            return false;
        if (maxId != that.getMaxId()) return false;
        if (page != that.getPage()) return false;
        if (resultsPerPage != that.getResultsPerPage()) return false;
        if (sinceId != that.getSinceId()) return false;
        if (!query.equals(that.getQuery())) return false;
        if (refreshUrl != null ? !refreshUrl.equals(that.getRefreshUrl()) : that.getRefreshUrl() != null)
            return false;
        if (tweets != null ? !tweets.equals(that.getTweets()) : that.getTweets() != null)
            return false;
        if (warning != null ? !warning.equals(that.getWarning()) : that.getWarning() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (sinceId ^ (sinceId >>> 32));
        result = 31 * result + (int) (maxId ^ (maxId >>> 32));
        result = 31 * result + (refreshUrl != null ? refreshUrl.hashCode() : 0);
        result = 31 * result + resultsPerPage;
        result = 31 * result + (warning != null ? warning.hashCode() : 0);
        temp = completedIn != +0.0d ? Double.doubleToLongBits(completedIn) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + page;
        result = 31 * result + query.hashCode();
        result = 31 * result + (tweets != null ? tweets.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QueryResultJSONImpl{" +
                "sinceId=" + sinceId +
                ", maxId=" + maxId +
                ", refreshUrl='" + refreshUrl + '\'' +
                ", resultsPerPage=" + resultsPerPage +
                ", warning='" + warning + '\'' +
                ", completedIn=" + completedIn +
                ", page=" + page +
                ", query='" + query + '\'' +
                ", tweets=" + tweets +
                '}';
    }
}
