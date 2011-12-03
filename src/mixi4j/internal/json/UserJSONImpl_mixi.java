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

import java.util.ArrayList;
import java.util.Iterator;

import mixi4j.MixiException;
import mixi4j.User_mixi;
import mixi4j.conf.Configuration;
import mixi4j.internal.http.HttpResponse;
import mixi4j.old.Address;
import mixi4j.old.Organization;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

/**
 * A data class representing Basic user information element
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
/*package*/ final class UserJSONImpl_mixi extends MixiResponseImpl implements User_mixi, java.io.Serializable {

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

    private static final long serialVersionUID = -6345893237975349030L;

    /*package*/UserJSONImpl_mixi(HttpResponse res, Configuration conf) throws MixiException {
        super(res);
        if (conf.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.clearThreadLocalMap();
        }

        JSONObject json = res.asJSONObject();
        init(json);
        if (conf.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.registerJSONObject(this, json);
        }

    }

    /*package*/UserJSONImpl_mixi(JSONObject json) throws MixiException {
        super();
        init(json);

    }

    /**
     * この中でjsonから値を取り出している
     * @param json
     * @throws MixiException
     */
    private void init(JSONObject json) throws MixiException {
        try {
        	/*
        	 * {"startIndex":0,"totalResults":1,
        	 * "entry":
        	 * {"id":"1bwfysyf8nf3j","profileUrl":"http://mixi.jp/show_friend.pl?uid=1bwfysyf8nf3j",
        	 * "thumbnailUrl":"http://profile.img.mixi.jp/photo/user/1bwfysyf8nf3j_683193527.jpg","displayName":"しのはら"},
        	 * "itemsPerPage":1}
        	 */

        	Iterator keys = json.keys();
        	while(keys.hasNext()){
        		String key = (String) keys.next();
        		if(key.equals("entry")){
                	json = json.getJSONObject("entry");
                	break;
        		}
        	}

        	userId = json.getString("id");
        	profileUrl = json.getString("profileUrl");
        	thumbnailUrl = json.getString("thumbnailUrl");
        	displayName = json.getString("displayName");

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
            	", userId=" + userId +
            	", profileUrl=" + profileUrl +
            	", thumbnailUrl=" + thumbnailUrl +
            	", displayName=" + displayName +

//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", screenName='" + screenName + '\'' +
//                ", location='" + location + '\'' +
//                ", description='" + description + '\'' +
//                ", isContributorsEnabled=" + isContributorsEnabled +
//                ", profileImageUrl='" + profileImageUrl + '\'' +
//                ", profileImageUrlHttps='" + profileImageUrlHttps + '\'' +
//                ", url='" + url + '\'' +
//                ", isProtected=" + isProtected +
//                ", followersCount=" + followersCount +
//                ", status=" + status +
//                ", profileBackgroundColor='" + profileBackgroundColor + '\'' +
//                ", profileTextColor='" + profileTextColor + '\'' +
//                ", profileLinkColor='" + profileLinkColor + '\'' +
//                ", profileSidebarFillColor='" + profileSidebarFillColor + '\'' +
//                ", profileSidebarBorderColor='" + profileSidebarBorderColor + '\'' +
//                ", profileUseBackgroundImage=" + profileUseBackgroundImage +
//                ", showAllInlineMedia=" + showAllInlineMedia +
//                ", friendsCount=" + friendsCount +
//                ", createdAt=" + createdAt +
//                ", favouritesCount=" + favouritesCount +
//                ", utcOffset=" + utcOffset +
//                ", timeZone='" + timeZone + '\'' +
//                ", profileBackgroundImageUrl='" + profileBackgroundImageUrl + '\'' +
//                ", profileBackgroundImageUrlHttps='" + profileBackgroundImageUrlHttps + '\'' +
//                ", profileBackgroundTiled=" + profileBackgroundTiled +
//                ", lang='" + lang + '\'' +
//                ", statusesCount=" + statusesCount +
//                ", isGeoEnabled=" + isGeoEnabled +
//                ", isVerified=" + isVerified +
//                ", translator=" + translator +
//                ", listedCount=" + listedCount +
//                ", isFollowRequestSent=" + isFollowRequestSent +
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

}
