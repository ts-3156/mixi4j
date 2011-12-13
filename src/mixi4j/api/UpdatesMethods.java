package mixi4j.api;

import java.util.ArrayList;

import mixi4j.MixiException;
import mixi4j.Status_mixi;

/**
 *
 * 様々なFeedを取得できる
 *
 * http://developer.mixi.co.jp/connect/mixi_graph_api/mixi_io_spec_top/updates-api/
 *
 *
r_updates


 */
public interface UpdatesMethods {

	public ArrayList<Status_mixi> getStatuses() throws MixiException;
	public ArrayList<Status_mixi> getFriendsStatuses() throws MixiException;

	public ArrayList<Status_mixi> getArticles() throws MixiException;
	public ArrayList<Status_mixi> getEvents() throws MixiException;
	public ArrayList<Status_mixi> getReviews() throws MixiException;
	public ArrayList<Status_mixi> getVideos() throws MixiException;
	public ArrayList<Status_mixi> getServices() throws MixiException;
	public ArrayList<Status_mixi> getPhotos() throws MixiException;
	public ArrayList<Status_mixi> getPhotoAlbums() throws MixiException;
	public ArrayList<Status_mixi> getBookmarks() throws MixiException;

}
