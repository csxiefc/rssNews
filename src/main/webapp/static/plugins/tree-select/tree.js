!function(e, t) {
    "object" == typeof exports && "object" == typeof module ? module.exports = t() : "function" == typeof define && define.amd ? define([], t) : "object" == typeof exports ? exports.treeSelect = t() : e.treeSelect = t()
}("undefined" != typeof self ? self : this, function() {
    return function(e) {
        var t = {};
        function n(r) {
            if (t[r])
                return t[r].exports;
            var o = t[r] = {
                i: r,
                l: !1,
                exports: {}
            };
            return e[r].call(o.exports, o, o.exports, n),
                o.l = !0,
                o.exports
        }
        return n.m = e,
            n.c = t,
            n.d = function(e, t, r) {
                n.o(e, t) || Object.defineProperty(e, t, {
                    configurable: !1,
                    enumerable: !0,
                    get: r
                })
            }
            ,
            n.n = function(e) {
                var t = e && e.__esModule ? function() {
                        return e.default
                    }
                    : function() {
                        return e
                    }
                ;
                return n.d(t, "a", t),
                    t
            }
            ,
            n.o = function(e, t) {
                return Object.prototype.hasOwnProperty.call(e, t)
            }
            ,
            n.p = "",
            n(n.s = 1)
    }([function(e, t, n) {
        "use strict";
        Object.defineProperty(t, "__esModule", {
            value: !0
        }),
            t.default = {
                template: "#tree-select",
                name: "el-tree-select",
                props: {
                    props: {
                        type: Object,
                        default: function() {
                            return {
                                value: "id",
                                label: "title",
                                children: "children"
                            }
                        }
                    },
                    options: {
                        type: Array,
                        default: function() {
                            return []
                        }
                    },
                    value: {
                        type: Number,
                        default: function() {
                            return null
                        }
                    },
                    clearable: {
                        type: Boolean,
                        default: function() {
                            return !0
                        }
                    },
                    accordion: {
                        type: Boolean,
                        default: function() {
                            return !1
                        }
                    }
                },
                data: function() {
                    return {
                        valueId: this.value,
                        valueTitle: "",
                        defaultExpandedKey: []
                    }
                },
                mounted: function() {
                    this.initHandle()
                },
                methods: {
                    initHandle: function() {
                        if (this.valueId) {
                            if (!this.$refs.selectTree.getNode(this.valueId))
                                return this.valueTitle = "",
                                    this.valueId = null,
                                    this.defaultExpandedKey = [],
                                    void this.clearSelected();
                            this.valueTitle = this.$refs.selectTree.getNode(this.valueId).data[this.props.label],
                                this.$refs.selectTree.setCurrentKey(this.valueId),
                                this.defaultExpandedKey = [this.valueId]
                        }
                        this.initScroll()
                    },
                    initScroll: function() {
                        this.$nextTick(function() {
                            var e = document.querySelectorAll(".el-scrollbar .el-select-dropdown__wrap")[0]
                                , t = document.querySelectorAll(".el-scrollbar .el-scrollbar__bar");
                            //e.style.cssText = "margin-bottom: -17px;margin-right: -17px;",
                            //     t.forEach(function(e) {
                            //         return e.style.width = 0
                            //     })
                        })
                    },
                    handleNodeClick: function(e) {
                        this.$emit("input", e[this.props.value]),
                            this.$emit("change", {
                                node: e,
                                dom: this.$refs.tsSselect
                            }),
                            this.$refs.tsSselect.blur(),
                            this.defaultExpandedKey = []
                    },
                    clearHandle: function() {
                        this.valueTitle = "",
                            this.valueId = null,
                            this.defaultExpandedKey = [],
                            this.clearSelected(),
                            this.$emit("input", null),
                            this.$emit("change", null)
                    },
                    clearSelected: function() {
                        document.querySelectorAll("#tree-option .el-tree-node").forEach(function(e) {
                            return e.classList.remove("is-current")
                        })
                    }
                },
                watch: {
                    value: function() {
                        this.valueId = this.value,
                            this.initHandle()
                    },
                    options: {
                        handler: function(e, t) {
                            var n = this;
                            this.valueId = this.value,
                                this.$nextTick(function(e) {
                                    n.initHandle()
                                })
                        },
                        immediate: !0,
                        deep: !0
                    }
                }
            }
    }
        , function(e, t, n) {
            "use strict";
            Object.defineProperty(t, "__esModule", {
                value: !0
            });
            var r = n(0)
                , o = n.n(r);
            for (var i in r)
                "default" !== i && function(e) {
                    n.d(t, e, function() {
                        return r[e]
                    })
                }(i);
            var a = n(8)
                , l = !1;
            var s = function(e) {
                l || n(2)
            }
                , u = n(7)(o.a, a.a, !1, s, "data-v-24dbbcae", null);
            u.options.__file = "src/components/tree.vue",
                t.default = u.exports
        }
        , function(e, t, n) {
            var r = n(3);
            "string" == typeof r && (r = [[e.i, r, ""]]),
            r.locals && (e.exports = r.locals);
            n(5)("682353eb", r, !1, {})
        }
        , function(e, t, n) {
            (e.exports = n(4)(void 0)).push([e.i, "\n.el-scrollbar .el-scrollbar__view .el-select-dropdown__item[data-v-24dbbcae] {\r\n  height: auto;\r\n  max-height: 274px;\r\n  padding: 0;\r\n  overflow: hidden;\r\n  overflow-y: auto;\n}\n.el-select-dropdown__item.selected[data-v-24dbbcae] {\r\n  font-weight: normal;\n}\nul li[data-v-24dbbcae] .el-tree .el-tree-node__content {\r\n  height: auto;\r\n  padding: 0 20px;\n}\n.el-tree-node__label[data-v-24dbbcae] {\r\n  font-weight: normal;\n}\n.el-tree[data-v-24dbbcae] .is-current .el-tree-node__label {\r\n  color: #409eff;\r\n  font-weight: 700;\n}\n.el-tree[data-v-24dbbcae] .is-current .el-tree-node__children .el-tree-node__label {\r\n  color: #606266;\r\n  font-weight: normal;\n}\r\n", ""])
        }
        , function(e, t) {
            e.exports = function(e) {
                var t = [];
                return t.toString = function() {
                    return this.map(function(t) {
                        var n = function(e, t) {
                            var n = e[1] || ""
                                , r = e[3];
                            if (!r)
                                return n;
                            if (t) {
                                var o = (a = r,
                                "/*# sourceMappingURL=data:application/json;charset=utf-8;base64," + new Buffer(JSON.stringify(a)).toString("base64") + " */")
                                    , i = r.sources.map(function(e) {
                                    return "/*# sourceURL=" + r.sourceRoot + e + " */"
                                });
                                return [n].concat(i).concat([o]).join("\n")
                            }
                            var a;
                            return [n].join("\n")
                        }(t, e);
                        return t[2] ? "@media " + t[2] + "{" + n + "}" : n
                    }).join("")
                }
                    ,
                    t.i = function(e, n) {
                        "string" == typeof e && (e = [[null, e, ""]]);
                        for (var r = {}, o = 0; o < this.length; o++) {
                            var i = this[o][0];
                            "number" == typeof i && (r[i] = !0)
                        }
                        for (o = 0; o < e.length; o++) {
                            var a = e[o];
                            "number" == typeof a[0] && r[a[0]] || (n && !a[2] ? a[2] = n : n && (a[2] = "(" + a[2] + ") and (" + n + ")"),
                                t.push(a))
                        }
                    }
                    ,
                    t
            }
        }
        , function(e, t, n) {
            var r = "undefined" != typeof document;
            if ("undefined" != typeof DEBUG && DEBUG && !r)
                throw new Error("vue-style-loader cannot be used in a non-browser environment. Use { target: 'node' } in your Webpack config to indicate a server-rendering environment.");
            var o = n(6)
                , i = {}
                , a = r && (document.head || document.getElementsByTagName("head")[0])
                , l = null
                , s = 0
                , u = !1
                , d = function() {}
                , c = null
                , f = "data-vue-ssr-id"
                , p = "undefined" != typeof navigator && /msie [6-9]\b/.test(navigator.userAgent.toLowerCase());
            function h(e) {
                for (var t = 0; t < e.length; t++) {
                    var n = e[t]
                        , r = i[n.id];
                    if (r) {
                        r.refs++;
                        for (var o = 0; o < r.parts.length; o++)
                            r.parts[o](n.parts[o]);
                        for (; o < n.parts.length; o++)
                            r.parts.push(m(n.parts[o]));
                        r.parts.length > n.parts.length && (r.parts.length = n.parts.length)
                    } else {
                        var a = [];
                        for (o = 0; o < n.parts.length; o++)
                            a.push(m(n.parts[o]));
                        i[n.id] = {
                            id: n.id,
                            refs: 1,
                            parts: a
                        }
                    }
                }
            }
            function v() {
                var e = document.createElement("style");
                return e.type = "text/css",
                    a.appendChild(e),
                    e
            }
            function m(e) {
                var t, n, r = document.querySelector("style[" + f + '~="' + e.id + '"]');
                if (r) {
                    if (u)
                        return d;
                    r.parentNode.removeChild(r)
                }
                if (p) {
                    var o = s++;
                    r = l || (l = v()),
                        t = g.bind(null, r, o, !1),
                        n = g.bind(null, r, o, !0)
                } else
                    r = v(),
                        t = function(e, t) {
                            var n = t.css
                                , r = t.media
                                , o = t.sourceMap;
                            r && e.setAttribute("media", r);
                            c.ssrId && e.setAttribute(f, t.id);
                            o && (n += "\n/*# sourceURL=" + o.sources[0] + " */",
                                n += "\n/*# sourceMappingURL=data:application/json;base64," + btoa(unescape(encodeURIComponent(JSON.stringify(o)))) + " */");
                            if (e.styleSheet)
                                e.styleSheet.cssText = n;
                            else {
                                for (; e.firstChild; )
                                    e.removeChild(e.firstChild);
                                e.appendChild(document.createTextNode(n))
                            }
                        }
                            .bind(null, r),
                        n = function() {
                            r.parentNode.removeChild(r)
                        }
                    ;
                return t(e),
                    function(r) {
                        if (r) {
                            if (r.css === e.css && r.media === e.media && r.sourceMap === e.sourceMap)
                                return;
                            t(e = r)
                        } else
                            n()
                    }
            }
            e.exports = function(e, t, n, r) {
                u = n,
                    c = r || {};
                var a = o(e, t);
                return h(a),
                    function(t) {
                        for (var n = [], r = 0; r < a.length; r++) {
                            var l = a[r];
                            (s = i[l.id]).refs--,
                                n.push(s)
                        }
                        t ? h(a = o(e, t)) : a = [];
                        for (r = 0; r < n.length; r++) {
                            var s;
                            if (0 === (s = n[r]).refs) {
                                for (var u = 0; u < s.parts.length; u++)
                                    s.parts[u]();
                                delete i[s.id]
                            }
                        }
                    }
            }
            ;
            var b, y = (b = [],
                    function(e, t) {
                        return b[e] = t,
                            b.filter(Boolean).join("\n")
                    }
            );
            function g(e, t, n, r) {
                var o = n ? "" : r.css;
                if (e.styleSheet)
                    e.styleSheet.cssText = y(t, o);
                else {
                    var i = document.createTextNode(o)
                        , a = e.childNodes;
                    a[t] && e.removeChild(a[t]),
                        a.length ? e.insertBefore(i, a[t]) : e.appendChild(i)
                }
            }
        }
        , function(e, t) {
            e.exports = function(e, t) {
                for (var n = [], r = {}, o = 0; o < t.length; o++) {
                    var i = t[o]
                        , a = i[0]
                        , l = {
                        id: e + ":" + o,
                        css: i[1],
                        media: i[2],
                        sourceMap: i[3]
                    };
                    r[a] ? r[a].parts.push(l) : n.push(r[a] = {
                        id: a,
                        parts: [l]
                    })
                }
                return n
            }
        }
        , function(e, t) {
            e.exports = function(e, t, n, r, o, i) {
                var a, l = e = e || {}, s = typeof e.default;
                "object" !== s && "function" !== s || (a = e,
                    l = e.default);
                var u, d = "function" == typeof l ? l.options : l;
                if (t && (d.render = t.render,
                    d.staticRenderFns = t.staticRenderFns,
                    d._compiled = !0),
                n && (d.functional = !0),
                o && (d._scopeId = o),
                    i ? (u = function(e) {
                        (e = e || this.$vnode && this.$vnode.ssrContext || this.parent && this.parent.$vnode && this.parent.$vnode.ssrContext) || "undefined" == typeof __VUE_SSR_CONTEXT__ || (e = __VUE_SSR_CONTEXT__),
                        r && r.call(this, e),
                        e && e._registeredComponents && e._registeredComponents.add(i)
                    }
                        ,
                        d._ssrRegister = u) : r && (u = r),
                    u) {
                    var c = d.functional
                        , f = c ? d.render : d.beforeCreate;
                    c ? (d._injectStyles = u,
                            d.render = function(e, t) {
                                return u.call(t),
                                    f(e, t)
                            }
                    ) : d.beforeCreate = f ? [].concat(f, u) : [u]
                }
                return {
                    esModule: a,
                    exports: l,
                    options: d
                }
            }
        }
        , function(e, t, n) {
            "use strict";
            var r = function() {
                var e = this
                    , t = e.$createElement
                    , n = e._self._c || t;
                return n("div", {
                    attrs: {
                        id: "selecttree"
                    }
                }, [n("el-select", {
                    ref: "tsSselect",
                    attrs: {
                        value: e.valueTitle,
                        clearable: e.clearable
                    },
                    on: {
                        clear: e.clearHandle
                    }
                }, [n("el-option", {
                    staticClass: "options",
                    attrs: {
                        value: e.valueTitle,
                        label: e.valueTitle
                    }
                }, [n("el-tree", {
                    ref: "selectTree",
                    attrs: {
                        id: "tree-option",
                        "default-expand-all": "",
                        "expand-on-click-node": !1,
                        accordion: e.accordion,
                        data: e.options,
                        props: e.props,
                        "node-key": e.props.value,
                        "default-expanded-keys": e.defaultExpandedKey
                    },
                    on: {
                        "node-click": e.handleNodeClick
                    }
                })], 1)], 1)], 1)
            };
            r._withStripped = !0;
            var o = {
                render: r,
                staticRenderFns: []
            };
            t.a = o
        }
    ]).default
});
Vue.component('tree-select', treeSelect)
