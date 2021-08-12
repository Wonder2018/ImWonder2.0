/**
 * 将数据列表渲染为树结构
 *
 * @class DataTree
 */
class DataTree {
	/**
	 * Creates an instance of DataTree.
	 *
	 * @param {HTMLElement} parent
	 * @param {*} option
	 * @param {*} data
	 * @memberof DataTree
	 */
	constructor(option, data) {
		Object.assign(this, option);
		if (data) {
			this.initData(data);
		}
	}

	initData(data) {
		this.parent.innerHTML = "";
	}

	createList(parent, list) {}
}

class DataList extends DataNode {
	constructor(data) {
		this.isList = true;
		this.NodeList = [];
        for (let item of data) {
            if(Array.isArray(item)){
                this.addNode(new DataList(item))
            }else{
                this.addNode(new DataNode(item));
            }
        }
	}

	addNode(node) {
		this.NodeList.unshift(node);
	}
}

class DataNode {
	constructor() {
		this.isList = false;
	}
}

Array.prototype.diff = function (a) {
	return this.filter(function (i) {
		return a.indexOf(i) < 0;
	});
};
