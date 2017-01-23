<% response.setStatus(200); %>
<%@ page session="false" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ja" dir="ltr">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta http-equiv="X-UA-Compatible" content="IE=9" >
	<title>アクセスエラー [KLB03]</title>
	<meta name="description" content="">
	<meta name="keywords" content="">

	<script src="/klb/klb_resources/js/common/jquery-1.8.1.min.js"></script>

<style type="text/css">
/* HEADER */
		#navi_info {
			height: 44px;
			background: #fff;
			border-bottom:solid 3px #FF9721;
		}
		#navi {
			background: #fff;
			-webkit-box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
			-moz-box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
			-ms-box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
			box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
		}
		#logo {
			height:44px;
			width:142px;
			float: left;
			text-align: center;
			padding: 0;
			margin-left:10px;
			font-size:0;
			background: url(./resources/images/logo.PNG) no-repeat;
		}

/* HTML RESET */
		html, body, div, h1, h2, p, article, header{
			margin: 0;
			padding: 0;
			border: 0;
			font-size: 100%;
			vertical-align: baseline;
		}
		body{
			font-family: 'ヒラギノ角ゴ Pro W3', 'Hiragino Kaku Gothic Pro W3', 'メイリオ','Meiryo', 'ＭＳ Ｐゴシック', 'Arial', 'verdana', sans-serif;
			font-size: 14px;
			line-height: 1;
			min-width: 1024px;
			width: auto !important;
			width: 1024px;
			position: relative;
			/* for IE */
			*font-size: small;
			/* for IE in quirks mode */
			*font: x-small;
			background-color: #cfcfcf;
		}

/* ERROR CONTENTS */
		#contents_inn{
			margin:10px 60px;
			padding:20px 20px 30px;
		}
		#error_contents {
			width:650px;
			margin:2% auto 5%;
			padding-bottom:30px;
			border:3px solid #f91002;
			background: white;
			border-radius: 10px;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			-ms-border-radius: 5px;
			-o-border-radius: 5px;
			-webit-box-shadow: 0px 1px 2px 2px rgba(0, 0, 0, 0.2);
			-moz-box-shadow: 0px 1px 2px 2px rgba(0, 0, 0, 0.2);
			-ms-box-shadow: 0px 1px 2px 2px rgba(0, 0, 0, 0.2);
			box-shadow: 0px 1px 2px 2px rgba(0, 0, 0, 0.2);
			behavior: url(PIE.htc);
		}
		#error_header{
			background-color: #f91002;
			height:44px;
			color:#ffffff;
			font-weight:bold;
			padding-left:10px;
		}
		#error_header h1{
			line-height:44px;
			color:#ffffff;
			font-weight:bold;
			background:url('./klb_resources/images/ico_warn_white.png') no-repeat left 50%;
			padding-left:30px;
		}
		#error_contents h2{
			font-weight:bold;
			font-size:20px;
			border-bottom:3px solid #f91002;
			padding:3px;
			margin:20px 0;
			text-align: center;

		}
		#error_contents p.desc{
			font-size:16px;
			text-align: center;
			line-height:2;
		}
	</style>

	<script>

	$(document).ready(function(){

		window.onload = function onLoad() {
			param = GetQueryString();

			if(param == '404'){

				document.getElementById("code_404").style.visibility = "visible";

				document.getElementById("code_error").style.visibility = "hidden";

			} else {
				document.getElementById("code_404").style.visibility = "hidden";

				document.getElementById("code_error").style.visibility = "visible";
			}

		}
	});

	function GetQueryString() {
		if (1 < document.location.search.length) {
			// 最初の1文字 (?記号) を除いた文字列を取得する
			var query = document.location.search.substring(1);
			// クエリの区切り記号 (&) で文字列を配列に分割する
			var parameters = query.split('&');
			for (var i = 0; i < parameters.length; i++) {
				// パラメータ名とパラメータ値に分割する
				var element = parameters[i].split('=');
				var paramName = decodeURIComponent(element[0]);
				var paramValue = decodeURIComponent(element[1]);
				if(paramName == 'code'){
					return paramValue;
				}
			}
			return "";
		}
		return "";
	}


	</script>
</head>
<body>
<div id="page">

	<header id="navi">
		<div id="navi_info">
		<%-- ロゴ、ユーザ情報 --%>
			<h1><img src="${imagePath}/logo.gif" alt="Knowledgy Library Balloon" /></h1>
		</div>
	</header>
	<%--
	<header id="navi">
		<div id="navi_info">
			<h1 id="logo">SAGA Education Information System</h1>
		</div>
	</header>
	 --%>

	<!--/#navi-->
	<div id ="code_error">
		<div id="error_contents">
			<header id="error_header">
				<h1>例外処理エラー [KLB03]</h1>
			</header>
			<!--/contents_header-->

			<article id="contents_inn">
				<h2>予期しないエラーが発生しました。</h2>
				<p class="desc">
				アプリケーションは予期しない理由で終了しました。<br/>
				ヘルプデスクまたは管理者へ問い合わせてください。<br/>
				</p>
			</article>
			<!--/#contents_inn-->
		</div>
	</div>

	<%-- 404 エラーの場合、 --%>
	<div id="code_404" style="margin:-345px;">
		<div id="error_contents">
			<header id="error_header">
				<h1>存在しないURL[KLB404]</h1>
			</header>
			<!--/contents_header-->

			<article id="contents_inn">
				<h2>このURLは表示できません。</h2>
				<p class="desc">
				存在するURLであるか、もう一度確認してください。<br/>&nbsp;<br/>
				</p>
			</article>
			<!--/#contents_inn-->
		</div>
	</div>

	<!--/#contents-->
</div>
<!--/#page-->
</body>
</html>