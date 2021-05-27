package com.zyd.out.common.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebView
import com.just.agentweb.DefaultWebClient
import com.zyd.out.common.BuildConfig
import com.zyd.out.common.R
import kotlinx.android.synthetic.main.activity_webview.*


/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 *
 */
class WebActivity : AppCompatActivity() {

    private lateinit var mAgentWeb: AgentWeb//agentWeb的对象

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                ll_webview,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator(resources.getColor(R.color.colorAccent))
            .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
            .interceptUnkownUrl() //拦截找不到相关页面的Scheme
            .createAgentWeb()
            .ready()
            .get()

        val url = intent.getStringExtra("url")
        mAgentWeb.urlLoader.loadUrl(url)
        //开启webView的调试
        AgentWebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
    }

    /**
     * 处理按键的事件，来响应对应的逻辑
     *
     * @param keyCode 按键keycode
     * @param event   事件
     * @return true 处理
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    /**
     * Activity暂停状态，停止web’的加载
     */
    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()

    }

    /**
     * Activity的resume，同步web的状态resume
     */
    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }


    companion object {
        fun openUrl(context: Context, url: String) {
            context.startActivity(Intent(context, WebActivity::class.java).also {
                it.putExtra("url", url)
            })
        }
    }
}