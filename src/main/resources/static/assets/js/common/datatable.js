/*
 * @Author: Wonder2019
 * @Date: 2020-08-19 10:09:14
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-24 10:01:46
 */

function comtext(dom, action, value) {
	let text;
	switch (action) {
		case "edit":
			text = value || dom.innerText;
			let editable = document.createElement("div");
			editable.classList.add("editable-div");
			editable.innerText = text;
			editable.contentEditable = true;
			clean(dom);
			dom.appendChild(editable);
			dom["dt-ov"] = text;
			break;
		case "recover":
			dom.querySelector(".editable-div").innerText = dom["dt-ov"];
			break;
		case "cancel":
			clean(dom);
			dom.innerText = dom["dt-ov"];
			break;
		case "save":
			text = value || dom.querySelector(".editable-div").innerText;
			clean(dom);
			dom.innerText = text;
			break;
		case "value":
			return dom.querySelector(".editable-div").innerText;
		default:
			break;
	}
}

function iconfont(dom, action, value) {
	switch (action) {
		case "edit":
			value = value || dom.innerHTML.match(/"iconfont icon-.*"/);
			value = value ? value[0].replace('"iconfont icon-', "").replace('"', "") : "";
			comtext(dom, "edit", value);
			break;
		case "recover":
			comtext(dom, "recover");
			break;
		case "cancel":
			iconfont(dom, "save", dom["dt-ov"]);
			break;
		case "save":
			value = value || comtext(dom, "value");
			let span = document.createElement("span");
			span.classList.add("iconfont", `icon-${value}`);
			clean(dom);
			dom.appendChild(span);
			break;
		case "value":
			return comtext(dom, "value");
		default:
			break;
	}
}

function createRow(format) {
	let child = document.createElement(format.tagName);
	format["edit-type"] && child.setAttribute("edit-type", format["edit-type"]);
	format["edit-key"] && child.setAttribute("edit-key", format["edit-key"]);
	format["dt-type"] && child.setAttribute("dt-type", format["dt-type"]);
	format.text && (child.innerText = format.text);
	format.onclick &&
		(child.onclick = function () {
			window[format.onclick]("insert");
		});
	format.children &&
		(function () {
			for (let item of format.children) {
				child.appendChild(createRow(item));
				child.appendChild(document.createTextNode(" "));
			}
		})();
	return child;
}

function clean(dom) {
	let child = dom.firstChild;
	while (child) {
		child.remove();
		child = dom.firstChild;
	}
}

function add(formats, selector) {
	$(`[dt-type="adro"]`).attr("disabled", true);
	let tbody = document.querySelector(selector).tBodies[0];
	let first = tbody.rows[0];
	let row = document.createElement("tr");
	row.id = "tbinsert";
	for (let item of formats) {
		row.appendChild(createRow(item));
	}
	console.log(tbody, first, row);
	if (first) {
		tbody.insertBefore(row, first);
	} else {
		tbody.appendChild(row);
	}
	for (let item of $(`#tbinsert [edit-type]`)) {
		window[item.getAttribute("edit-type")](item, "edit");
	}
}

function edit(id) {
	$(`#tb${id} [dt-type="edit"]`).html("保存")[0].onclick = function () {
		update(id);
	};
	for (let item of $(`#tb${id} [edit-type]`)) {
		window[item.getAttribute("edit-type")](item, "edit");
	}
}

function recover(id) {
	for (let item of $(`#tb${id} [edit-type]`)) {
		window[item.getAttribute("edit-type")](item, "recover");
	}
}

function cancel(id) {
	if (id == "insert") {
		$("#tbinsert")[0].remove();
		$(`[edit-type="adro"]`).attr("disabled", false);
		return "cancel";
	}
	for (let item of $(`#tb${id} [edit-type]`)) {
		window[item.getAttribute("edit-type")](item, "cancel");
	}
	$(`#tb${id} [dt-type="edit"]`).html("编辑")[0].onclick = function () {
		edit(id);
	};
	$(`#tb${id} [dt-type]`).attr("disabled", false);
}

function save(id, obj) {
	if(id == "insert"){
		window.location.reload();
		return "save";
	}
	for (let item of $(`#tb${id} [edit-type]`)) {
		window[item.getAttribute("edit-type")](item, "save", obj.result[item.getAttribute("edit-key")]);
	}
	$(`#tb${id} [dt-type="edit"]`).html("编辑")[0].onclick = function () {
		edit(id);
	};
	$(`#tb${id} [dt-type]`).attr("disabled", false);
}

function ajerror(id, obj, status) {
	alert(`${status}：${obj.code} - ${obj.msg}`);
	recover(id);
	$(`#tb${id} [dt-type]`).attr("disabled", false);
}

function insert(id) {
	submit(id, insertAPI);
}

function update(id) {
	submit(id, updateAPI);
}

function submit(id, api) {
	parm = { id: id };
	for (let item of $(`#tb${id} [edit-type]`)) {
		parm[item.getAttribute("edit-key")] = window[item.getAttribute("edit-type")](item, "value");
	}
	$(`#tb${id} [dt-type]`).attr("disabled", true);
	$.ajax({
		cache: false,
		type: "POST",
		url: api,
		data: parm,
		dataType: "json",
		success(xhr) {
			save(id, xhr);
		},
		error(xhr, status) {
			ajerror(id, xhr.responseJSON, status);
		},
	});
}
