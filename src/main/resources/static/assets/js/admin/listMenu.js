let updateAPI = "../api/menus/${id}/${name}/${icon}/${perm}/${order}/${href}";
let insertAPI = "../api/menus/${name}/${icon}/${perm}/${order}/${href}";
let deleteAPI = "../api/menus/${id}";
let retObjKey = "menu";
let tbjson = [
	{
		tagName: "td",
	},
	{
		editType: "comtext",
		editKey: "name",
		tagName: "td",
	},
	{
		editType: "iconfont",
		editKey: "icon",
		tagName: "td",
	},
	{
		editType: "comtext",
		editKey: "perm",
		tagName: "td",
	},
	{
		editType: "comtext",
		editKey: "order",
		tagName: "td",
	},
	{
		editType: "comtext",
		editKey: "href",
		tagName: "td",
	},
	{
		children: [
			{
				dtType: "edit",
				onclick: "submit",
				tagName: "button",
				text: "保存",
			},
			{
				dtType: "cancel",
				onclick: "cancel",
				tagName: "button",
				text: "取消",
			},
		],
		tagName: "td",
	},
];

/**
 * 构造 RESTful 请求
 *
 * @param {String} action 要进行的操作
 * @param {Object} param 要使用到的参数
 * @returns {RESTBody}
 */
function parseRequest(action, param) {
	switch (action) {
		case "update":
			return parseUpdate(param);
		case "insert":
			return parseInsert(param);
		case "delete":
			return parseDelete(param);
		default:
			alert("异常调用！");
			break;
	}
}

/**
 * 构造更新请求
 *
 * @param {String} param 要使用到的参数
 * @returns {RESTBody}
 */
function parseUpdate(param) {
	let api = updateAPI.replace("${id}", param.id);
	api = api.replace("${name}", param.name);
	api = api.replace("${icon}", param.icon);
	api = api.replace("${perm}", base64.encode(param.perm));
	api = api.replace("${order}", param.order);
	api = api.replace("${href}", base64.encode(param.href));
	let restBody = new RESTBody(api, "PUT", {}, retObjKey);
	restBody.setEnableCsrf(true);
	restBody.setCsrfKey(_chn);
	restBody.setCsrfToken(_ctv);
	return restBody;
}

/**
 * 构造新增请求
 *
 * @param {String} param 要使用到的参数
 * @returns {RESTBody}
 */
function parseInsert(param) {
	let api = insertAPI.replace("${name}", param.name);
	api = api.replace("${icon}", param.icon);
	api = api.replace("${perm}", base64.encode(param.perm));
	api = api.replace("${order}", param.order);
	api = api.replace("${href}", base64.encode(param.href));
	let restBody = new RESTBody(api, "POST", {}, retObjKey);
	restBody.setEnableCsrf(true);
	restBody.setCsrfKey(_chn);
	restBody.setCsrfToken(_ctv);
	return restBody;
}
/**
 * 构造删除请求
 *
 * @param {String} param 要使用到的参数
 * @returns {RESTBody}
 */
function parseDelete(param) {
	let restBody = new RESTBody(deleteAPI.replace("${id}", param.id), "DELETE", {}, retObjKey);
	restBody.setEnableCsrf(true);
	restBody.setCsrfKey(_chn);
	restBody.setCsrfToken(_ctv);
	return restBody;
}
