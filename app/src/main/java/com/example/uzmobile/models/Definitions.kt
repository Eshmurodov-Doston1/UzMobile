package com.example.uzmobile.models

import java.io.Serializable

class Definitions:Serializable {
    var code:String?=null
    var info:String?=null
    var info_min:String?=null
    var mont_mb:String?=null
    var month_min:String?=null
    var month_sms:String?=null
    var summ_mont:String?=null
    var name:String?=null

    constructor(
        code: String?,
        info: String?,
        info_min: String?,
        mont_mb: String?,
        month_min: String?,
        month_sms: String?,
        summ_mont: String?,
        name: String?
    ) {
        this.code = code
        this.info = info
        this.info_min = info_min
        this.mont_mb = mont_mb
        this.month_min = month_min
        this.month_sms = month_sms
        this.summ_mont = summ_mont
        this.name = name
    }
    constructor()
}