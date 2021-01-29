let base64 = {
	/**
	 * 字符串转 base64
	 *
	 * @param {String} str 要加密的字符串
	 * @returns {String} base64 值
	 */
	encode(str) {
		return btoa(encodeURI(str)).replace(/=+$/, "");
	},
	/**
	 * base64 转字符串
	 *
	 * @param {String} str 要解密的 base64
	 * @returns {String} 字符串值
	 */
	decode(str) {
		return decodeURI(atob(str));
	},
};