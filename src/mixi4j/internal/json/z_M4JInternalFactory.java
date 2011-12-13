/*
 * Copyright (C) 2007 Yusuke Yamamoto
 * Copyright (C) 2011 Twitter, Inc.
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

import mixi4j.MixiException;
import mixi4j.Status_mixi;
import mixi4j.internal.http.HttpResponse;
import mixi4j.profle.User_mixi;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.2.4
 */
public interface z_M4JInternalFactory extends java.io.Serializable {
//    Status createStatus(JSONObject json) throws MixiException;
//
//    User createUser(JSONObject json) throws MixiException;
//
//    UserList createAUserList(JSONObject json) throws MixiException;
//
//    DirectMessage createDirectMessage(JSONObject json) throws MixiException;
//
//    RateLimitStatus createRateLimitStatus(HttpResponse res) throws MixiException;
//
//    Status createStatus(HttpResponse res) throws MixiException;
//
//    ResponseList<Status> createStatusList(HttpResponse res) throws MixiException;
//
//    Trends createTrends(HttpResponse res) throws MixiException;
//
//    ResponseList<Trends> createTrendsList(HttpResponse res) throws MixiException;
//
//    User createUser(HttpResponse res) throws MixiException;

    User_mixi createUser(HttpResponse res) throws MixiException;

    ArrayList<Status_mixi> createStatusArrayList(HttpResponse res) throws MixiException;

//    ResponseList<User> createUserList(HttpResponse res) throws MixiException;
//
//    ResponseList<User> createUserListFromJSONArray(HttpResponse res) throws MixiException;
//
//    ResponseList<User> createUserListFromJSONArray_Users(HttpResponse res) throws MixiException;
//
//    QueryResult createQueryResult(HttpResponse res, Query query) throws MixiException;
//
//    IDs createIDs(HttpResponse res) throws MixiException;
//
//    PagableResponseList<User> createPagableUserList(HttpResponse res) throws MixiException;

    ArrayList<User_mixi> createUserArrayList(HttpResponse res) throws MixiException;

//    UserList createAUserList(HttpResponse res) throws MixiException;
//
//    PagableResponseList<UserList> createPagableUserListList(HttpResponse res) throws MixiException;
//
//    ResponseList<UserList> createUserListList(HttpResponse res) throws MixiException;
//
//    ResponseList<Category> createCategoryList(HttpResponse res) throws MixiException;
//
//    ProfileImage createProfileImage(HttpResponse res) throws MixiException;
//
//    DirectMessage createDirectMessage(HttpResponse res) throws MixiException;
//
//    ResponseList<DirectMessage> createDirectMessageList(HttpResponse res) throws MixiException;
//
//    Relationship createRelationship(HttpResponse res) throws MixiException;
//
//    ResponseList<Friendship> createFriendshipList(HttpResponse res) throws MixiException;
//
//    AccountTotals createAccountTotals(HttpResponse res) throws MixiException;
//
//    AccountSettings createAccountSettings(HttpResponse res) throws MixiException;
//
//    SavedSearch createSavedSearch(HttpResponse res) throws MixiException;
//
//    ResponseList<SavedSearch> createSavedSearchList(HttpResponse res) throws MixiException;
//
//    ResponseList<Location> createLocationList(HttpResponse res) throws MixiException;
//
//    Place createPlace(HttpResponse res) throws MixiException;
//
//    ResponseList<Place> createPlaceList(HttpResponse res) throws MixiException;
//
//    SimilarPlaces createSimilarPlaces(HttpResponse res) throws MixiException;
//
//    RelatedResults createRelatedResults(HttpResponse res) throws MixiException;
//
//    TwitterAPIConfiguration createTwitterAPIConfiguration(HttpResponse res) throws MixiException;
//
//    ResponseList<HelpMethods.Language> createLanguageList(HttpResponse res) throws MixiException;
//
//    <T> ResponseList<T> createEmptyResponseList();
}
