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

import mixi4j.RateLimitStatus;
import mixi4j.MixiResponse;
import mixi4j.internal.http.HttpResponse;
import mixi4j.internal.util.z_T4JInternalParseUtil;


/**
 * Super interface of Twitter Response data interfaces which indicates that rate limit status is available.
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @see mixi4j.DirectMessage
 * @see mixi4j.Status
 * @see mixi4j.User
 */
/*package*/ abstract class MixiResponseImpl implements MixiResponse, java.io.Serializable {

    private transient RateLimitStatus rateLimitStatus = null;
    private static final long serialVersionUID = -7284708239736552059L;
    private transient int accessLevel;

    public MixiResponseImpl() {
        accessLevel = NONE;
    }

    public MixiResponseImpl(HttpResponse res) {
        this.rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(res);
        accessLevel = z_T4JInternalParseUtil.toAccessLevel(res);
    }

    /**
     * {@inheritDoc}
     */
    public RateLimitStatus getRateLimitStatus() {
        return rateLimitStatus;
    }

    /**
     * {@inheritDoc}
     */
    public int getAccessLevel() {
        return accessLevel;
    }
}
