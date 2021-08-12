/*
 * @Author: Wonder2019
 * @Date: 2020-08-19 10:09:14
 * @Last Modified by: Wonder2021
 * @Last Modified time: 2021-08-09 16:00:19
 */

let datatable = {
    /**
     * 处理普通文本编辑框
     *
     * @param {HTMLElement} dom 单元格节点
     * @param {String} action 操作类型
     * @param {String} value 立即值
     * @returns {String} 编辑框当前值
     */
    comtext(dom, action, value) {
        let text;
        switch (action) {
            case "edit":
                text = value || dom.innerText;
                let editable = document.createElement("div");
                editable.classList.add("editable-div");
                editable.innerText = text;
                editable.contentEditable = true;
                this.clean(dom);
                dom.appendChild(editable);
                dom["dt-ov"] = text;
                break;
            case "recover":
                dom.querySelector(".editable-div").innerText = dom["dt-ov"];
                break;
            case "cancel":
                this.clean(dom);
                dom.innerText = dom["dt-ov"];
                break;
            case "save":
                text = value || dom.querySelector(".editable-div").innerText;
                this.clean(dom);
                dom.innerText = text;
                break;
            case "value":
                let el = dom.querySelector(".editable-div");
                return el ? el.innerText : dom.innerText;
            default:
                break;
        }
    },

    /**
     * 处理 iconfont 编辑框
     *
     * @param {HTMLElement} dom 单元格节点
     * @param {String} action 操作类型
     * @param {String} value 立即值
     * @returns {String} 编辑框当前值
     */
    iconfont(dom, action, value) {
        let text;
        switch (action) {
            case "edit":
                text = value || dom.innerHTML.match(/"iconfont icon-.*"/);
                text = text ? (value ? value : text[0].replace('"iconfont icon-', "").replace('"', "")) : "";
                this.comtext(dom, "edit", text);
                break;
            case "recover":
                this.comtext(dom, "recover");
                break;
            case "cancel":
                this.iconfont(dom, "save", dom["dt-ov"]);
                break;
            case "save":
                text = value || this.comtext(dom, "value");
                let span = document.createElement("span");
                span.classList.add("iconfont", `icon-${text}`);
                this.clean(dom);
                dom.appendChild(span);
                break;
            case "value":
                return this.comtext(dom, "value");
            default:
                break;
        }
    },

    /**
     * 根据模板创建单元格
     *
     * @param {object} format 模板
     * @returns {HTMLElement} 创建好的单元格
     */
    createRow(format, id) {
        let child = document.createElement(format.tagName);
        format.editType && child.setAttribute("edit-type", format.editType);
        format.editKey && child.setAttribute("edit-key", format.editKey);
        format.dtType && child.setAttribute("dt-type", format.dtType);
        format.text && child.appendChild(document.createTextNode(format.text));
        format.class && child.classList.add(...format.class);
        format.onclick &&
            (child.onclick = function () {
                datatable[format.onclick](id || "insert", id || "insert");
            });
        format.children &&
            (function () {
                for (let item of format.children) {
                    child.appendChild(datatable.createRow(item));
                    child.appendChild(document.createTextNode(" "));
                }
            })();
        return child;
    },

    /**
     * 清除所有子节点
     *
     * @param {HTMLElement} dom
     */
    clean(dom) {
        let child = dom.firstChild;
        while (child) {
            child.remove();
            child = dom.firstChild;
        }
    },

    /**
     * 添加一行
     *
     * @param {Object} formats 模板
     * @param {String} selector css选择器
     */
    add(formats, selector) {
        $(`[dt-type="adro"]`).attr("disabled", true);
        let tbody = document.querySelector(selector).tBodies[0];
        let first = tbody.rows[0];
        let row = document.createElement("tr");
        row.setAttribute("data-id", "tbinsert");
        for (let item of formats) {
            row.appendChild(this.createRow(item));
        }
        console.log(tbody, first, row);
        if (first) {
            tbody.insertBefore(row, first);
        } else {
            tbody.appendChild(row);
        }
        for (let item of $(`[data-id='tbinsert'] [edit-type]`)) {
            this[item.getAttribute("edit-type")](item, "edit");
        }
    },

    /**
     * 编辑当前行
     *
     * @param {String} id 当前行 ID
     */
    edit(id) {
        let editBtn = $(`[data-id="tb${id}"] [dt-type="edit"]`).removeClass("btn-primary").addClass("btn-success").html("保存")[0];
        let cancelBtn = this.createRow(
            {
                dtType: "cancel",
                onclick: "cancel",
                tagName: "button",
                class: ["btn", "btn-danger"],
                text: "取消",
            },
            id
        );
        editBtn.after(cancelBtn);
        editBtn.after(document.createTextNode(" "));
        editBtn.onclick = function () {
            datatable.submit(id, "update");
        };
        for (let item of $(`[data-id="tb${id}"] [edit-type]`)) {
            this[item.getAttribute("edit-type")](item, "edit");
        }
    },

    /**
     * 删除当前行
     *
     * @param {String} id 当前行 ID
     */
    dlet(id, tag) {
        if (confirm(`确定要删除 “${tag}” 吗？`)) {
            datatable.submit(id, "delete");
        }
    },

    /**
     * 恢复当前行数值
     *
     * @param {String} id 当前行 ID
     */
    recover(id) {
        for (let item of $(`[data-id="tb${id}"] [edit-type]`)) {
            this[item.getAttribute("edit-type")](item, "recover");
        }
    },

    /**
     * 取消编辑当前行，或取消创建新数据
     *
     * @param {String} id 当前行 ID
     * @returns {String} 取消创建数据时，返回 “cancel”
     */
    cancel(id) {
        if (id == "insert") {
            $("[data-id='tbinsert']")[0].remove();
            $(`[dt-type="adro"]`).attr("disabled", false);
            return "cancel";
        }
        for (let item of $(`[data-id="tb${id}"] [edit-type]`)) {
            this[item.getAttribute("edit-type")](item, "cancel");
        }
        $(`[data-id="tb${id}"] [dt-type="edit"]`).removeClass("btn-success").addClass("btn-primary").html("编辑")[0].onclick = function () {
            datatable.edit(id);
        };
        $(`[data-id="tb${id}"] [dt-type="cancel"]`).remove();
        $(`[data-id="tb${id}"] [dt-type]`).attr("disabled", false);
    },

    /**
     * 保存修改完的数据
     *
     * @param {String} id 当前行 ID
     * @param {Object} obj 服务器返回的数据
     * @returns {String} 如果操作为新增数据，则刷新页面并返回 “save”
     */
    save(id, obj) {
        if (id == "insert") {
            window.location.reload();
            return "save";
        }
        for (let item of $(`[data-id="tb${id}"] [edit-type]`)) {
            this[item.getAttribute("edit-type")](item, "save", obj[item.getAttribute("edit-key")]);
        }
        $(`[data-id="tb${id}"] [dt-type="edit"]`).removeClass("btn-success").addClass("btn-primary").html("编辑")[0].onclick = function () {
            datatable.edit(id);
        }
        $(`[data-id="tb${id}"] [dt-type="cancel"]`).remove();
        $(`[data-id="tb${id}"] [dt-type]`).attr("disabled", false);
    },

    /**
     * 服务器异常处理
     *
     * @param {String} id 当前行 ID
     * @param {Object} obj 服务器返回结果
     * @param {number} status 状态
     */
    ajerror(id, obj, status) {
        alert(`${status}：${obj.code} - ${obj.msg}`);
        this.recover(id);
        $(`[data-id="tb${id}"] [dt-type]`).attr("disabled", false);
    },

    /**
     * 提交操作
     *
     * @param {String} id 当前行ID
     * @param {String} action 要进行的操作
     */
    submit(id, action) {
        let parm = { id: id };
        for (let item of $(`[data-id="tb${id}"] [edit-type]`)) {
            parm[item.getAttribute("edit-key")] = this[item.getAttribute("edit-type")](item, "value");
        }
        let obj = parseRequest(action, parm);
        $(`[data-id="tb${id}"] [dt-type]`).attr("disabled", true);
        $.ajax({
            cache: false,
            type: obj.type,
            url: obj.api,
            data: obj.parm,
            dataType: "json",
            headers: obj.getHeaders(),
            success(xhr) {
                if (obj.type == "DELETE") {
                    alert("删除成功！");
                    window.location.reload();
                    return "delete";
                }
                datatable.save(id, xhr[obj.retObjKey || "result"]);
            },
            error(xhr, status) {
                datatable.ajerror(id, xhr.responseJSON, status);
            },
        });
    },
};

/**
 * RESTful 请求所需内容
 *
 * @class RESTBody
 */
class RESTBody {
    /**
     * RESTful 构造方法
     * @param {String} api 请求地址（包含参数）
     * @param {String} type 请求类型
     * @param {Object} parm 附加参数
     * @param {String} retObjKey 返回数据的键值
     * @memberof RESTBody
     */
    constructor(api, type, parm, retObjKey) {
        this.api = api;
        this.type = type;
        this.parm = parm;
        this.retObjKey = retObjKey;
        this.header = {};
    }
    /**
     * 设置是否开启 CSRF 功能
     *
     * @param {Boolean} enableCsrf
     * @memberof RESTBody
     */
    setEnableCsrf(enableCsrf) {
        this.enableCsrf = enableCsrf;
    }
    /**
     * 查看是否开启 CSRF 功能
     *
     * @returns {Boolean}
     * @memberof RESTBody
     */
    isEnableCsrf() {
        return this.enableCsrf;
    }

    /**
     * 设置 CSRF 键名
     *
     * @param {String} csrfKey
     * @memberof RESTBody
     */
    setCsrfKey(csrfKey) {
        this.csrfKey = csrfKey;
    }

    /**
     * 获取 CSRF 键名
     *
     * @returns {String}
     * @memberof RESTBody
     */
    getCsrfKey() {
        return this.csrfKey;
    }

    /**
     * 设置 CsrfToken 值
     *
     * @param {String} csrfToken
     * @memberof RESTBody
     */
    setCsrfToken(csrfToken) {
        this.csrfToken = csrfToken;
    }

    /**
     * 获取 CsrfToken 值
     *
     * @returns {String}
     * @memberof RESTBody
     */
    getcsrfToken() {
        return this.csrfToken;
    }

    /**
     * 添加 Http 请求头
     *
     * @param {String} key 键名
     * @param {String} value 键值
     * @memberof RESTBody
     */
    addHeader(key, value) {
        this.header[key] = value;
    }

    /**
     * 获取 Http 请求头
     *
     * @param {String} key 键名
     * @returns {String} 键值
     * @memberof RESTBody
     */
    getHeader(key) {
        return this.header[key];
    }

    /**
     * 获取完整 Http 请求头
     *
     * @returns
     * @memberof RESTBody
     */
    getHeaders() {
        if (this.enableCsrf) {
            this.header[this.csrfKey] = this.csrfToken;
        }
        return this.header;
    }
}
