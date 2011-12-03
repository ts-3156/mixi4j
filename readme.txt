◆◆mixi4jの使い方◆◆

1) mixi4j.jarをクラスパスに通してください。
2) mixi4j.propertiesに自分のmixi Graph APIの設定を書いてください。
3) Sample.javaを実行してください。
4) エラーが起きると思います。エラーメッセージに従って、authorization codeをmixi4j.propertiesに書いてください。

成功すると、「認証を与えられたユーザの名前」、「認証を与えられたユーザのマイミクの名前」が
標準出力に表示されます。

mixi Graph APIのOAuthの仕様に関しては、以下のサイトを参考にしてください。
・認証認可手順 mixi Developer Center
http://developer.mixi.co.jp/connect/mixi_graph_api/api_auth/


◆mixi4jで出来ること◆

mixi4j-0.0.1で実装されているのは、

・認証を与えられたユーザのプロフィール情報取得
・認証を与えられたユーザのマイミクのプロフィール情報取得

のみです。今後増やしていきます…！


◆◆mixi4jに同封してあるもの◆◆

mixi4j.jar        … mixi4j本体です。このjarをクラスパスに通せばmixi4jを使うことができます。
mixi4j.properties … コンシューマキー等の設定ファイルです。mixi4j.jarを使う際に必須です。
Sample.java       … mixi4j.jarを使うためのサンプルです。
srcディレクトリ   … mixi4j.jarのソースです。


◆mixi4jのライセンス情報◆

Mixi4Jは、Javaからmixi Graph APIを操作するためのライブラリです。
ライセンスは、Apache License2.0.に準拠します。

内部のほぼ全ての処理はtwitter4jを利用しています。
twitter4jの作者の山本さんありがとうございます。

さらにJSON.orgのJSONパーサを利用しています。JSON.orgのライセンス情報はこちらです。
http://www.JSON.org/license.html


ここからは、twitter4j-2.2.5に付属のreadme.txtです
****************************************************************************************

Twittetr4J is a Twitter Aw binding library for the Java language licensed under Apache License 2.0.

Twitter4J includes software from JSON.org to parse JSON response from the Twitter API. You can see the license term at http://www.JSON.org/license.html

LICENSE.txt - the terms of license of this software
pom.xml - maven parent pom
powered-by-badge - badge
readme.txt - this file
twitter4j-core - core component : support REST and Search API
twitter4j-apache-httpclient-support - optional component adds Apache HttpClient support
twitter4j-examples - examples
twitter4j-media-support - media API support
twitter4j-async - Async API support : depending on twitter4j-core
twitter4j-stream - Streaming API support : depeinding on twitter4j-core and twitter4j-async

Contributors
------------
Adam Samet <asamet at twitter.com> @damnitsamet
Alan Gutierrez <alan at blogometer.com>
Alessandro Bahgat <ale.bahgat at gmail.com> @abahgat
Anton Evane <antonevane at gmail.com> @anton_evane
Blake Barnes <blake.barnes at gmail.com>
Bruno Torres Goyanna <bgoyanna at gmail.com> @bgoyanna
Ciaran Jessup <ciaranj at gmail.com> @ciaran_j
Cole Wen <wennnnke at gmail.com> @Pigwen
Dan Checkoway <dcheckoway at gmail.com>
Dong Wang <dong at twitter.com> @dongwang218
Eric Jensen <ej at twitter.com> @ej
Hitoshi Kunimatsu <hkhumanoid at gmail.com>
Jacob S. Hoffman-Andrews <jsha at twitter.com> @j4cob
Jenny Loomis <jenny at rockmelt.com>
John Corwin <jcorwin at twitter.com> @johnxorz
John Sirois <jsirois at twitter.com> @johnsirois
Julien Letrouit <julien.letrouit at gmail.com> @jletroui
Ludovico Fischer @ludovicofischer
marr-masaaki <marr fiveflavors at gmail.com> @marr
Mocel <docel77 at gmail.com> @Mocel
Nobutoshi Ogata <n-ogata at cnt.biglobe.co.jp> @nobu666
Nicholas Dellamaggiore <nick.dellamaggiore at gmail.com> @nickdella
Perry Sakkaris <psakkaris at gmail.com>
Roberto Estrada <robestradac at gmail.com>
Roy Reshef <royreshef at gmail.com> @tsipo
Rui Silva
Sam Pullara <sam at sampullara.com> @sampullara
Steve Lhomme <slhomme at matroska.org> @robux4
R辿my Rakic <remy.rakic at gmail.com> @lqd
Takao Nakaguchi <takao.nakaguchi at gmail.com> @takawitter
Tomohisa Igarashi <tm.igarashi at gmail.com>
Will Glozer <will at glozer.net> @ar3te
William Morgan <william at twitter.com> @wm
withgod <noname at withgod.jp> @withgod
Yusuke Yamamoto <yusuke at mac.com> @yusuke
