package com.mattkula.template

import android.app.Application

class TemplateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}
