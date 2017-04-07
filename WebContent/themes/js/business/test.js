(function() {
	var V = navigator.userAgent;
	var af = V.match(/Chrome/i) != null
			&& V.match(/Version\/\d+\.\d+(\.\d+)?\sChrome\//i) == null ? true
			: false;
	var ag = (V.match(/(Android);?[\s\/]+([\d.]+)?/)) ? true : false;
	var a = (V.match(/(iPad).*OS\s([\d_]+)/)) ? true : false;
	var Y = (V.match(/(Mac\sOS)\sX\s([\d_]+)/)) ? true : false;
	var B = (!a && V.match(/(iPhone\sOS)\s([\d_]+)/)) ? true : false;
	var i = (B || a) && V.match(/Safari/);
	var F = V.match(/MQQBrowser\/([\d\.]+)/) ? true : false;
	var x = V.match(/UCBrowser\/([\d\.]+)/) ? true : false;
	var ad = 0;
	i && (ad = V.match(/Version\/([\d\.]+)/));
	try {
		ad = parseFloat(ad[1], 10)
	} catch (ah) {
	}
	var V = window.navigator.userAgent, aa = false;
	var aa = V.toUpperCase().indexOf("SAMSUNG-SM-N7508V") != -1;
	var r = false;
	var C = "plugIn_downloadAppPlugIn_loadIframe";
	var D = false;
	var q = 0;
	var R = {};
	var b = {};
	var l = null;
	var ac = {};
	var G = window.Zepto || window.jQuery ? true : false;
	var o = [];
	var H = window.localStorage ? true : false;
	var z = "mdownloadAppPlugInskip";
	var A = null;
	var f = Math.floor(Math.random() * 100) + 1;
	var m = null;
	var c = false;
	var v = false;
	var O = {
		closeUaAjax : false,
		keplerAjax : false,
		configCenterAjax : false
	};
	var g = {
		AROUSAL_APP : true,
		DOWNLOAD_LAYER : true,
		GENERAL_HEAD : true
	};
	var d = {
		keplerID : null,
		keplerFrom : 1,
		keplerParamJson : null
	};
	var u = {
		url : "https://mapi.m.jd.com/config/display.action?_format_=json&domain="
				+ encodeURI(document.referrer),
		method : "POST",
		async : false,
		timeout : 1000,
		withCredentials : true,
		hasAjax : "configCenterAjax",
		hasAjaxSend : false,
		error : function() {
			O.configCenterAjax = true
		},
		success : function(e) {
			O.configCenterAjax = true;
			e = JSON.parse(e);
			if (e && e.data) {
				for (var ak = 0; ak < e.data.length; ak++) {
					var aj = e.data[ak];
					if (aj.compent && !aj.display
							&& g.hasOwnProperty(aj.compent)) {
						g[aj.compent] = aj.display
					}
				}
			}
			if (e && e.kepler) {
				if (e.kepler.kepler_data && e.kepler.kepler_data.keplerID
						&& e.kepler.kepler_data.keplerFrom) {
					d.keplerID = e.kepler.kepler_data.keplerID;
					d.keplerFrom = e.kepler.kepler_data.keplerFrom
				}
				if (e.kepler.kepler_param) {
					d.keplerParamJson = JSON.stringify(e.kepler.kepler_param)
				}
			}
		}
	};
	var N = {
		url : "https://so.m.jd.com/downLoad/closeUa.action?_format_=json",
		method : "POST",
		async : false,
		timeout : 1000,
		hasAjax : "closeUaAjax",
		hasAjaxSend : false,
		error : function() {
			O.closeUaAjax = true
		},
		success : function(e) {
			O.closeUaAjax = true;
			e = JSON.parse(e);
			M(e)
		}
	};
	var ai = {
		url : "https://mapi.m.jd.com/keple/getKepleData.action?_format_=json",
		method : "POST",
		async : true,
		timeout : 1000,
		withCredentials : true,
		hasAjax : "keplerAjax",
		hasAjaxSend : false,
		error : function() {
			m = '{"source":"m-base","otherData":{},"channel":"m-arouse"}';
			O.keplerAjax = true
		},
		success : function(e) {
			var aj = JSON.parse(e);
			if (aj && aj.data) {
				if (!aj.data.source && !aj.data.channel) {
					aj.data.source = "m-base";
					aj.data.channel = "m-arouse"
				}
				m = JSON.stringify(aj.data)
			} else {
				m = '{"source":"m-base","otherData":{},"channel":"m-arouse"}'
			}
			O.keplerAjax = true
		}
	};
	function t() {
		var aj = new Date();
		var ak = aj.getFullYear();
		var al = aj.getMonth() + 1;
		var e = aj.getDate();
		strDate = ak + "-" + al + "-" + e;
		return strDate
	}
	function j(ak, aj, e) {
		if (G) {
			l("#" + ak).bind(aj, e)
		} else {
			l("#" + ak).addEventListener(aj, e, !1)
		}
	}
	function P(e) {
		var aj = (e || "mGen") + (++q);
		return aj
	}
	if (G) {
		l = window.$;
		ac = window.$
	} else {
		l = function(e) {
			if (typeof e == "object") {
				return e
			}
			return document.querySelector(e)
		};
		if (!window.$) {
			window.$ = ac = l
		} else {
			ac = window.$
		}
	}
	window.onblur = function() {
		for (var e = 0; e < o.length; e++) {
			clearTimeout(o[e])
		}
	};
	function k(ak) {
		var aj = document.cookie.indexOf(ak + "=");
		if (aj == -1) {
			return ""
		}
		aj = aj + ak.length + 1;
		var e = document.cookie.indexOf(";", aj);
		if (e == -1) {
			e = document.cookie.length
		}
		return document.cookie.substring(aj, e)
	}
	function s(ak, am, e, an, al) {
		var ao = ak + "=" + escape(am);
		if (e != "") {
			var aj = new Date();
			aj.setTime(aj.getTime() + e * 24 * 3600 * 1000);
			ao += ";expires=" + aj.toGMTString()
		}
		if (an != "") {
			ao += ";path=" + an
		}
		if (al != "") {
			ao += ";domain=" + al
		}
		document.cookie = ao
	}
	function W(ak, an) {
		var am = null;
		if (an) {
			am = {
				downAppURl : "//h5.m.jd.com/active/download/download.html?channel=jd-m",
				downAppIos : "https://itunes.apple.com/us/app/jing-dong-wang-gou-shou-dan/id414245413",
				downWeixin : "http://a.app.qq.com/o/simple.jsp?pkgname=com.jingdong.app.mall&g_f=991850",
				downIpad : "https://itunes.apple.com/cn/app/jing-dong-hd/id434374726?mt=8",
				inteneUrl : "openapp.jdmobile://virtual?",
				inteneUrlParams : null,
				sourceType : "JSHOP_SOURCE_TYPE",
				sourceValue : "JSHOP_SOURCE_VALUE",
				M_sourceFrom : "mxz",
				NoJumpDownLoadPage : false,
				kepler_param : null,
				autoOpenAppEventId : "MDownLoadFloat_AppArouse",
				autoOpenAppEventParam : "",
				autoOpenIntervalTime : 0,
				cookieFlag : null
			}
		} else {
			am = {
				downAppURl : "//h5.m.jd.com/active/download/download.html?channel=jd-m",
				downAppIos : "https://itunes.apple.com/us/app/jing-dong-wang-gou-shou-dan/id414245413",
				downWeixin : "http://a.app.qq.com/o/simple.jsp?pkgname=com.jingdong.app.mall&g_f=991850",
				downIpad : "https://itunes.apple.com/cn/app/jing-dong-hd/id434374726?mt=8",
				inteneUrl : "openapp.jdmobile://virtual?",
				inteneUrlParams : null,
				openAppBtnId : "",
				closePanelBtnId : "",
				closePanelId : "",
				openAppCallback : null,
				openAppCallbackSource : null,
				closeCallblack : null,
				closeCallblackSource : null,
				cookieFlag : null,
				noRecord : false,
				sourceType : "JSHOP_SOURCE_TYPE",
				sourceValue : "JSHOP_SOURCE_VALUE",
				openAppEventId : "MDownLoadFloat_OpenNow",
				openAppEventParam : "",
				closePanelEventId : "MDownLoadFloat_Close",
				closePanelEventParam : "",
				appDownCloseIntervalTime : 259200000,
				appDownOpenIntervalTime : 2592000000,
				isnotWriteOpenAppCookie : false,
				M_sourceFrom : "mxz",
				NoJumpDownLoadPage : false,
				kepler_param : null
			}
		}
		if (ak) {
			for ( var al in ak) {
				if ((al && ak[al])
						|| (al == "appDownCloseIntervalTime" && ak[al] == 0)
						|| (al == "appDownOpenIntervalTime" && ak[al] == 0)
						|| (al == "autoOpenIntervalTime" && ak[al] == 0)) {
					if (al == "appDownCloseIntervalTime"
							|| al == "appDownOpenIntervalTime"
							|| al == "autoOpenIntervalTime") {
						var ap = /^(-     |\+)?\d+$/;
						if (ap.test(ak[al]) && ak[al] >= 0) {
							try {
								var aj = parseInt(ak[al], 10);
								am[al] = aj
							} catch (ao) {
							}
						}
					} else {
						am[al] = ak[al]
					}
				}
			}
		}
		return am
	}
	function I(e) {
		var am = n(e);
		var aj = null;
		if (a) {
			aj = e.downIpad
		} else {
			if (B) {
				aj = e.downAppIos
			} else {
				aj = e.downAppURl
			}
		}
		if (af) {
			if (ag) {
				var al = am;
				am = L(al);
				setTimeout(function() {
					window.location.href = am
				}, 50)
			}
		}
		if ((i && ad >= 9) || Y || aa) {
			setTimeout(function() {
				var an = document.createElement("a");
				an.setAttribute("href", am), an.style.display = "none",
						document.body.appendChild(an);
				var ao = document.createEvent("HTMLEvents");
				ao.initEvent("click", !1, !1), an.dispatchEvent(ao)
			}, 0)
		} else {
			document.querySelector("#" + C).src = am
		}
		if (!e.NoJumpDownLoadPage) {
			var ak = Date.now();
			setTimeout(function() {
				var an = setTimeout(function() {
					J(ak, aj)
				}, 1500);
				o.push(an)
			}, 100)
		}
	}
	function J(ak, aj) {
		var e = Date.now();
		if (ak && (e - ak) < (1500 + 200)) {
			window.location.href = aj
		}
	}
	function n(al) {
		var av = [];
		var ao = al.inteneUrlParams;
		var at = {
			category : "jump",
			des : "HomePage"
		};
		if (al.sourceType && al.sourceValue) {
			at.sourceType = al.sourceType;
			at.sourceValue = al.sourceValue;
			if (ao && !ao.sourceType && !ao.sourceValue) {
				ao.sourceType = al.sourceType;
				ao.sourceValue = al.sourceValue
			}
			if (al && al.M_sourceFrom) {
				at.M_sourceFrom = al.M_sourceFrom;
				if (ao) {
					ao.M_sourceFrom = al.M_sourceFrom
				}
			}
		}
		if (ao) {
			for ( var au in ao) {
				if (au && ao[au]) {
					av.push('"' + au + '":"' + ao[au] + '"')
				}
			}
		} else {
			for ( var au in at) {
				if (au && at[au]) {
					av.push('"' + au + '":"' + at[au] + '"')
				}
			}
		}
		av.push('"kepler_param":' + m);
		if (d.keplerID) {
			av.push('"keplerID":"' + d.keplerID + '"');
			av.push('"keplerFrom":"' + d.keplerFrom + '"')
		}
		try {
			var ap = MPing.EventSeries.getSeries();
			if (ap) {
				var aw = JSON.parse(ap);
				aw.jdv = encodeURIComponent(k("__jdv"));
				aw.unpl = encodeURIComponent(k("unpl"));
				aw.mt_xid = encodeURIComponent(k("mt_xid"));
				aw.mt_subsite = encodeURIComponent(k("mt_subsite"))
			}
			var ar = {
				mt_subsite : encodeURIComponent(k("mt_subsite")),
				__jdv : encodeURIComponent(k("__jdv")),
				unpl : encodeURIComponent(k("unpl")),
				__jda : encodeURIComponent(k("__jda"))
			};
			ap = JSON.stringify(aw);
			av.push('"m_param":' + ap);
			av.push('"SE":' + JSON.stringify(ar))
		} catch (aq) {
			av.push('"m_param":null')
		}
		var ak = "{" + av.join(",") + "}";
		var an = al.inteneUrl.split("?");
		var aj = null;
		if (an.length == 2) {
			aj = an[0] + "?" + an[1] + "&params=" + ak
		} else {
			aj = an[0] + "?params=" + ak
		}
		var am = {
			url : "//ccc-x.jd.com/dsp/op?openapp_url=" + encodeURI(ak)
					+ "&openapp_source_type=100",
			method : "POST",
			async : true,
			timeout : 1000,
			error : function() {
			},
			success : function(e) {
			}
		};
		K(am);
		return aj
	}
	function L(e) {
		return "intent://m.jd.com/#Intent;scheme=" + e
				+ ";package=com.jingdong.app.mall;end"
	}
	function w(e) {
		if (e.openAppBtnId && document.querySelector("#" + e.openAppBtnId)) {
			R[e.openAppBtnId] = e;
			Z(e.openAppBtnId, e.openAppEventId, e.openAppEventParam);
			j(
					e.openAppBtnId,
					"click",
					function() {
						var an = this.getAttribute("id");
						var aj = R[an];
						if (!D) {
							var al = document.createElement("iframe");
							al.id = C;
							document.body.appendChild(al);
							document.getElementById(C).style.display = "none";
							document.getElementById(C).style.width = "0px";
							document.getElementById(C).style.height = "0px";
							D = true
						}
						if (aj.openAppCallback) {
							var ak = aj.openAppCallbackSource ? aj.openAppCallbackSource
									: null;
							aj.openAppCallback.call(ak)
						}
						var am = {
							ajaxPrame : ai,
							funcList : function(ao) {
								if (!ao.isnotWriteOpenAppCookie) {
									var ap = ao.cookieFlag ? "downloadAppPlugIn_downCloseDate_"
											+ ao.cookieFlag
											: "downloadAppPlugIn_downCloseDate";
									s(ap, Date.now() + "_"
											+ ao.appDownOpenIntervalTime, 60,
											"/", ".m.jd.com");
									s(ap, Date.now() + "_"
											+ ao.appDownOpenIntervalTime, 60,
											"/", "m.jd.hk")
								}
								X(ao);
								I(ao)
							},
							funcPrame : aj
						};
						E(am)
					})
		}
	}
	function U(e) {
		if (e.closePanelBtnId && e.closePanelId
				&& document.querySelector("#" + e.closePanelId)
				&& document.querySelector("#" + e.closePanelBtnId)) {
			R[e.closePanelBtnId] = e;
			Z(e.closePanelBtnId, e.closePanelEventId, e.closePanelEventParam);
			if (Q(e)) {
				document.querySelector("#" + e.closePanelId).style.display = "none";
				if (e.closeCallblack) {
					var ak = event || window.event;
					var aj = e.closeCallblackSource ? e.closeCallblackSource
							: null;
					e.closeCallblack.call(this, aj, ak)
				}
				return
			} else {
				document.querySelector("#" + e.closePanelId).style.display = "block"
			}
			j(
					e.closePanelBtnId,
					"click",
					function() {
						var ap = this.getAttribute("id");
						var al = R[ap];
						var ao = al.cookieFlag ? "downloadAppPlugIn_downCloseDate_"
								+ al.cookieFlag
								: "downloadAppPlugIn_downCloseDate";
						if (!al.noRecord) {
							s(ao, Date.now() + "_"
									+ al.appDownCloseIntervalTime, 60, "/",
									"m.jd.com");
							s(ao, Date.now() + "_"
									+ al.appDownCloseIntervalTime, 60, "/",
									"m.jd.hk")
						}
						document.querySelector("#" + al.closePanelId).style.display = "none";
						if (al.closeCallblack) {
							var an = event || window.event;
							var am = al.closeCallblackSource ? al.closeCallblackSource
									: null;
							al.closeCallblack.call(this, am, an)
						}
					})
		}
	}
	function Q(ak, aq) {
		var al = false;
		var ap = null;
		if (aq) {
			ap = ak.cookieFlag ? "autoOpenApp_downCloseDate_" + ak.cookieFlag
					: "autoOpenApp_downCloseDate";
			if (v || !g.AROUSAL_APP) {
				al = true
			}
		} else {
			var aj = V.indexOf("jdmsxh");
			var am = V.indexOf("jdmsxh2");
			if (V.indexOf("Html5Plus") >= 0 || (aj >= 0 && aj != am) || c
					|| !g.DOWNLOAD_LAYER) {
				al = true
			}
			ap = ak.cookieFlag ? "downloadAppPlugIn_downCloseDate_"
					+ ak.cookieFlag : "downloadAppPlugIn_downCloseDate"
		}
		var an = k(ap);
		var ao = null;
		if (an) {
			ao = an.split("_");
			if (ao.length == 2) {
				ao[0] = parseInt(ao[0], 10);
				ao[1] = parseInt(ao[1], 10)
			} else {
				ao = null
			}
		}
		var e = Date.now();
		if (al || (!ak.noRecord && ao && ao.length == 2 && (e - ao[0]) < ao[1])) {
			al = true
		}
		return al
	}
	function K(ak) {
		var aj;
		try {
			aj = new ActiveXObject("Msxml2.XMLHTTP")
		} catch (am) {
			try {
				aj = new ActiveXObject("Microsoft.XMLHTTP")
			} catch (am) {
				aj = new XMLHttpRequest()
			}
		}
		aj.ajaxRunError = true;
		if (ak.withCredentials) {
			try {
				aj.withCredentials = true
			} catch (am) {
			}
		}
		try {
			aj.open(ak.method, ak.url, ak.async);
			if (ak.timeout) {
				var al = ak.source ? ak.source : null;
				setTimeout(function() {
					if (aj.ajaxRunError) {
						aj.onreadystatechange = function() {
						};
						aj.abort();
						ak.error.call(al)
					}
				}, ak.timeout)
			}
			aj.onreadystatechange = function() {
				var an = ak.source ? ak.source : null;
				if (aj.readyState == 4) {
					if (aj.status == 200) {
						aj.ajaxRunError = false;
						var e = aj.responseText;
						ak.success.call(an, e)
					} else {
						ak.error.call(an)
					}
				}
			};
			aj.send(null)
		} catch (am) {
		}
	}
	function Z(ao, al, aj) {
		try {
			var an = document.getElementById(ao);
			var ak = an.className;
			if (ak) {
				ak = ak + " J_ping"
			} else {
				ak = "J_ping"
			}
			an.className = ak;
			an.setAttribute("report-eventid", al);
			if (aj) {
				an.setAttribute("report-eventparam", aj)
			}
		} catch (am) {
		}
	}
	function h(al, aj) {
		try {
			var am = new MPing.inputs.Click(al);
			if (aj) {
				am.event_param = aj
			}
			var ak = new MPing();
			ak.send(am)
		} catch (an) {
		}
	}
	function y(aj, ap, aw) {
		var ak = ap ? ap : 1;
		var aq = aw ? true : false;
		if (!Q(aj) && aj.closePanelId) {
			var ax = document.getElementById(aj.closePanelId);
			if (ax) {
				var au = ax.getBoundingClientRect();
				var av = au.height || au.bottom - au.top;
				var ao = T(ax, "opacity");
				var at = T(ax, "display");
				if (at && av && at != "none" && av == 0) {
					aq = true
				} else {
					if (av && ao && av == 50 && ao == 0) {
						aq = true
					}
				}
			} else {
				aq = true
			}
			if (ak < 3 && aq == false) {
				ak++;
				setTimeout(function() {
					y(aj, ak, aq)
				}, 2000)
			}
		}
		if (aq) {
			try {
				var am = new MPing.inputs.Click("MDownLoadFloat_FloatShield");
				var an = new MPing();
				am.event_param = aj.openAppEventId;
				an.send(am);
				var al = {
					url : "//so.m.jd.com/downLoad/checkShield.action?_format_=json",
					method : "POST",
					async : true,
					timeout : 1000,
					error : function() {
					},
					success : function(e) {
					}
				};
				K(al)
			} catch (ar) {
			}
		}
	}
	function T(aj, e) {
		if (aj.currentStyle) {
			return aj.currentStyle[e]
		} else {
			return document.defaultView.getComputedStyle(aj, false)[e]
		}
	}
	function E(am, ak) {
		var al = ak ? Q(am.funcPrame, true) : Q(am.funcPrame);
		var e = true;
		if (!al
				&& am
				&& Object.prototype.toString.apply(am.ajaxPrame) === "[object Array]"
				&& am.ajaxPrame.length > 0) {
			am.ajaxPrameIsArray = true;
			for (var aj = 0; aj < am.ajaxPrame.length; aj++) {
				if (!O[am.ajaxPrame[aj].hasAjax]) {
					e = false;
					if (!am.ajaxPrame[aj].hasAjaxSend) {
						K(am.ajaxPrame[aj]);
						am.ajaxPrame[aj].hasAjaxSend = true
					}
				}
			}
		} else {
			if (!al && !O[am.ajaxPrame.hasAjax]) {
				e = false;
				if (!am.ajaxPrame.hasAjaxSend) {
					K(am.ajaxPrame);
					am.ajaxPrame.hasAjaxSend = true
				}
			}
		}
		if (e) {
			p(am.funcList, am.funcPrame)
		} else {
			ae(am)
		}
	}
	function ae(am, aj) {
		var al = aj ? aj : 0;
		var e = true;
		if (am.ajaxPrameIsArray) {
			for (var ak = 0; ak < am.ajaxPrame.length; ak++) {
				if (!O[am.ajaxPrame[ak].hasAjax]) {
					e = false;
					break
				}
			}
		} else {
			e = O[am.ajaxPrame.hasAjax]
		}
		if (am.funcList && am.funcList.length > 0) {
			if (e) {
				p(am.funcList, am.funcPrame)
			} else {
				setTimeout(function() {
					al++;
					if (al < 20) {
						ae(am, al)
					} else {
						p(am.funcList, am.funcPrame)
					}
				}, 50)
			}
		}
	}
	function p(e, ak) {
		if (e.length == 1
				&& Object.prototype.toString.apply(e) === "[object Function]") {
			e(ak)
		} else {
			for (var aj = 0; aj < e.length; aj++) {
				e[aj](ak)
			}
		}
	}
	function M(al) {
		if (al && al.ua) {
			al = JSON.parse(al.ua);
			if (al && al.clickCloseUa) {
				var am = al.clickCloseUa.split("|");
				for (var ak = 0; ak < am.length; ak++) {
					var aj = am[ak];
					if (aj && V.indexOf(aj) >= 0) {
						c = true;
						break
					}
				}
			}
			if (al && al.autoCloseBrowser) {
				for (var ak = 0; ak < al.autoCloseBrowser.length; ak++) {
					var e = al.autoCloseBrowser[ak];
					if (e.browser && e.abtest && V.indexOf(e.browser) >= 0) {
						ab(e.abtest);
						break
					}
				}
			}
			if (!v && al && al.autoCloseOs) {
				for (var ak = 0; ak < al.autoCloseOs.length; ak++) {
					var e = al.autoCloseOs[ak];
					if (e.os == "IOS" && e.abtest && (a || B)) {
						ab(e.abtest);
						break
					} else {
						if (e.os == "Android" && e.abtest && ag) {
							ab(e.abtest);
							break
						}
					}
				}
			}
			if (!v && al && al.autoCloseOsAndBrowser) {
				for (var ak = 0; ak < al.autoCloseOsAndBrowser.length; ak++) {
					var e = al.autoCloseOsAndBrowser[ak];
					if (e.os && e.browser && e.abtest && e.os == "IOS"
							&& (a || B) && V.indexOf(e.browser) >= 0) {
						ab(e.abtest);
						break
					}
					if (e.os && e.browser && e.abtest && e.os == "Android"
							&& ag && V.indexOf(e.browser) >= 0) {
						ab(e.abtest);
						break
					}
				}
			}
		}
	}
	function ab(e) {
		if (e && f <= e) {
			v = true
		}
	}
	function X(e) {
		if (e && e.kepler_param) {
			m = e.kepler_param
		} else {
			if (!m) {
				m = '{"source":"m-base","otherData":{},"channel":"m-arouse"}'
			}
		}
		h("MDownLoadFloat_ArouseParam", m)
	}
	function S(e) {
		var aj = W(e);
		w(aj);
		var ak = {
			ajaxPrame : [ N, u ],
			funcList : [ U, y ],
			funcPrame : aj
		};
		E(ak)
	}
	ac.downloadAppPlugIn = S;
	ac.downloadAppPlugInOpenApp = function(e) {
		var aj = W(e, true);
		if (!D) {
			var ak = document.createElement("iframe");
			ak.id = C;
			document.body.appendChild(ak);
			document.getElementById(C).style.display = "none";
			document.getElementById(C).style.width = "0px";
			document.getElementById(C).style.height = "0px";
			D = true
		}
		var al = {
			ajaxPrame : [ ai, N, u ],
			funcList : function(am) {
				if (!Q(am, true)) {
					cookieName = aj.cookieFlag ? "autoOpenApp_downCloseDate_"
							+ aj.cookieFlag : "autoOpenApp_downCloseDate";
					if (am.autoOpenIntervalTime) {
						s(cookieName, Date.now() + "_"
								+ am.autoOpenIntervalTime, 60, "/", ".m.jd.com");
						s(cookieName, Date.now() + "_"
								+ am.autoOpenIntervalTime, 60, "/", "m.jd.hk")
					}
					X(am);
					h(am.autoOpenAppEventId, am.autoOpenAppEventParam);
					I(am)
				}
			},
			funcPrame : aj
		};
		E(al, true)
	}
})();