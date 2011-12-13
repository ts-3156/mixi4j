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

import static mixi4j.internal.util.z_M4JInternalParseUtil.getInt;
import static mixi4j.internal.util.z_M4JInternalParseUtil.getRawString;
import static mixi4j.internal.util.z_M4JInternalParseUtil.getRawString2;

import java.util.ArrayList;
import java.util.Iterator;

import mixi4j.MixiException;
import mixi4j.Status_mixi;
import mixi4j.conf.Configuration;
import mixi4j.internal.http.HttpResponse;
import mixi4j.internal.org.json.JSONArray;
import mixi4j.internal.org.json.JSONException;
import mixi4j.internal.org.json.JSONObject;
import mixi4j.profle.Address;
import mixi4j.profle.FavoriteThing;
import mixi4j.profle.Organization;
import mixi4j.profle.User_mixi;

/**
 * A data class representing Basic user information element
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
/*package*/ final class UserJSONImpl_mixi implements User_mixi, java.io.Serializable {
///*package*/ final class UserJSONImpl_mixi extends MixiResponseImpl implements User_mixi, java.io.Serializable {

	private String userId;
	private String displayName;
	private String thumbnailUrl;
	private String profileUrl;

	private String givenName;
	private String familyName;
	private String gender;
	private String birthday;

	private ArrayList<Organization> organizations;
	private String occupation;
	private String aboutMe;
	private String bloodType;
	private ArrayList<String> interests;
	private ArrayList<Address> addresses;
	private ArrayList<FavoriteThing> favoriteThings;

	private Status_mixi status;

	/** 最後にログインしてからの経過分。必ず60の倍数になる。最後にログインしてから15分経過していたら、60が入る */
	private int lastLogin;

    private static final long serialVersionUID = -6345893237975349030L;

    /*package*/UserJSONImpl_mixi(HttpResponse res, Configuration conf) throws MixiException {
//        super(res);
//        if (conf.isJSONStoreEnabled()) {
//            DataObjectFactoryUtil.clearThreadLocalMap();
//        }

        JSONObject json = res.asJSONObject();
        init(json);
//        if (conf.isJSONStoreEnabled()) {
//            DataObjectFactoryUtil.registerJSONObject(this, json);
//        }

    }

    /*package*/UserJSONImpl_mixi(JSONObject json) throws MixiException {
        super();
        init(json);

    }

    /** Updates API経由だと一部の情報しか送られてこない。その情報を元にUserインスタントを作成する */
    UserJSONImpl_mixi(String userId, String displayName, String profileUrl, String thumbnailUrl) throws MixiException {
		this.userId = userId;
		this.displayName = displayName;
		this.profileUrl = profileUrl;
		this.thumbnailUrl = thumbnailUrl;

		// 他のフィールドは全部nullになる。プリミティブ型のみ値を入れる
		lastLogin = -1;
    }

    /**
     * この中でjsonから値を取り出している
     * @param json
     * @throws MixiException
     */
    private void init(JSONObject json) throws MixiException {
//    	System.err.println("debug, UserJSONImpl_mixi, " + json);

    	try {
        	Iterator keys = json.keys();
        	while(keys.hasNext()){
        		String key = (String) keys.next();
        		if(key.equals("entry")){
                	json = json.getJSONObject("entry");
                	break;
        		}
        	}

        	userId = getRawString("id", json);
        	profileUrl = getRawString("profileUrl", json);
        	thumbnailUrl = getRawString("thumbnailUrl", json);
        	displayName = getRawString("displayName", json);

            if (!json.isNull("name")) {
                JSONObject nameJSON = json.getJSONObject("name");
                givenName = getRawString("givenName", nameJSON);
                familyName = getRawString("familyName", nameJSON);
            }

            gender = getRawString("gender", json);
            birthday = getRawString("birthday", json);

            if (!json.isNull("organizations")) {
                JSONArray organizationJSON = json.getJSONArray("organizations");
                organizations = new ArrayList<Organization>();
                for(int i = 0; i < organizationJSON.length(); i++){
                	Organization o = new OrganizationJSONImpl(organizationJSON.getJSONObject(i));
                	if(o != null)
                		organizations.add(o);
                }
            }

            occupation = getRawString("occupation", json);
            aboutMe = getRawString("aboutMe", json);
            bloodType = getRawString("bloodType", json);

            if (!json.isNull("interests")) {
                JSONArray interestJSON = json.getJSONArray("interests");
                interests = new ArrayList<String>();
                for(int i = 0; i < interestJSON.length(); i++){
                	String in = getRawString2(i, interestJSON);
                	if(in != null)
                		interests.add(in);
                }
            }

            if (!json.isNull("addresses")) {
                JSONArray addressJSON = json.getJSONArray("addresses");
                addresses = new ArrayList<Address>();
                for(int i = 0; i < addressJSON.length(); i++){
                	Address a = new AddressJSONImpl(addressJSON.getJSONObject(i));
                	if(a != null)
                		addresses.add(a);
                }
            }

            if (!json.isNull("favoriteThings")) {
                JSONArray favoriteThingJSON = json.getJSONArray("favoriteThings");
                favoriteThings = new ArrayList<FavoriteThing>();
                for(int i = 0; i < favoriteThingJSON.length(); i++){
                	FavoriteThing f = new FavoriteThingJSONImpl(favoriteThingJSON.getJSONObject(i));
                	if(f != null)
                		favoriteThings.add(f);
                }
            }

            if (!json.isNull("status")) {
                JSONObject statusJSON = json.getJSONObject("status");
                status = new StatusJSONImpl_mixi(statusJSON);
            }

            lastLogin = getInt("lastLogin", json);

        } catch (JSONException jsone) {
            throw new MixiException(jsone.getMessage() + ":" + json.toString(), jsone);
        }
    }

    public int compareTo(User_mixi that) {
//        return (int) (this.getUserId() - that.getUserId());
        return -1;
    }


    @Override
    public int hashCode() {
//        return (int) userId;
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
        return obj instanceof User_mixi && ((User_mixi) obj).equals(this.getUserId());
    }

    @Override
    public String toString() {
        return "UserJSONImpl_mixi{" +
            	"userId=" + userId +
            	", displayName=" + displayName +
            	", profileUrl=" + profileUrl +
            	", thumbnailUrl=" + thumbnailUrl +
            	", givenName=" + givenName +
            	", familyName=" + familyName +
            	", gender=" + gender +
            	", birthday=" + birthday +
            	", organizations=" + organizations +
            	", occupation=" + occupation +
            	", aboutMe=" + aboutMe +
            	", bloodType=" + bloodType +
            	", interests=" + interests +
            	", addresses=" + addresses +
            	", favoriteThings=" + favoriteThings +
            	", status=" + status +
            	", lastLogin=" + lastLogin +
                '}';
    }

	public String getUserId() {
		return userId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getGender() {
		return gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public ArrayList<Organization> getOrganizations() {
		return organizations;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public String getBloodType() {
		return bloodType;
	}

	public ArrayList<String> getInterests() {
		return interests;
	}

	public ArrayList<Address> getAddresses() {
		return addresses;
	}

	@Override
	public ArrayList<FavoriteThing> getFavoriteThings() {
		return favoriteThings;
	}

	@Override
	public Status_mixi getStatus() {
		return status;
	}

	@Override
	public int getLastLogin() {
		return lastLogin;
	}

    static ArrayList<User_mixi> createUserArrayList(HttpResponse res, Configuration conf) throws MixiException {
        try {
//            if (conf.isJSONStoreEnabled()) {
//                DataObjectFactoryUtil.clearThreadLocalMap();
//            }
            JSONObject json = res.asJSONObject();

            JSONArray list = json.getJSONArray("entry");
            int size = list.length();
            ArrayList<User_mixi> users = new ArrayList<User_mixi>(size);
            for (int i = 0; i < size; i++) {
                JSONObject userJson = list.getJSONObject(i);
                User_mixi user = new UserJSONImpl_mixi(userJson);
//                if (conf.isJSONStoreEnabled()) {
//                    DataObjectFactoryUtil.registerJSONObject(user, userJson);
//                }
                users.add(user);
            }
//            if (conf.isJSONStoreEnabled()) {
//                DataObjectFactoryUtil.registerJSONObject(users, json);
//            }
            return users;
        } catch (JSONException jsone) {
            throw new MixiException(jsone);
        } catch (MixiException me) {
            throw me;
        }
    }

}
