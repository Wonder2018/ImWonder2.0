let updateAPI = "updateAjax";
let tbjson = [
	{
		tagName: "td",
	},
	{
		"edit-type": "comtext",
		"edit-key": "name",
		tagName: "td",
	},
	{
		"edit-type": "iconfont",
		"edit-key": "icon",
		tagName: "td",
	},
	{
		"edit-type": "comtext",
		"edit-key": "perm",
		tagName: "td",
	},
	{
		"edit-type": "comtext",
		"edit-key": "order",
		tagName: "td",
	},
	{
		"edit-type": "comtext",
		"edit-key": "href",
		tagName: "td",
	},
	{
		children: [
			{
				"dt-type": "edit",
				onclick: "insert",
				tagName: "button",
				text: "保存",
			},
			{
				"dt-type": "cancel",
				onclick: "cancel",
				tagName: "button",
				text: "取消",
			},
        ],
        tagName:"td",
	},
];
