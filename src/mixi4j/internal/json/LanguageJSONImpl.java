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

import mixi4j.ResponseList;
import mixi4j.MixiException;
import mixi4j.conf.Configuration;
import mixi4j.internal.http.HttpResponse;
import mixi4j.internal.json.DataObjectFactoryUtil;
import twitter4j.api.HelpMethods;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.2.3
 */
public class LanguageJSONImpl implements HelpMethods.Language {
    private String name;
    private String code;
    private String status;

    LanguageJSONImpl(JSONObject json) throws MixiException {
        super();
        init(json);
    }

    private void init(JSONObject json) throws MixiException {
        try {
            name = json.getString("name");
            code = json.getString("code");
            status = json.getString(("status"));

        } catch (JSONException jsone) {
            throw new MixiException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    static ResponseList<HelpMethods.Language> createLanguageList(HttpResponse res, Configuration conf) throws MixiException {
        return createLanguageList(res.asJSONArray(), res, conf);
    }

    /*package*/
    static ResponseList<HelpMethods.Language> createLanguageList(JSONArray list, HttpResponse res
            , Configuration conf) throws MixiException {
        if (conf.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.clearThreadLocalMap();
        }
        try {
            int size = list.length();
            ResponseList<HelpMethods.Language> languages =
                    new ResponseListImpl<HelpMethods.Language>(size, res);
            for (int i = 0; i < size; i++) {
                JSONObject json = list.getJSONObject(i);
                HelpMethods.Language language = new LanguageJSONImpl(json);
                languages.add(language);
                if (conf.isJSONStoreEnabled()) {
                    DataObjectFactoryUtil.registerJSONObject(language, json);
                }
            }
            if (conf.isJSONStoreEnabled()) {
                DataObjectFactoryUtil.registerJSONObject(languages, list);
            }
            return languages;
        } catch (JSONException jsone) {
            throw new MixiException(jsone);
        } catch (MixiException te) {
            throw te;
        }
    }
}
