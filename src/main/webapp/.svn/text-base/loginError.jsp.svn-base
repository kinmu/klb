<% response.setStatus(200); %>
<%@ page session="false" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="ja" dir="ltr">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta http-equiv="X-UA-Compatible" content="IE=9" >
	<title>アクセスエラー [KLB01]</title>
	<meta name="description" content="">
	<meta name="keywords" content="">
	<style type="text/css">
		/*	--------------------------------------------------
		Reveal Modals
		-------------------------------------------------- */

		.reveal-modal-bg {
			position: fixed;
			height: 100%;
			width: 100%;
			background: #000;
			background: rgba(0,0,0,.8);
			z-index: 100;
			display: none;
			top: 0;
			left: 0;
		}
		.reveal-modal {
			visibility: hidden;
			top: 100px;
			left: 50%;
			margin-left: -300px;
			width: 460px;
			background: #eee;
			position: absolute;
			z-index: 101;
			padding: 20px 30px 24px;
			-moz-border-radius: 5px;
			-webkit-border-radius: 5px;
			border-radius: 8px;
			-moz-box-shadow: 0 0 10px rgba(0,0,0,.4);
			-webkit-box-shadow: 0 0 10px rgba(0,0,0,.4);
			-box-shadow: 0 0 10px rgba(0,0,0,.4);
		}
		.reveal-modal.small {
			width: 200px;
			margin-left: -140px;
		}
		.reveal-modal.medium {
			width: 400px;
			margin-left: -240px;
		}
		.reveal-modal.large {
			width: 600px;
			margin-left: -340px;
		}
		.reveal-modal.xlarge {
			width: 800px;
			margin-left: -440px;
		}
		.reveal-modal a.close-reveal-modal, .reveal-modal a.close-reveal-modal-without-callback {
			font-size: 22px;
			line-height: .5;
			position: absolute;
			top: 8px;
			right: 11px;
			color: #aaa;
			text-shadow: 0 -1px 1px rbga(0, 0, 0, .6);
			font-weight: bold;
			cursor: pointer;
			border: none;
		}
		.reveal-modal a.close-reveal-modal:hover, .reveal-modal a.close-reveal-modal-without-callback:hover, {
			color:#F00;
		}
		/*

			NOTES

			Close button entity is &#215;

			Example markup

			<div id="myModal" class="reveal-modal">
				<h2>Awesome. I have it.</h2>
				<p class="lead">Your couch.  I it's mine.</p>
				<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In ultrices aliquet placerat. Duis pulvinar orci et nisi euismod vitae tempus lorem consectetur. Duis at magna quis turpis mattis venenatis eget id diam. </p>
				<a class="close-reveal-modal">&#215;</a>
			</div>

			*/

		/* common */
		.reveal-modal h2 {
			font-size: 16px;
			font-weight: bold;
			border-bottom: #999 solid 2px;
			padding-bottom: 5px;
			margin-bottom: 1em;
		}
		#error_s.reveal-modal h2,
		#error_l.reveal-modal h2 {
			color:#F00;
			background: url(./klb_resources/images/ico_warn.png) no-repeat;
			border-bottom: #f00 solid 3px;
			padding: 0 0 5px 25px;
			margin-bottom: 1em;
		}

		#save.reveal-modal h2 {
			font-size: 16px;
			font-weight: bold;
			border-bottom: none;
			padding-top: 2em;
			text-align: center;
		}

		.reveal-modal p {
			margin-bottom: 1em;
		}

		.reveal-modal ul li {
			list-style-type: disc;
			margin: 0 0 1em 2em;
		}

		p.modalBtn {
			text-align: right;
			margin: 0;
		}
		.modalBtn input {
			margin-left: 10px;
		}


		/* border style */
		#alert.reveal-modal {
			border: #FFF solid 2px;
			background-color: #e5e5e5;
		}
		#error_s.reveal-modal,
		#error_l.reveal-modal {
			border: #F00 solid 2px;
			background-color: #e5e5e5;
		}
		#save.reveal-modal {
			border: #99cc00 solid 2px;
			background-color: #e5e5e5;
		}

		#loading.reveal-modal {
			border: none;
			background: none;
			color:#fff;
			text-align:center;
			-moz-border-radius: none;
			-webkit-border-radius: none;
			border-radius: none;
			-moz-box-shadow: none;
			-webkit-box-shadow: none;
			-box-shadow: none;
		}

		.close-reveal-modal , .close-reveal-modal-without-callback {
			font-size: 14px;
			line-height: 1;
			color: #000;
			text-shadow: none;
			font-weight: normal;
			position: relative;
			display: inline-block;
			padding: 4px 30px;
			text-align: center;
			line-height: 2em;
			white-space: nowrap;
			border: solid 1px #b3b4b4;
			cursor: pointer;

		}

		/**
		*	HTML RESET
		* ********************************************** */
		html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas, details, embed, figure, figcaption, footer, header, hgroup, menu, nav, output, ruby, section, summary, time, mark, audio, video {
			margin: 0;
			padding: 0;
			border: 0;
			font-size: 100%;
			vertical-align: baseline;
		}

		body {
			line-height: 1;
		}
		h1, h2, h3, h4, h5, h6 {
			font-weight: normal;
			line-height: 1;
		}

		ol, ul {
			list-style: none;
		}

		table {
			border-collapse: collapse;
			border-spacing: 0;
		}

		caption, th, td {
			text-align: left;
			font-weight: normal;
			vertical-align: middle;
		}

		q, blockquote {
			quotes: none;
		}

		q:before, q:after, blockquote:before, blockquote:after {
			content: "";
			content: none;
		}

		a img {
			border: none;
		}

		article, aside, details, figcaption, figure, footer, header, hgroup, menu, nav, section, summary {
			display: block;
		}

		/**
		*	@Font
		* ********************************************* */
		/* default font size 14px */
		body, input, textarea, keygen, select, button, isindex {
			font-family: 'ヒラギノ角ゴ Pro W3', 'Hiragino Kaku Gothic Pro W3', 'メイリオ','Meiryo', 'ＭＳ Ｐゴシック', 'Arial', 'verdana', sans-serif;
			font-size: 14px;
			line-height: 1.7;
			/* for IE */
			*font-size: small;
			/* for IE in quirks mode */
			*font: x-small;
		}

		html, body, #page {
			width: 100%;
			height: 100%;
		}

		/* min-width: 1024px */
		body {
			min-width: 1024px;
			width: auto !important;
			width: 1024px;
			position: relative;
		}

		body {
			background-color: #cfcfcf;
		}

		.hide {
			display: none;
		}

		.show {
			display: block;
		}

		/* clearfix */
		.clearfix, #page, #navi_info, #navi_menu {
			*zoom: 1;
		}
		.clearfix:after, #page:after, #navi_info:after, #navi_menu:after {
			content: "";
			display: table;
			clear: both;
		}

		/* ====================
		layout
		==================== */

		/* header */
		#navi_info {
			height: 44px;
			background: #fff;
		}
		#navi {
			background: #fff;
			-webkit-box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
			-moz-box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
			-ms-box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
			box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
		}
		#logo, #bread, #user_info {
			padding: 7px 7px 0;
		}
		#logo {
			height:44px;
			width:142px;
			float: left;
			text-align: center;
			padding: 0;
			margin: 0;
			font-size:0;
			background: url(./klb_resources/images/logo.PNG) no-repeat;
		}
		#bread {
			float: left;
			height:25px;
			padding-top: 7px;
		}
		#bread li {
			float: left;
			padding: 0 5px 0;
		}
		#bread li:after {
			content: " >";
		}
		#bread li.last:after {
			content: "";
		}
		#user_info {
			position: absolute;
			right: 107px;
		}
		#date, #school, #name {
			padding: 0 1em 0 0;
			font-weight: bold;
			font-size: 115%;
		}
		#account {
			position: absolute;
			right: 0;
		}

		#bg_navi_menu {
			width: 100%;
			height: 44px;
			background: #ff9721;
			min-width:1030px;
		}

		#navi_menu {
			height:41px;
			width: 100%;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #118fda), color-stop(100%, #63bef5));
			background: -webkit-linear-gradient(top, #118fda, #63bef5);
			background: -moz-linear-gradient(top, #118fda, #63bef5);
			background: -o-linear-gradient(top, #118fda, #63bef5);
			-pie-background: linear-gradient(top, #118fda, #63bef5);
			background: linear-gradient(top, #118fda,#63bef5);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#118fda', endColorstr='#63bef5');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#118fda', endColorstr='#63bef5')";
			padding: 0;
			display:table;
		}
		#navi_menu ul,
		#navi_menu ul ul {
			background: #FFF;
			padding: 0;
		}

		.navi_menu {
			float: left;
			line-height: 1;
		}
		#navi_menu li {
			position: relative;
		}
		.navi_menu a {
			display: block;
			width: 90px;
			vertical-align: middle;
			white-space:nowrap;
			padding: 13px 14px 13px 9px;
			border-right: solid 1px #5597c5;
			text-decoration: none;
			color: #fff;
			font-weight: bold;
			font-size  : 110%;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #118fda), color-stop(100%, #63bef5));
			background: -webkit-linear-gradient(top, #118fda, #63bef5);
			background: -moz-linear-gradient(top, #118fda, #63bef5);
			background: -o-linear-gradient(top, #118fda, #63bef5);
			-pie-background: linear-gradient(top, #118fda, #63bef5);
			background: linear-gradient(top, #118fda,#63bef5);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#118fda', endColorstr='#63bef5');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#118fda', endColorstr='#63bef5')";
			text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.5);
			zoom:1;
		}
		.navi_menu a:hover, .navi_menu a:focus {
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #9ed2f0), color-stop(100%, #329edf));
			background: -webkit-linear-gradient(top, #9ed2f0, #329edf);
			background: -moz-linear-gradient(top, #9ed2f0, #329edf);
			background: -o-linear-gradient(top, #9ed2f0, #329edf);
			-pie-background: linear-gradient(top, #9ed2f0, #329edf);
			background: linear-gradient(top, #9ed2f0,#329edf);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#9ed2f0', endColorstr='#329edf');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#9ed2f0', endColorstr='#329edf')";
		}

		.navi_menu a.selected {
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #286dc7), color-stop(100%, #235ba5));
			background: -webkit-linear-gradient(top, #286dc7, #235ba5);
			background: -moz-linear-gradient(top, #286dc7, #235ba5);
			background: -o-linear-gradient(top, #286dc7, #235ba5);
			-pie-background: linear-gradient(top, #286dc7, #235ba5);
			background: linear-gradient(top, #286dc7,#235ba5);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#286dc7', endColorstr='#235ba5');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#286dc7', endColorstr='#235ba5')";
		}

		.navi_menu li a,
		.navi_menu li a:hover,
		.navi_menu li a.selected {
			background: #fff;
		//	-pie-background: none;
			background: none;
			filter: none;
			-ms-filter:none;
			text-shadow: none;
			width: 190px;
		}



		/* dropdown */
		#navi_menu li:hover > ul {
			display: block;
			-webkit-box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
			-moz-box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
			-ms-box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
			box-shadow: 0px 1px 2px 2px rgba(153, 153, 153, 0.4);
		}

		/* level 2 list */
		#navi_menu ul {
			display: none;
			margin: 0;
			padding: 0;
			position: absolute;
			top: 41px;
			left: 0;
			border:#FF9721 solid 2px;
			background: #fff;
		}

		#navi_menu .navi_menu ul li {
			float: none;
			width: 200px;
			margin: 0;
			padding: 0;
			background: #fff;
			border-bottom: #ccc solid 1px;
		}

		#navi_menu .navi_menu ul li a {
			background: #fafafa;
			color: #000;
			font-size:12px;
			padding: 12px 5px;
		}

		#navi_menu .navi_menu ul li a:hover{
			background: #286dc7;
			color: #FFF;
		}

		#navi_menu .navi_menu ul li a:before {
			content: "■ ";
			color: #FF9721;
			font-size:9px;
		}

		/* level 3+ list */
		#navi_menu .navi_menu ul ul {
			width: 200px;
			top: -3px;
			left: 199px;
		}
		#navi_menu .navi_menu ul ul li {
			float: none;
			width: 200px;
			margin: 0;
			padding: 0;
		}

		/* clearfix */
		#navi_menu:after {
			content: ".";
			display: block;
			clear: both;
			visibility: hidden;
			line-height: 0;
			height: 0;
		}
		#navi_menu {
			display: inline-block;
		}
		html[xmlns] #navi_menu {
			display: block;
		}

		* html #navi_menu {
			height: 1%;
		}


		/* contents */
		#contents {
			margin: 8px 10px 10px 10px;
			background: #fff;
			border-radius: 10px;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			-ms-border-radius: 5px;
			-o-border-radius: 5px;
			-webit-box-shadow: 0px 1px 2px 2px rgba(0, 0, 0, 0.2);
			-moz-box-shadow: 0px 1px 2px 2px rgba(0, 0, 0, 0.2);
			-ms-box-shadow: 0px 1px 2px 2px rgba(0, 0, 0, 0.2);
			box-shadow: 0px 1px 2px 2px rgba(0, 0, 0, 0.2);
			behavior: url(PIE.htc); /* IE */
		}
		#contents_header {
			margin-bottom: 15px;
		}
		#contents_discription {
			padding: 5px 20px;
			margin-bottom: 0;
			background: #e2ecf8;
			font-weight: bold;
		}

		.sep {
		border-left: solid 1px #A4C9D5;
		padding: 2px 10px;
		}

		#contents_inn {
			padding: 20px;

		}
		.block {
			margin-bottom: 40px;
		}
		.holiz_table, .holiz_form {
			margin: 0 auto;
		}

		.holiz_table th {
			text-align: center;
		}
		.t_vert_under {
			width: 55%;
			margin-left: 25px;
			padding-top: 10px;
			text-align: right;
		}
		.t_vert_under input {
			margin-left: 10px;
		}

		p.pagenation {
			text-align: right;
			margin: 0 auto;
		}
		.pagenation a{
			color:#000;
			padding:2px;
		}
		.pagenation a:hover{
			color:#FFF;
			background-color:#F39846;
		}
		.pagenation .selected{
			color:#000;
			text-decoration:none;
			font-weight:bold;
			pointer-events:none;
		}


		ul.alertList li {
			text-decoration: underline;
		}

		div.section {
			width: 96%;
			margin: 0 auto 1em;
		}

		.txt_right {
			text-align: right;
		}

		.mb10 {
			margin-bottom: 1em;
		}

		/* headline */
		#contents_title {
			padding: 15px 20px;
			height: 22px;
		}
		#contents_title_tab {
			padding: 15px 20px;
			height: 22px;
			border-top: solid 2px #999;

		}
		#contents_title h2,#contents_title_tab h2 {
			font-size: 1.3em;
			border-left: solid 6px #118fda;
			font-weight: bold;
			padding: 5px 0 0 10px ;
			float:left;
		}
		#go_back {
			float:right;
		}
		#go_back a {
			color: #000;
		}

		.block_title {
			border-bottom: solid 2px #f39846;
			margin-bottom: 15px;
			padding-bottom: 5px;
			font-weight: bold;
			font-size:107%;
		}

		.block_title:before {
			content: "■ ";
			color: #f39846;
		}

		span.marking {
			background-color: #FC6;
		}
		.font_red {
			color: #F00;
		}

		/* tab */
		ul li.tab_menu {
			float: left;
			display: block;
			margin: -2px 0 0 0;
			padding: 1px 10px;
			text-align: center;
			line-height: 2em;
			white-space: nowrap;
			border: solid 2px #999;
			border-bottom: 0;
			cursor: pointer;
			background-color: #006bb6;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #006bb6), color-stop(100%, #003c69));
			background: -webkit-linear-gradient(top, #006bb6, #003c69);
			background: -moz-linear-gradient(top, #006bb6, #003c69);
			background: -o-linear-gradient(top, #006bb6, #003c69);
			-pie-background: linear-gradient(top, #006bb6, #003c69);
			background: linear-gradient(top, #006bb6,#003c69);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#006bb6', endColorstr='#003c69');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#006bb6', endColorstr='#003c69')";
			color: #fff;
			border-top-left-radius: 5px;
			-webkit-border-top-left-radius: 5px;
			-moz-border-top-left-radius: 5px;
			-ms-border-top-left-radius: 5px;
			-o-border-top-left-radius: 5px;
			border-top-right-radius: 5px;
			-webkit-border-top-right-radius: 5px;
			-moz-border-top-right-radius: 5px;
			-ms-border-top-right-radius: 5px;
			-o-border-top-right-radius: 5px;
			behavior: url(PIE.htc); /* IE */
		}
		ul li.tab_menu_off {
			background: #fff;
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#ffffff', endColorstr='#ffffff');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#ffffff', endColorstr='#ffffff')";
			color: #000;
		}

		ul li.tab_menu a,ul li.tab_menu a:visited{
			color:#FFF;
			text-decoration:none;
		}
		ul li.tab_menu_off a,ul li.tab_menu_off a:visited{
			color:#000;
			text-decoration:none;
		}
		ul li.tab_menu_off:hover, ul li.tab_menu_off:focus {
			background: #bce2eb;
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#bce2eb', endColorstr='#bce2eb');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#bce2eb', endColorstr='#bce2eb')";
			color: #000;
		}
		#contents_inn_tab {
			padding: 20px;
		}
		.block_tab_inn {
			padding: 20px 10px;
			border: solid 2px #999;
			min-height: 250px;
		}

		div.btnList {
			margin-bottom: 1em;
		}

		div.btnList ul li,
		div.btnList2 ul li {
			width: 25%;
			float: left;
			text-align: center;
		}

		div.btnList2 {
			border: #808080 solid 1px;
			padding: 10px 0;
		}

		div.stepList ul li {
			display:table-cell;
			float: left;
			text-align: center;
			padding:10px;
			border: solid #FFD4AD 2px;
			background-color: #FFE1A6;
			height:3em;
			width:5em;
			margin: 10px 0px;
			border-radius: 10px;
			-webkit-border-radius: 10px;
			-moz-border-radius: 10px;
			-ms-border-radius: 10px;
			-o-border-radius: 10px;
		}
		div.stepList ul li a {
			font-weight:bold;
			color:#E4A868;
		}
		div.stepList ul li a:hover {
			color:#E97204;
		}
		div.stepList ul li.selected {
			border: solid #E97204 2px;
			background-color: #F7C23B;
		}
		div.stepList ul li.disabled {
			background-color: #F8F8FA;
			border: solid #ECECEC 2px;
		}
		div.stepList ul li.selected a {
			text-decoration:none;
			color:#222;
			pointer-events:none;
		}
		div.stepList ul li.disabled a {
			text-decoration:none;
			font-weight:bold;
			color:#E0E0E0;
			pointer-events:none;
		}
		div.stepList ul li.arrow {
			padding:0;
			margin:20px;
			background-color:#FFF;
			background-image:url("./klb_resources/images/arrow.png");
			background-repeat:no-repeat;
			border: none;
			width:48px;
			height:48px;
			font-size:0;
		}

		/* footer */
		#footer {
			padding: 5px 20px;
			text-align: right;
			border-top: solid 1px #999;
		}
		#footer a.to_top {
			color: #000;
		}



		/* table */
		table.tablesorter thead tr .header {
			background-image: url(./klb_resources/images/bg.gif);
			background-repeat: no-repeat;
			background-position: center right;
			cursor: pointer;
		}

		table.tablesorter thead tr .headerSortUp {
			background-image: url(./klb_resources/images/asc.gif);
			background-color:#67C7F8;
		}
		table.tablesorter thead tr .headerSortDown {
			background-image: url(./klb_resources/images/desc.gif);
			background-color:#67C7F8;
		}

		table.t_holiz {
			vertical-align: middle;
		}
		table.t_holiz th, table.t_vert th {
			border: solid 1px #808080;
			padding: 5px;
			text-align: center;
		}

		table.t_holiz td, table.t_holiz td, table.t_vert th, table.t_vert td {
			border: solid 1px #808080;
			padding: 3px;
			text-align: left;
		}

		table.t_holiz td.btn {
			text-align: center;
		}
		table.t_holiz th, table.t_vert td.label, td.label1 {
			background: #b1dff7;
			color: #000;
			vertical-align: middle;
		}

		table.t_vert {
			width: 100%;
			background-color: #FFF;
			margin-bottom: 1em;
		}
		table.t_vert td.label {
			vertical-align: middle;
			text-align: center;
		}

		.bgBlue {
			background-color: #f3fafe;
			padding: 15px;
		}

		table.t_vert td.label1 {
			text-align: left;
		}
		table.t_vert td.space {
			border: none;
		}
		table.t_vert th, table.t_vert td {
			padding: 3px;
		}
		td.odd, tr.odd {
			background: #fff;
		}
		td.even, tr.even {
			background: #f7f9fc;
		}
		td.selected,  tr.selected{
			background: #fffbcf;
		}
		td.important, tr.important {
			background: #fc6;
		}

		td.gray, tr.gray {
			background: #dadadb;
		}

		.width_10 {
			width: 10%;
		}
		.width_20 {
			width: 20%;
		}
		.width_30 {
			width: 30%;
		}


		/* エラー関連 */
		#contents_error {
			padding: 5px 20px;
			background: #f8d1e2;
			color: #d30000;
			font-weight: bold;
			border-top: solid #fff 1px;
		}
		#contents_error ul{
			list-style-type: square;
			padding :0px 30px;
		}
		#contents_error a{
			color: #d30000;
		}
		#contents_error a:hover {
			color: red;
		}
		tr.error td,td.error,input.error,textarea.error,select.error {
			background: #f7c9dd;
			color: #c61a22;
			font-weight:bold;
			border: solid #C61A22 1px;
		}

		/* 必須関連 */
		span.required {
			float: left;
			background: #EC7000;
			color: white;
			border: white solid 1px;
			padding: 1px 2px;
			margin: 2px 2px 0 2px;
			font-size: 9px;
			white-space: pre;
		}
		tr.required td,td.required,input.required,textarea.required,select.required {
		 background: #FFE2ad;
		 color: #333;
		}

		/* フォーカス */
		input:focus, input:active, textarea:focus, textarea:active, select:focus, select:active, button:active ,button:focus{
			border-color: #F39846;
			background-color:#FFFBCF;
		}

		table.btnList {
			width: 100%;
		}

		table.btnList td {
			text-align: center;
			padding: 5px 0;
		}

		/* form */
		label {
			white-space:nowrap;
		}
		td.label label{
			white-space:normal;
		}
		input,select,textarea{
			line-height: 1.5em;
			border: solid 1px #ccc;
			border-radius: 3px;
			-webkit-border-radius: 3px;
			-moz-border-radius: 3px;
			-ms-border-radius: 3px;
			-o-border-radius: 3px;
		}

		input.input_1,textarea.input_1 {
			width: 90%;
			behavior: url(PIE.htc); /* IE */
		}
		input.input_2 {
			width: 82%;
			behavior: url(PIE.htc); /* IE */
		}
		input.input_3 {
			width: 12%;
			margin-right: 5px;
			behavior: url(PIE.htc); /* IE */
		}

		:disabled {
			cursor: not-allowed ;
			background: #dadadb;
			color: #777785;

		}
		.select_1 {
			width: 90%;
			line-height: 100%;
			border: solid 1px #ccc;
		}
		.select_2 {
			width: 10%;
			line-height: 100%;
			border: solid 1px #ccc;
		}



		form#logout {
			width: 117px;
			height: 44px;
			margin: 0;
			padding: 0;
		}

		input.btn_logout {
			width: 117px;
			height: 44px;
			background: url(./klb_resources/images/btn_logout.gif) no-repeat;
			text-indent: -9999em;
			overflow: hidden;
			border: none;
		}

		input.btn_logout:hover {
			background: url(./klb_resources/images/btn_logout_on.gif) no-repeat;
		}

		#jquery-ui-datepicker {
			width: 80%;
		}

		img.ui-datepicker-trigger {
		    border: 1px solid dimgray;
		    margin-left: 4px;
		    vertical-align: middle;
		}

		.date-sunday a.ui-state-default ,td.date-sunday{
		 background: #ffc0cb;
		}
		.date-saturday a.ui-state-default ,td.date-saturday{
		 background: #add8e6;
		}
		.date-holiday a.ui-state-default ,td.date-holiday{
		 background: #ffc0cb;
		}



		.torikomi {
			text-align: right;
			padding-right: 9px;
		}

		/* button */
		.button_collect, .button_collect_table {
			margin: 2.5px;
			display: inline-block;
			padding: 4px 1.7em;
			font-weight:bold;
			text-align: center;
			line-height: 2em;
			white-space: nowrap;
			border: solid 1px #3573a3;
			cursor: pointer;
			background-color: #569ccc;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #569ccc), color-stop(100%, #3573a3));
			background: -webkit-linear-gradient(top, #569ccc, #3573a3);
			background: -moz-linear-gradient(top, #569ccc, #3573a3);
			background: -o-linear-gradient(top, #569ccc, #3573a3);
			-pie-background: linear-gradient(top, #569ccc, #3573a3);
			background: linear-gradient(top, #569ccc,#3573a3);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#569ccc', endColorstr='#3573a3');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#569ccc', endColorstr='#3573a3')";
			color: #fff;
			border-radius: 5px;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			-ms-border-radius: 5px;
			-o-border-radius: 5px;
			behavior: url(PIE.htc); /* IE */
		}

		.button_collect:hover, .button_collect_table:hover, .button_collect:focus, .button_collect_table:focus {
			background-color: #7ab6df;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #7ab6df), color-stop(100%, #4c8ebd));
			background: -webkit-linear-gradient(top, #7ab6df, #4c8ebd);
			background: -moz-linear-gradient(top, #7ab6df, #4c8ebd);
			background: -o-linear-gradient(top, #7ab6df, #4c8ebd);
			-pie-background: linear-gradient(top, #7ab6df, #4c8ebd);
			background: linear-gradient(top, #7ab6df,#4c8ebd);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#7ab6df', endColorstr='#4c8ebd');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#7ab6df', endColorstr='#4c8ebd')";
			behavior: url(PIE.htc); /* IE */
		}

		.button_normal, .button_normal_table {
			margin: 2.5px;
			display: inline-block;
			padding: 4px 1.7em;
			font-weight:bold;
			text-align: center;
			line-height: 2em;
			white-space: nowrap;
			border: solid 1px #ccc;
			cursor: pointer;
			color:#000;
			background-color: #fcfcfc;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #fcfcfc), color-stop(100%, #ccc));
			background: -webkit-linear-gradient(top, #fcfcfc, #ccc);
			background: -moz-linear-gradient(top, #fcfcfc, #ccc);
			background: -o-linear-gradient(top, #fcfcfc, #ccc);
			-pie-background: linear-gradient(top, #fcfcfc, #ccc);
			background: linear-gradient(top, #fcfcfc,#ccc);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#b3b4b4', endColorstr='#787a7a');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#b3b4b4', endColorstr='#787a7a')";
			border-radius: 5px;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			-ms-border-radius: 5px;
			-o-border-radius: 5px;
			behavior: url(PIE.htc); /* IE */
		}
		.button_normal:hover, .button_normal_table:hover, .button_normal:focus, .button_normal_table:focus  {
			color:#000;
			background-color: #ffffff;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #ffffff), color-stop(100%, #f1f1f1));
			background: -webkit-linear-gradient(top, #ffffff, #f1f1f1);
			background: -moz-linear-gradient(top, #ffffff, #f1f1f1);
			background: -o-linear-gradient(top, #ffffff, #f1f1f1);
			-pie-background: linear-gradient(top, #ffffff, #1f1ff1);
			background: linear-gradient(top, #ffffff,#f1f1f1);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#ffffff', endColorstr='#1ff1f1');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#ffffff', endColorstr='#f1f1f1')";
			behavior: url(PIE.htc); /* IE */
		}

		.button_abnormal, .button_abnormal_table {
			margin: 2.5px;
			display: inline-block;
			padding: 4px 1.7em;
			font-weight:bold;
			text-align: center;
			line-height: 2em;
			white-space: nowrap;
			border: solid 1px #871e20;
			cursor: pointer;
			background-color: #d33129;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #d33129), color-stop(100%, #871e20));
			background: -webkit-linear-gradient(top, #d33129, #871e20);
			background: -moz-linear-gradient(top, #d33129, #871e20);
			background: -o-linear-gradient(top, #d33129, #871e20);
		//	-pie-background: linear-gradient(top, #d33129, #871e20);
			background: linear-gradient(top, #d33129,#871e20);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#d33129', endColorstr='#871e20');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#d33129', endColorstr='#871e20')";
			color: #fff;
			border-radius: 5px;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			-ms-border-radius: 5px;
			-o-border-radius: 5px;
		//	behavior: url(PIE.htc); /* IE */
		}
		.button_abnormal:hover, .button_abnormal_table:hover, .button_abnormal:focus, .button_abnormal_table:focus {
			background-color: #ec5f58;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #ec5f58), color-stop(100%, #b72f2b));
			background: -webkit-linear-gradient(top, #ec5f58, #b72f2b);
			background: -moz-linear-gradient(top, #ec5f58, #b72f2b);
			background: -o-linear-gradient(top, #ec5f58, #b72f2b);
			-pie-background: linear-gradient(top, #ec5f58, #b72f2b);
			background: linear-gradient(top, #ec5f58,#b72f2b);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#ec5f58', endColorstr='#b72f2b');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#ec5f58', endColorstr='#b72f2b')";
			behavior: url(PIE.htc); /* IE */
		}

		.button_exit, .button_exit_table {
			margin: 2.5px;
			display: inline-block;
			padding: 4px 2em;
			font-weight:bold;
			text-align: center;
			line-height: 2em;
			white-space: nowrap;
			border: solid 1px #070303;
			cursor: pointer;
			background-color: #bdbebe;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #bdbebe), color-stop(30%, #070303));
			background: -webkit-linear-gradient(top, #bdbebe, #070303);
			background: -moz-linear-gradient(top, #bdbebe, #070303);
			background: -o-linear-gradient(top, #bdbebe, #070303);
			-pie-background: linear-gradient(top, #bdbebe, #070303);
			background: linear-gradient(top, #bdbebe,#070303);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#bdbebe', endColorstr='#070303');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#bdbebe', endColorstr='#070303')";
			color: #fff;
			border-radius: 5px;
			-webkit-border-radius: 5px;
			-moz-border-radius: 5px;
			-ms-border-radius: 5px;
			-o-border-radius: 5px;
			behavior: url(PIE.htc); /* IE */
		}

		.button_exit:hover, .button_exit_table:hover, .button_exit:focus, .button_exit_table:focus {
			background-color: #c3c3c3;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #c3c3c3), color-stop(100%, #403d3d));
			background: -webkit-linear-gradient(top, #c3c3c3, #403d3d);
			background: -moz-linear-gradient(top, #c3c3c3, #403d3d);
			background: -o-linear-gradient(top, #c3c3c3, #403d3d);
			-pie-background: linear-gradient(top, #c3c3c3, #403d3d);
			background: linear-gradient(top, #c3c3c3,#403d3d);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#c3c3c3', endColorstr='#403d3d');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#c3c3c3', endColorstr='#403d3d')";
			behavior: url(PIE.htc); /* IE */
		}

		.button_collect:disabled, .button_collect_table:disabled,
		.button_normal:disabled, .button_normal_table:disabled,
		.button_abnormal:disabled, .button_abnormal_table:disabled,
		.button_exit:disabled, .button_exit_table:disabled
		{
			cursor: not-allowed ;
			color: #7e7e7e;
			font-weight:bold;
			background-color: #8b8d8d;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #8b8d8d), color-stop(100%, #bfc0c0));
			background: -webkit-linear-gradient(top, #8b8d8d, #bfc0c0);
			background: -moz-linear-gradient(top, #8b8d8d, #bfc0c0);
			background: -o-linear-gradient(top, #8b8d8d, #bfc0c0);
			-pie-background: linear-gradient(top, #8b8d8d, #bfc0c0);
			background: linear-gradient(top, #8b8d8d, #bfc0c0);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#8b8d8d', endColorstr='#bfc0c0');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#8b8d8d', endColorstr='#bfc0c0')";
			behavior: url(PIE.htc); /* IE */
		}

		.button_collect_table,
		.button_normal_table,
		.button_abnormal_table,
		.button_exit_table {
			padding: 2px 10px;
			margin: 2.5px;
			line-height: 1.5em;
		}

		button .button_collect:, button .button_normal, .button_abnormal button, button .button_exit
		{
			padding: 1px 8px;
		}

		button i {
			font-size:130%;
			margin-right:9px;
		}

		div#contents_discription .button_exit_table{
		padding: 2px 8px;
		margin: 2.5px 6px 2.5px -10px;
		font-size:85%;
		}

		.btnSample input {
			margin-left: 100px;
		}

		.btnSample2 {
			border: #CCC solid 1px;
			padding: 10px;
		}

		.btnSample2 input {
			margin-left: 150px;
		}



		/* float */
		.float_cen {
			margin: 0 auto;
		}
		.float_l {
			float: left;
		}
		.float_r {
			float: right;
		}

		/* modal */
		.modal {
		opacity: 0;
		pointer-events: none;
		}

		.modal {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background: rgba(0,0,0,0.5);
		z-index: 10000;
		…
		}

		.modal:target {
		opacity: 1;
		pointer-events: auto;
		}


		/* ボタンのめE */
		.chk-group label {
		  display: -moz-inline-stack;
		  display: inline-block;
		  vertical-align: middle;
		  *vertical-align: auto;
		  zoom: 1;
		  *display: inline;
		  padding: 0 6px;
		  line-height: 2em;
		  float: left;
		  text-align: center;
		  color: #333;
		  background: #ccc;
		  background-size: 100%;
		  background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #fcfcfc), color-stop(100%, #cccccc));
		  background-image: -webkit-linear-gradient(top, #fcfcfc, #cccccc);
		  background-image: -moz-linear-gradient(top, #fcfcfc, #cccccc);
		  background-image: -o-linear-gradient(top, #fcfcfc, #cccccc);
		  -pie-background-image: linear-gradient(top, #fcfcfc, #cccccc);
		  background-image: linear-gradient(top, #fcfcfc, #cccccc);
		  text-shadow: 0 1px 0 white;
		  -webkit-box-shadow: inset 1px 0 0 #f6f6f6;
		  -moz-box-shadow: inset 1px 0 0 #f6f6f6;
		  -ms-box-shadow: inset 1px 0 0 #f6f6f6;
		  box-shadow: inset 1px 0 0 #f6f6f6;
		  border-top: 1px solid #aaa;
		  border-right: 1px solid #b5b6b5;
		  border-bottom: 1px solid #999;
		  cursor: pointer;
		  behavior: url(PIE.htc); /* IE */
		}
		.chk-group label:first-of-type {
		  border-radius: 7px 0 0 7px;
		  -webkit-border-radius: 7px 0 0 7px;
		  -moz-border-radius: 7px 0 0 7px;
		  -ms-border-radius: 7px 0 0 7px;
		  -o-border-radius: 7px 0 0 7px;
		  border-left: 1px solid #b5b6b5;
		}
		.chk-group label:last-of-type {
		  border-radius: 0 7px 7px 0;
		  -webkit-border-radius: 0 7px 7px 0;
		  -moz-border-radius: 0 7px 7px 0;
		  -ms-border-radius: 0 7px 7px 0;
		  -o-border-radius: 0 7px 7px 0;
		}
		.chk-group input[type="radio"] {
		  display: none;
		}
		.chk-group label.checked {
		  background: #7aaceb;
		  background-size: 100%;
		  background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #315CA5), color-stop(100%, #4d89e8));
			background-image: -webkit-linear-gradient(top, #315CA5, #4d89e8);
			background-image: -moz-linear-gradient(top, #315CA5, #4d89e8);
			background-image: -o-linear-gradient(top, #315CA5, #4d89e8);
			-pie-background: linear-gradient(top, #315CA5, #4d89e8);
			background-image: linear-gradient(top, #315CA5,#4d89e8);
		  filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#315CA5', endColorstr='#4d89e8');
		  -ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#315CA5', endColorstr='#4d89e8')";
		  -webkit-box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  -moz-box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  -ms-box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  color: #fff;
		  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.3);
		  	behavior: url(PIE.htc); /* IE */
		}

		.chk-group label.red.checked {
		  background-size: 100%;
			background-color: #d33129;
			background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #871e20), color-stop(100%, #d33129));
			background-image: -webkit-linear-gradient(top, #871e20, #d33129);
			background-image: -moz-linear-gradient(top, #871e20, #d33129);
			background-image: -o-linear-gradient(top, #871e20, #d33129);
			-pie-background: linear-gradient(top, #871e20, #d33129);
			background-image: linear-gradient(top, #871e20,#d33129);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#871e20', endColorstr='#d33129');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#871e20', endColorstr='#d33129')";
		  -webkit-box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  -moz-box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  -ms-box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  color: #fff;
		  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.3);
		  	behavior: url(PIE.htc); /* IE */
		}

		.chk-group label.green.checked {
		  background-size: 100%;
			background-color: #44D329;
			background-image: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #44871E), color-stop(100%, #44D329));
			background-image: -webkit-linear-gradient(top, #44871E, #44D329);
			background-image: -moz-linear-gradient(top, #44871E, #44D329);
			background-image: -o-linear-gradient(top, #44871E, #44D329);
			-pie-background: linear-gradient(top, #44871E,#44D329);
			background-image: linear-gradient(top, #44871E,#44D329);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#44871E', endColorstr='#44D329');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#44871E', endColorstr='#44D329')";
		  -webkit-box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  -moz-box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  -ms-box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  box-shadow: inset 0 7px 8px rgba(0, 0, 0, 0.37);
		  color: #fff;
		  text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.3);
		  	behavior: url(PIE.htc); /* IE */
		}
		.chk-group label.sep{
		  border-right: 4px solid #b5b6b5;
		  border-left: none;
		}

		.chk-group label.checked a,.chk-group label.red.checked a,.chk-group label.green.checked a{
			color:#FFF;
			text-decoration:none;
		}

		#logo{
			margin-left:10px;
		}

		#navi_info{
			border-bottom:solid 3px #FF9721;
		}

		#contents{
			width:650px;
			margin:2% auto 15%;
		}

		#contents_header{
			height:44px;
			background-color: #fcfcfc;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #fcfcfc), color-stop(100%, #ccc));
			background: -webkit-linear-gradient(top, #fcfcfc, #ccc);
			background: -moz-linear-gradient(top, #fcfcfc, #ccc);
			background: -o-linear-gradient(top, #fcfcfc, #ccc);
			-pie-background: linear-gradient(top, #fcfcfc, #ccc);
			background: linear-gradient(top, #fcfcfc,#ccc);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#b3b4b4', endColorstr='#787a7a');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#b3b4b4', endColorstr='#787a7a')";
			behavior: url(PIE.htc); /* IE */
			border-bottom:solid 3px #FF9721;
			border-radius: 10px 10px 0 0;
			-webkit-border-radius: 10px 10px 0 0;
			-moz-border-radius: 10px 10px 0 0;
			-ms-border-radius: 10px 10px 0 0;
			-o-border-radius: 10px 10px 0 0;

		}

		#contents_header h1{
			height:44px;
			width:152px;
			font-size:14px;
			line-height: 44px;
			padding-left:10px;
			color:#ffffff;
			font-weight:bold;
			background: -webkit-gradient(linear, 50% 0%, 50% 100%, color-stop(0%, #9ed2f0), color-stop(100%, #329edf));
			background: -webkit-linear-gradient(top, #9ed2f0, #329edf);
			background: -moz-linear-gradient(top, #9ed2f0, #329edf);
			background: -o-linear-gradient(top, #9ed2f0, #329edf);
			-pie-background: linear-gradient(top, #9ed2f0, #329edf);
			background: linear-gradient(top, #9ed2f0,#329edf);
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#9ed2f0', endColorstr='#329edf');
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#9ed2f0', endColorstr='#329edf')";
			border-radius: 5px 0 0 0;
			-webkit-border-radius: 5px 0 0 0;
			-moz-border-radius: 5px 0 0 0;
			-ms-border-radius: 5px 0 0 0;
			-o-border-radius: 5px 0 0 0;

		}

		.block_title {
			border-bottom:none;
			margin-bottom:0;
		}

		.block_title.blue::before {
			color:#118fda;
		}

		#contents_inn{
			margin:10px 60px;
			padding:20px 20px 30px;
		}

		p.desc{
			margin-bottom:20px;
			font-size:14px;
		}

		span.bold{
			font-weight:bold;

		}
		input.text{
			border:solid 1px #999999;
			border-radius: 7px;
			-webkit-border-radius: 7px;
			-moz-border-radius: 7px;
			-ms-border-radius: 7px;
			-o-border-radius: 7px;
			height:50px;
			font-size:44px;
			padding:5px 10px;
			width:465px;
			vertical-align:middle;
			margin-bottom:20px;
		}

		div.notice{
			background-color:#f7f9fc;
			border:1px solid #999999;
			padding:0px;
			font-size:12px;
			min-height:80px;
			margin-bottom:10px;
		}

		.button{
			text-align: center;
		}
		.button_normal,
		.button_collect,
		.button_exit,
		.button_abnormal{
			padding:9px 1.7em;
		}

		p.logout{
			text-align: center;
			font-size:16px;
			margin:50px 0 60px;
			line-height: 2em;
		}

		p.logout .bold{
			font-size:24px;
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

		textarea.notice{
			background-color:#f7f9fc;
			border:1px solid #999999;
			padding:10px;
			font-size:12px;
			min-height:80px;
			margin-bottom:10px;
		}

	</style>
	<!-- IE8以下にもHTML5タグの対応が必要な場合に使用します。 -->
	<!--[if lte IE 8]>
	<script src="${jsPath}/common/html5.js" type="text/javascript"></script>
	<![endif]-->
	<!--[if gte IE 9]>
	<style type="text/css">
		.button_collect,.button_normal,.button_abnormal,.button_exit,
		.button_collect:disabled,.button_normal:disabled,.button_abnormal:disabled,.button_exit:disabled,
		.button_collect:hover,.button_normal:hover,.button_abnormal:hover,.button_exit:hover,
		.button_collect_table,.button_normal_table,.button_abnormal_table,.button_exit_table,
		.button_collect_table:disabled:disabled,.button_normal_table:disabled,
		.button_abnormal_table:disabled,.button_exit_table:disabled,
		.button_collect_table:hover,.button_normal_table:hover,
		.button_abnormal_table:hover,.button_exit_table:hover,
		ul li.tab_menu
		{
			filter: none;
		}
	</style>
	<![endif]-->
</head>
<body>
<div id="page">
	<header id="navi">
		<div id="navi_info">
			<h1><img src="${imagePath}/logo.gif" alt="Knowledgy Library Balloon" /></h1>
		</div>
	</header>
	<!--/#navi-->

	<div id="error_contents">
		<header id="error_header">
			<h1>アクセスエラー [KLB02]</h1>
		</header>
		<!--/contents_header-->

		<article id="contents_inn">
			<h2>対象外学校、またはユーザー情報の取得ができませんでした。</h2>
			<p class="desc">
			 再度ログインしなおしてください。<br/>
          	 再度ログインを試してもできない場合は、ヘルプデスクまでお問い合わせください。
			</p>
		</article>
		<!--/#contents_inn-->
	</div>
	<!--/#contents-->
</div>
<!--/#page-->
</body>
</html>