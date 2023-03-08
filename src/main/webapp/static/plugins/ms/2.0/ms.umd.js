! function (e, t) {
  "object" == typeof exports && "object" == typeof module ? module.exports = t() : "function" == typeof define && define.amd ? define([], t) : "object" == typeof exports ? exports.ms = t() : e.ms = t()
}(window, (function () {
  return function (e) {
    var t = {};

    function n(o) {
      if (t[o]) return t[o].exports;
      var r = t[o] = {
        i: o,
        l: !1,
        exports: {}
      };
      return e[o].call(r.exports, r, r.exports, n), r.l = !0, r.exports
    }
    return n.m = e, n.c = t, n.d = function (e, t, o) {
      n.o(e, t) || Object.defineProperty(e, t, {
        enumerable: !0,
        get: o
      })
    }, n.r = function (e) {
      "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {
        value: "Module"
      }), Object.defineProperty(e, "__esModule", {
        value: !0
      })
    }, n.t = function (e, t) {
      if (1 & t && (e = n(e)), 8 & t) return e;
      if (4 & t && "object" == typeof e && e && e.__esModule) return e;
      var o = Object.create(null);
      if (n.r(o), Object.defineProperty(o, "default", {
          enumerable: !0,
          value: e
        }), 2 & t && "string" != typeof e)
        for (var r in e) n.d(o, r, function (t) {
          return e[t]
        }.bind(null, r));
      return o
    }, n.n = function (e) {
      var t = e && e.__esModule ? function () {
        return e.default
      } : function () {
        return e
      };
      return n.d(t, "a", t), t
    }, n.o = function (e, t) {
      return Object.prototype.hasOwnProperty.call(e, t)
    }, n.p = "", n(n.s = 1)
  }([function (e, t) {
    Vue.prototype.$table = {}, Vue.prototype.$table.moneyFormatter = function (e, t, n, o) {
      return null != n ? accounting.formatMoney(n, "¥") : ""
    }, Vue.prototype.$table.percentageFormatter = function (e, t, n, o) {
      return null != n ? n + "%" : "-"
    }
  }, function (e, t, n) {
    "use strict";

    function o(e) {
      if (null != e) {
        var t = axios.create({
          baseURL: null == e.baseURL ? axios.defaults.baseURL : e.baseURL,
          timeout: null == e.timeout ? axios.defaults.timeout : e.timeout,
          headers: null == e.headers ? null : e.headers
        });
        return t.interceptors.response.use((function (e) {
          return e
        }), (function (e) {
          if (e.response) {
            let n;
            if (401 == e.response.status ? n = "登录超时" : 400 == e.response.status ? n = "客户端错误" : 403 == e.response.status ? n = "您的权限不足" : 423 == e.response.status ? n = "账号被锁定！" : 500 == e.response.status ? n = "服务器异常" : 501 == e.response.status ? n = "您的操作被取消或不允许提交" : 423 == e.response.status && (n = "服务器正在开小差...."), n) {
              if (!t) {
                var t = document.createElement("div");
                t.id = "tempVue", document.body.appendChild(t)
              }
              new Vue({
                el: "#tempVue"
              }).$notify.error({
                title: n,
                message: e.response.data.msg || n,
                type: "warning"
              }), 401 == e.response.status && (window.ms.login ? location.href = window.ms.login : console.log(n))
            }
            return Promise.reject(e)
          }
        })), t.interceptors.request.use((function (e) {
          return !e.headers && (e.headers = {}), e.headers = Object.assign(e.headers, {
            "Cache-Control": "no-cache",
            Pragma: "no-cache",
            "X-Requested-With": "XMLHttpRequest"
          }), "" != sessionStorage.getItem("token") && (e.headers.token = sessionStorage.getItem("token")), "post" === e.method && "application/x-www-form-urlencoded" === e.headers["Content-Type"] && (e.data = Qs.stringify(e.data, {
            allowDots: !0
          })), e
        }), (function (e) {
          return Promise.reject(e)
        })), t
      }
      return axios
    }
    n.r(t), n.d(t, "http", (function () {
      return r
    })), n.d(t, "util", (function () {
      return i
    })), axios.defaults.timeout = 6e4, axios.defaults.baseURL = "";
    var r = {
      get: function (e, t, n) {
        return null == t && (t = {}), null == n && (n = {
          headers: {
            "X-Requested-With": "XMLHttpRequest"
          }
        }), new Promise((function (r, a) {
          o(n).get(e, {
            params: t
          }, n).then((function (e) {
            r(e ? e.data : void 0)
          })).catch((function (e) {
            a(e)
          }))
        }))
      },
      post: function (e, t, n) {
        return null == t && (t = {}), null == n && (n = {
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            "X-Requested-With": "XMLHttpRequest"
          }
        }), new Promise((function (r, a) {
          o(n).post(e, t, n).then((function (e) {
            r(e.data ? e.data : void 0)
          }), (function (e) {
            a(e)
          }))
        }))
      },
      put: function (e, t, n) {
        return null == t && (t = {}), new Promise((function (r, a) {
          o(n).put(e, t, n).then((function (e) {
            r(e.data)
          }), (function (e) {
            a(e)
          }))
        }))
      },
      patch: function (e, t, n) {
        return null == t && (t = {}), new Promise((function (r, a) {
          o(n).patch(e, t, n).then((function (e) {
            r(e)
          }), (function (e) {
            a(e)
          }))
        }))
      },
      download: function (e, t, n, o) {
        return null == t && (t = {}), new Promise((function (r, a) {
          axios({
            method: "post",
            url: e,
            data: t,
            timeout: n || 3e5,
            responseType: "blob",
            headers: {
              Accept: "application/vnd.openxmlformats-officedocument"
            },
            onDownloadProgress: function (e) {
              o && o(e)
            }
          }).then((function (e) {
            const t = e.data,
              n = new Blob([t]),
              o = e.headers["content-disposition"].split(";"),
              a = o[2].slice(11, o[2].length - 1);
            if ("download" in document.createElement("a")) {
              const e = document.createElement("a");
              e.download = a, e.style.display = "none", e.href = URL.createObjectURL(n), document.body.appendChild(e), e.click(), URL.revokeObjectURL(e.href), document.body.removeChild(e)
            } else navigator.msSaveBlob(n, a);
            r(e)
          }), (function (e) {
            a(e)
          }))
        }))
      }
    };
    var a = function (e) {
      window.ms.debug && console.log(e)
    };
    var i = {
      browserVersion: function () {
        var e = navigator,
          t = e.appVersion.split(";")[1].replace(/[ ]/g, "");
        return "Microsoft Internet Explorer" != e.appName ? "other" : t.indexOf("MSIE") > -1 && "MSIE10.0" !== t ? "MSIE9.0" : t.indexOf("MSIE10.0") > -1 ? "MSIE10.0" : void 0
      },
      getParameter: function (e) {
        try {
          var t = new RegExp("(^|&)" + e + "=([^&]*)(&|$)"),
            n = window.location.search.substr(1).match(t);
          return null != n ? decodeURI(n[2]) : null
        } catch (e) {
          a(e.message)
        }
      },
      treeData: function (e, t, n, o) {
        var r = JSON.parse(JSON.stringify(e));
        return r.filter((function (e) {
          var a = r.filter((function (o) {
            return e[t] == o[n]
          }));
          return a.length > 0 && (e[o] = a), !e[n] || "0" == e[n] || null == e[n]
        }))
      },
      childValidate: function e(t, n, o, r, a) {
        var i = t.find((function (e) {
          return e[r] == o
        }));
        return !i || "0" === i[a] || !i[a] || n != i[a] && e(t, n, i[a], r, a)
      },
      moneyFormatter: function (e) {
        return accounting.formatMoney(e, "¥", 2)
      },
      date: {
        fmt: function (e, t) {
          var n = new Date("string" == typeof e ? e.replace(/-/g, "/") : e);
          t || (t = "yyyy-MM-dd");
          var o = {
            "M+": n.getMonth() + 1,
            "d+": n.getDate(),
            "h+": n.getHours(),
            "m+": n.getMinutes(),
            "s+": n.getSeconds(),
            "q+": Math.floor((n.getMonth() + 3) / 3),
            S: n.getMilliseconds()
          };
          for (var r in /(y+)/.test(t) && (t = t.replace(RegExp.$1, (n.getFullYear() + "").substr(4 - RegExp.$1.length))), o) new RegExp("(" + r + ")").test(t) && (t = t.replace(RegExp.$1, 1 == RegExp.$1.length ? o[r] : ("00" + o[r]).substr(("" + o[r]).length)));
          return t
        },
        diyFmt: function (e) {
          Object.prototype.toString.call(new Date) !== Object.prototype.toString.call(e) && (e = new Date(e));
          var t = ((new Date).getTime() - e) / 1e3;
          return t < 60 ? "刚刚" : t < 3600 ? moment(e).format("A") + moment(e).format("H:mm") : t < 86400 ? "昨天" : t < 172800 ? moment(e).format("dddd") : t < 31536e3 ? moment(e).format("MMM Do").replace(" ", "") : moment(e).subtract(10, "days").calendar()
        }
      },
      array: {
        unique: function (e, t) {
          if (0 != e.length) {
            for (var n = [e[0]], o = 1; o < e.length; o++) {
              for (var r = e[o], a = !1, i = 0; i < n.length; i++)
                if (r[t] == n[i][t]) {
                  if (r.write && 0 == n[i].write) break;
                  a = !0;
                  break
                } a || n.push(r)
            }
            return n
          }
        }
      },
      log: a,
      convert: {
        byte: function (e) {
          if (isNaN(e)) return "";
          var t = Math.floor(Math.log(e) / Math.log(2));
          t < 1 && (t = 0);
          var n = Math.floor(t / 10);
          return (e /= Math.pow(2, 10 * n)).toString().length > e.toFixed(2).toString().length && (e = e.toFixed(2)), e + " " + ["bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"][n]
        },
        distance: function (e, t, n, o) {
          var r, a, i, u, s;
          return r = .01745329252 * t, a = .01745329252 * o, (s = .01745329252 * e - .01745329252 * n) > 3.14159265359 ? s = 6.28318530712 - s : s < -3.14159265359 && (s = 6.28318530712 + s), i = 6370693.5 * Math.cos(r) * s, u = 6370693.5 * (r - a), Math.sqrt(i * i + u * u).toFixed(0)
        }
      },
      store: {
        set: function (e, t) {
          window.sessionStorage.setItem(e, t)
        },
        get: function (e) {
          return window.sessionStorage.getItem(e)
        },
        remove: function (e) {
          window.sessionStorage.removeItem(e)
        }
      },
      exportTable: function (e, t, n, o) {
        var r = XLSX.utils.table_to_book(e),
          a = [],
          i = [],
          u = [];
        o.column.forEach((function (e) {
          u.push(e.label), a.push({
            wpx: e.width
          })
        })), i.push(u);
        var s = new Array(o.column.length);
        n.map((function (e) {
          var t = [];
          return o.column.forEach((function (n) {
            if (e.hasOwnProperty(n.filed))
              if (null != n.dictDataOptions && n.dictDataOptions.length > 0) {
                var r = n.dictDataOptions.find((function (t) {
                  return e[n.filed] == t.dictValue
                }));
                t.push(r ? r.dictLabel : "")
              } else t.push(e[n.filed]), null != o.countFiled && o.countFiled.indexOf(n.filed) >= 0 && (s[0] = "总计", o.column.findIndex((function (t, o) {
                t.filed == n.filed && (s[o] = null == s[o] ? e[n.filed] : s[o] + e[n.filed])
              })));
            else t.push("")
          })), t
        })).forEach((function (e, t) {
          i.push(e)
        })), i.push(s);
        var l = XLSX2.utils.aoa_to_sheet(i);
        r.Sheets.Sheet1 = l, r.Sheets.Sheet1["!cols"] = a;
        var c = XLSX.write(r, {
          bookType: "xlsx",
          bookSST: !0,
          type: "array"
        });
        try {
          saveAs(new Blob([c], {
            type: "application/octet-stream"
          }), t + ".xlsx")
        } catch (e) {
          "undefined" != typeof console && console.log(e, c)
        }
        return c
      },
      printFile: function (e, t) {
        "" !== e && null != e && null != e ? ms.http.get(ms.manager + "/mprint/printTemplate/get.do", {
          name: e
        }).then((function (e) {
          null != t && null != t && null != e.data ? printVue.open(e.data, t) : console.error("未定义数据,或没有模板数据")
        })).catch((function (e) {
          console.log(e)
        })) : console.error("未定义模板名称")
      }
    };
    n(0)
  }])
}));