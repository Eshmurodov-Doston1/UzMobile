package com.example.uzmobile.models

class SMSCountry {
    var name:String?=null
    var price:String?=null
    var count_sms:String?=null
    var day_sms:String?=null
    var activeCode:String?=null
    var info:String?=null



    constructor()
    constructor(
        name: String?,
        price: String?,
        count_sms: String?,
        day_sms: String?,
        activeCode: String?,
        info: String?
    ) {
        this.name = name
        this.price = price
        this.count_sms = count_sms
        this.day_sms = day_sms
        this.activeCode = activeCode
        this.info = info
    }
}