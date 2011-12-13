package mixi4j.api;

import mixi4j.profle.User_mixi;

/**
 *
 * 様々なFeedを取得できる
 *
 * http://developer.mixi.co.jp/connect/mixi_graph_api/mixi_io_spec_top/voice-api/
 *
 *
r_updates


 */
public interface VoiceMethods {

	boolean isRegistered(String email);

	User_mixi lookupUser(String email);

}
