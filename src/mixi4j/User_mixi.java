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

package mixi4j;

import java.util.ArrayList;

import mixi4j.old.Address;
import mixi4j.old.Organization;


/**
 * A data interface representing Basic user information element
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public interface User_mixi extends Comparable<User_mixi>, MixiResponse, java.io.Serializable {

	public String getUserId();

	public String getDisplayName();

	public String getThumbnailUrl();

	public String getProfileUrl();

	public String getGivenName();

	public String getFamilyName();

	public String getGender();

	public String getBirthday();

	public ArrayList<Organization> getOrganizations();

	public String getOccupation();

	public String getAboutMe();

	public String getBloodType();

	public ArrayList<String> getInterests();

	public ArrayList<Address> getAddresses();
}