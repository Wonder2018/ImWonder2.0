/*
 * @Author: Wonder2019
 * @Date: 2020-08-06 10:44:56
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-02-16 16:07:25
 */

/**
 * 通过预加载初始化标签云的回调
 *
 * @param {String} progressId 预加载器 ID
 */
function initTagList(progressId) {
	$(".tag-cloud-board").jQCloud(window.tagList, {
		removeOverflowing: true,
		shape: "rectangular",
	});
	window[progressId].count++;
}

function initControlGroups(progressId) {
	let acgs = document.querySelectorAll(".control-group");
	for (let item of acgs) {
		controlGroups[item.querySelector(`input[class="form-control"]`).id] = item;
	}
	window[progressId].count++;
}

/**
 * 在 onready 中添加事件监听器
 *
 * @param {String} progressId
 */
function initListener(progressId) {
	let controllers = document.querySelectorAll("input.form-control,select.form-control");
	for (let item of controllers) {
		if (listener[item.name]) {
			for (let ln in listener[item.name]) {
				if (ln) item.addEventListener(ln, listener[item.name][ln]);
			}
		}
	}
	window[progressId].count++;
}

/**
 * 在 onready 中添加绑定事件
 *
 * @param {String} progressId
 */
function initAjaxSubmit(progressId) {
	submitBtn.addEventListener("click", () => {
		$(submitBtn).attr("disabled",true)
		submitBtn.innerText = "努力提交ing。。。";
		$(".friendly-link-form").ajaxSubmit({
			url: "/api/addFriendlyLink",
			type: "post",
			dataType: "json",
			headers: hds,
			beforeSubmit(values) {
				let result = true;
				console.log(values);
				for (let value of values) {
					console.log(value);
					console.log;
					if(!value.value){
						if(value.required){
							setError(controlGroups[value.name], "此项为必填项！");
							result = false;
							continue;
						}else{
							continue;
						}
					}
					let retBody = validator[value.name] && validator[value.name](value.value);
					console.log(retBody);
					if (retBody && retBody.code != "ok") {
						tipsSetter(controlGroups[value.name], retBody);
						if (retBody.code == "err") {
							result = false;
						}
						continue;
					}
					if (value.type == "file" && value.value.size >= 3145728) {
						setError(controlGroups[value.name], "文件太大了！不能超过 3M (ノへ￣、)");
						result = false;
						continue;
					}
				}
				if (!result) {
					$(submitBtn).attr("disabled",false)
					submitBtn.innerText = "提交";
				}
				return result;
			},
			error(a, b, c, d) {
				console.log("error", a, b, c, d);
				alert("出错了！(ノへ￣、)\n请联系管理员解决。。。");
				$(submitBtn).attr("disabled",false)
				submitBtn.innerText = "提交";
			},
			success(data) {
				if (data.code && data.code != "200") {
					alert("出错了！(ノへ￣、)\n请联系管理员解决。。。\n错误详情：" + data.msg);
					$(submitBtn).attr("disabled",false)
					submitBtn.innerText = "提交";
					return "error";
				}
				alert("你的网站已经成功加入！\n肥肠感谢您的参与！(～￣▽￣)～");
				window.location.href = "/";
			},
		});
	});
	window[progressId].count++;
}

/**
 *	清除提示信息
 *
 * @param {HTMLElement} controlGroup
 */
function cleanTips(controlGroup) {
	controlGroup.classList.remove("onerror", "onwarn", "right");
	let tipsBox = controlGroup.querySelector(".tips-box");
	tipsBox.classList.remove("icon-error", "icon-warn", "icon-right");
	tipsBox.setAttribute("tips-msg", "");
}

/**
 *	设置异常信息
 *
 * @param {HTMLElement} controlGroup
 * @param {string} [msg=""]
 */
function setError(controlGroup, msg = "") {
	controlGroup.classList.add("onerror");
	controlGroup.classList.remove("onwarn", "right");
	let tipsBox = controlGroup.querySelector(".tips-box");
	tipsBox.classList.add("icon-error");
	tipsBox.classList.remove("icon-warn", "icon-right");
	tipsBox.setAttribute("tips-msg", msg);
}

/**
 *	设置警告信息
 *
 * @param {HTMLElement} controlGroup
 * @param {string} [msg=""]
 */
function setWarn(controlGroup, msg = "") {
	controlGroup.classList.add("onwarn");
	controlGroup.classList.remove("onerror", "right");
	let tipsBox = controlGroup.querySelector(".tips-box");
	tipsBox.classList.add("icon-warn");
	tipsBox.classList.remove("icon-error", "icon-right");
	tipsBox.setAttribute("tips-msg", msg);
}

/**
 * 设置正确提示信息
 *
 * @param {HTMLElement} controlGroup
 * @param {string} [msg=""]
 */
function setRight(controlGroup, msg = "") {
	controlGroup.classList.add("right");
	controlGroup.classList.remove("onerror", "onwarn");
	let tipsBox = controlGroup.querySelector(".tips-box");
	tipsBox.classList.add("icon-right");
	tipsBox.classList.remove("icon-error", "icon-warn");
	tipsBox.setAttribute("tips-msg", msg);
}

/**
 * 通用消息设置
 *
 * @param {Element} controlGroup
 * @param {any} retBody
 */
function tipsSetter(controlGroup, retBody) {
	switch (retBody.code) {
		case "ok":
			setRight(controlGroup, retBody.msg);
			break;
		case "warn":
			setWarn(controlGroup, retBody.msg);
			break;
		case "err":
			setError(controlGroup, retBody.msg);
			break;
		default:
			break;
	}
}

// 正则
let regUrl = /^http(s)?:\/\/(?![\-\.])([\w\-]*\.)+\w[\w\-]*(?<![\-\.])(\/(?![\-\.])[\w\-\.]*(?<![\-\.]))*[\w\/]$/;
let regIconUrl = /^http(s)?:\/\/(?![\-\.])([\w\-]*\.)+\w[\w\-]*(?<![\-\.])(\/(?![\-\.])[\w\-\.]*(?<![\-\.]))*(\?([0-9A-Za-z]+=[0-9A-Za-z]+&?)+)*/;
let regEmail = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9\.\-]+\.([a-zA-Z]{2,4})$/;

// csrf
let hds = {};
hds[window._chn] = window._ctv;

// 提示容器
let controlGroups = {};

// 表单验证器
let validator = {
	url(value) {
		if (value.length > 32) {
			// 超过字长，显示异常
			return { code: "err", msg: "url 过长！" };
		}
		if (regUrl.test(value)) {
			if (value.startsWith("https")) {
				// 检测到 https协议url 显示正确信息
				return { code: "ok", msg: "" };
			} else {
				// 检测到 http协议url，显示警告
				return { code: "warn", msg: "url输入正确，但推荐您为你的网站开启https功能！" };
			}
		} else if (value) {
			// 检测到非法字符串，发出异常。
			return { code: "err", msg: "请输入正确的Url" };
		}
	},
	siteName(value) {
		if (value.length < 3) {
			return { code: "err", msg: "网站标题过短" };
		}
		if (value.length > 10 && value.length < 20) {
			return { code: "warn", msg: "网站标题过长会导致显示不完整，但不影响SEO" };
		}
		if (value.length > 20) {
			return { code: "err", msg: "网站标题过长！" };
		}
		return { code: "ok", msg: "" };
	},
	bili(value) {
		if (!isNaN(value) && value.indexOf("e") < 0) {
			return { code: "ok", msg: "好像没什么问题。" };
		}
		return { code: "err", msg: "UID 可以在B站个人中心看到！" };
	},
	qq(value) {
		if (!isNaN(value) && value.indexOf("e") < 0) {
			if (value.length < 4) {
				return { code: "err", msg: "真的有这么靓的靓号吗？" };
			}
			if (value.length > 13) {
				return { code: "err", msg: "你的QQ号好长啊！" };
			}
			return { code: "ok", msg: "好像没什么问题。" };
		}
		return { code: "err", msg: "这不是QQ号吧。。。" };
	},
	github(value) {
		if (/\w+/.test(value)) {
			return { code: "ok", msg: "好像没什么问题。" };
		}
		return { code: "err", msg: "没有这么秀的用户名吧。。" };
	},
	iconUrl(value) {
		if (regIconUrl.test(value)) {
			return { code: "ok", msg: "好像没什么问题。" };
		}
		return { code: "err", msg: "请输入正确的，完整的url" };
	},
	file(value) {
		if (value) {
			filePlaceholder.innerText = value.split(/\\|\//).pop();
			return { code: "ok", msg: "" };
		}
	},
	master(value) {
		if (value.length > 20) {
			return { code: "err", msg: "名字太长了，太长了鸭！！！" };
		}
		return { code: "ok", msg: "这个名字真不啜！" };
	},
	email(value) {
		if (value.length > 32) {
			return { code: "err", msg: "邮箱太长了！！！" };
		}
		if (regEmail.test(value)) {
			return { code: "ok", msg: "" };
		}
		return { code: "err", msg: "这。。不是邮箱吧。" };
	},
	pwd(value) {
		if (value.length > 0) {
			return { code: "ok", msg: "你的密码将使用BCrypt加盐加密保存！" };
		}
		return { code: "ok", msg: "" };
	},
};

// icon 控件属性
let iconInfo = {
	bili: {
		label: "关键信息：",
		placeholder: "B站 UID(本站不会保存，获取头像后清除)",
		type: "text",
	},
	qq: {
		label: "关键信息：",
		placeholder: "QQ 号(本站不会保存，获取头像后清除)",
		type: "text",
	},
	github: {
		label: "关键信息：",
		placeholder: "Github用户名(本站不会保存，获取头像后清除)",
		type: "text",
	},
	iconUrl: {
		label: "关键信息：",
		placeholder: "图片地址（可使用网站 favicon 文件）",
		type: "text",
	},
	file: {
		label: "上传图标：",
		type: "file",
		accept: "image/*",
	},
};

// 控件监听器
let listener = {
	url: {
		keyup() {
			let url = document.querySelector(`input[id="url"]`).value.trim();
			let controlGroup = controlGroups.url;
			if (!url) {
				cleanTips(controlGroup);
				return;
			}
			tipsSetter(controlGroup, validator.url(url));
		},
	},
	siteName: {
		keyup() {
			let siteName = document.querySelector(`input[id="siteName"]`).value.trimStart();
			let controlGroup = controlGroups.siteName;
			if (!siteName) {
				cleanTips(controlGroup);
				return;
			}
			tipsSetter(controlGroup, validator.siteName(siteName));
		},
	},
	iconType: {
		change() {
			let iconType = document.querySelector(`select[id="iconType"]`);
			let format = iconInfo[iconType.value];
			let controlGroup = controlGroups.icon;
			cleanTips(controlGroup);
			if (format) {
				if (iconType.value == "file") {
					iconGroup.querySelector(".control-group").classList.add("file-group");
					filePlaceholder.classList.remove("hide");
					filePlaceholder.innerText = "点击以选择文件";
				} else {
					iconGroup.querySelector(".control-group").classList.remove("file-group");
					filePlaceholder.classList.add("hide");
				}
				iconLabel.innerText = format.label;
				icon.type = format.type;
				icon.value = "";
				format.placeholder && icon.setAttribute("placeholder", format.placeholder);
				format.accept && icon.setAttribute("accept", format.accept);
				iconGroup.classList.remove("hide");
			} else {
				iconGroup.classList.add("hide");
			}
		},
	},
	icon: {
		keyup() {
			let iconType = document.querySelector(`select[id="iconType"]`).value;
			if (iconType == "file") {
				return;
			}
			let controlGroup = controlGroups.icon;
			let iv = icon.value;
			let retBody = validator[iconType](iv);
			tipsSetter(controlGroup, retBody);
		},
		change() {
			let iconType = document.querySelector(`select[id="iconType"]`).value;
			let controlGroup = controlGroups.icon;
			let iv = icon.value;
			if (!iv) {
				cleanTips(controlGroup);
				return;
			}
			let retBody = validator[iconType](iv);
			tipsSetter(controlGroup, retBody);
		},
	},
	master: {
		keyup() {
			let master = document.querySelector(`input[id="master"]`).value.trimStart();
			let controlGroup = controlGroups.master;
			if (!master) {
				cleanTips(controlGroup);
				return;
			}
			tipsSetter(controlGroup, validator.master(master));
		},
	},
	email: {
		keyup() {
			let email = document.querySelector(`input[id="email"]`).value.trimStart();
			let controlGroup = controlGroups.email;
			if (!email) {
				cleanTips(controlGroup);
				return;
			}
			tipsSetter(controlGroup, validator.email(email));
		},
	},
	pwd: {
		keyup() {
			let pwd = document.querySelector(`input[id="pwd"]`).value.trimStart();
			let controlGroup = controlGroups.pwd;
			if (!pwd) {
				cleanTips(controlGroup);
				return;
			}
			tipsSetter(controlGroup, validator.pwd(pwd));
		},
	},
};

// 初始化任务列表
if (!window.wonderTask) {
	window.wonderTask = [];
}

// 添加预加载任务
window.wonderTask.push({
	fun: loadBackground,
	params: [null, "body"],
});

window.wonderTask.push({
	fun: initTagList,
	params: [],
});

window.wonderTask.push({
	fun: initControlGroups,
	params: [],
});

window.wonderTask.push({
	fun: initAjaxSubmit,
	params: [],
});

window.wonderTask.push({
	fun: initListener,
	params: [],
});
