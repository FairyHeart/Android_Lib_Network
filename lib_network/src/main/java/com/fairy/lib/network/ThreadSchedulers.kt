package com.fairy.lib.network

/**
 * 运行线程枚举
 *
 * @author: Fairy.
 * @date  : 2020/10/14.
 */

enum class ThreadSchedulers(val value: Int) {
    /**
     * 当前线程
     */
    CURRENT_THREAD(0x0001),

    /**
     * 主线程
     */
    MAIN_THREAD(0x0002),

    /**
     * 子线程
     */
    CHILD_THREAD(0x0003)
}
