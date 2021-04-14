let updateAPI = "../api/perms/${id}/${name}";
let insertAPI = "../api/perms/${id}/${name}";
let deleteAPI = "../api/perms/${id}";
let retObjKey = "perm";
let tbjson = [
	{
		tagName: "td",
	},
	{
		editType: "comtext",
		editKey: "id",
		tagName: "td",
	},
	{
		editType: "comtext",
		editKey: "name",
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
	let api = insertAPI.replace("${id}", base64.encode(param.id));
	api = api.replace("${name}", param.name);
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
	let restBody = new RESTBody(deleteAPI.replace("${id}", base64.encode(param.id)), "DELETE", {}, retObjKey);
	restBody.setEnableCsrf(true);
	restBody.setCsrfKey(_chn);
	restBody.setCsrfToken(_ctv);
	return restBody;
}
