/*
 * @Author: Wonder2019
 * @Date: 2020-04-16 16:45:49
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-04-21 15:38:05
 */

/**
 * 预加载处理器
 *
 * @class PreProgress
 */
class PreProgress {
    /**
     * Creates an instance of PreProgress
     * @param {boolean} [hasLock=true]
     * @param {CallableFunction} callback when everything has done
     * @param {CallableFunction} progressingCallback when percent has changed
     * @memberof PreProgress
     */
    constructor(hasLock = true, callback, progressingCallback) {
        this.id = this.uuid(8);
        window["ppg" + this.id] = this;
        this.count = 0;
        this.percent = 0;
        this.hasLock = hasLock;
        this.callback = callback;
        this.progressingCallback = progressingCallback;
        this.isLock = false;
        this.tasks = [];
        this.state = "empty";
    }
    getId() {
        return "ppg" + this.id;
    }
    start() {
        if (this.state != "ready") {
            console.warn(`Illegal State >> ${this.state}`);
            return;
        }
        this.isLock = true;
        this.state = "progressing";
        for (let task of this.tasks) {
            setTimeout(task.fun, 10, this.getId(), ...task.params);
        }
        this.checkProgress();
    }
    checkProgress() {
        this.intervalID = setInterval(() => {
            if (this.count == this.tasks.length) {
                clearInterval(this.intervalID);
                this.state = "complete";
                this.callback && this.callback(this.id, this.tasks);
            } else {
                this.percent = Math.floor((this.count * 100) / this.tasks.length) / 100;
                this.progressingCallback && this.progressingCallback(this.percent * 100 + "%");
            }
        }, 300);
    }
    /**
     * 添加预加载任务
     *
     * @param {Object} task
     * @returns
     * @memberof PreProgress
     */
    addTask(task) {
        if (this.hasLock && this.isLock) {
            console.warn("This task queue has terminated");
            return;
        }
        if (/window\[.*\]\.count\+\+/.test(task.fun.toString())) {
            this.state = "ready";
            this.tasks.push(task);
        } else {
            console.warn(
                `Task ${task.fun.name} does not self-increase window["ppg${this.id}"].count, and will not be able to record the execution of this task, so it will not be joined the task queue!`
            );
        }
    }

    addAllTask(tasks) {
        tasks.forEach((item) => {
            this.addTask(item);
        });
    }

    uuid(len, radix) {
        let chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");
        let uuid = [];
        let i;
        radix = radix || chars.length;

        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | (Math.random() * radix)];
        } else {
            // rfc4122, version 4 form
            var r;

            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = "-";
            uuid[14] = "4";

            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | (Math.random() * 16);
                    uuid[i] = chars[i == 19 ? (r & 0x3) | 0x8 : r];
                }
            }
        }

        return uuid.join("");
    }
}
