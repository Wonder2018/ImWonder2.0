/*
 * @Author: Wonder2020
 * @Date: 2021-02-02 17:53:20
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-02-08 17:16:40
 */

/**
 * wonderModel模态框操作工具
 *
 * @class wonderModel
 */
class wonderModel {

	/**
	 *Creates an instance of wonderModel.
	 * @param {String} el
	 * @memberof wonderModel
	 */
	constructor(el) {
		this.el = document.querySelector(el);
		document.querySelector("el .closeBtn").addEventListener("click", this.hide);
	}

	show() {
		this.el.style.display = "block";
		setTimeout(() => {
			this.el.classList.add("onshow");
		}, 0);
	}

	hide() {
		this.el.classList.remove("onshow");
		setTimeout(() => {
			this.el.style.display = "none";
		}, 300);
    }
}
