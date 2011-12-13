package mixi4j.api;

import mixi4j.profle.User_mixi;

/**
 *
 * 与えられたメールアドレスで登録しているユーザがいるかどうか調べる。
 * 自分の友人の場合は、プロフィール情報が表示される。
 * 自分の友人以外の場合は、メールアドレスのみ返ってくる。
 *
 * http://developer.mixi.co.jp/connect/mixi_graph_api/mixi_io_spec_top/people-lookup-api/
 *
 *
r_profile


 */
public interface PeopleLookupMethods {

	boolean isRegistered(String email);

	User_mixi lookupUser(String email);

}
