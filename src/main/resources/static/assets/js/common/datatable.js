/*
 * @Author: Wonder2019
 * @Date: 2020-08-19 10:09:14
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-08-19 19:22:44
 */

function comtext(dom, action, value) {
	let text;
	switch (action) {
		case "edit":
			text = value || dom.innerHTML;
			let editable = document.createElement("div");
			editable.classList.add("editable-div");
			editable.innerHTML = text;
			editable.contentEditable = true;
			clean(dom);
			dom.appendChild(editable);
			dom["dt-ov"] = text;
			break;
		case "recover":
			dom.querySelector(".editable-div").innerHTML = dom["dt-ov"];
			break;
		case "save":
			text = value || dom.querySelector(".editable-div").innerHTML;
			clean(dom);
			dom.innerHTML = text;
			break;
		case "value":
			return dom.querySelector(".editable-div").innerHTML;
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
	format.text && (child.innerHTML = format.text);
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
	alert(id);
}

function save(id, obj) {
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
	alert(id);
}

function update(id) {
	parm = { id: id };
	for (let item of $(`#tb${id} [edit-type]`)) {
		parm[item.getAttribute("edit-key")] = window[item.getAttribute("edit-type")](item, "value");
	}
	$(`#tb${id} [dt-type]`).attr("disabled", true);
	$.ajax({
		cache: false,
		type: "POST",
		url: updateAPI,
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
