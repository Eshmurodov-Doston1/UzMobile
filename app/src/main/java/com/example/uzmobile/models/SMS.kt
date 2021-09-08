package com.example.uzmobile.models

class SMS {
    var name:String?=null
    var price:String?=null
    var summ_user:String?=null
    var count_sms:String?=null
    var day_sms:String?=null
    var activeCode:String?=null
    var offCode:String?=null


    constructor(
        name: String?,
        price: String?,
        summ_user: String?,
        count_sms: String?,
        day_sms: String?,
        activeCode: String?,
        offCode: String?
    ) {
        this.name = name
        this.price = price
        this.summ_user = summ_user
        this.count_sms = count_sms
        this.day_sms = day_sms
        this.activeCode = activeCode
        this.offCode = offCode
    }

    constructor()
}