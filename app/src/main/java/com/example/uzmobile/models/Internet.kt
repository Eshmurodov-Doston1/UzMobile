package com.example.uzmobile.models

class Internet {
    var mb:String?=null
    var gb:String?=null
    var name_price:String?=null
    var price:String?=null
    var mb_text:String?=null
    var mb_count:String?=null
    var day_text:String?=null
    var day_count:String?=null
    var activeCode:String?=null


    constructor(
        mb: String?,
        gb: String?,
        name_price: String?,
        price: String?,
        mb_text: String?,
        mb_count: String?,
        day_text: String?,
        day_count: String?,
        activeCode: String?
    ) {
        this.mb = mb
        this.gb = gb
        this.name_price = name_price
        this.price = price
        this.mb_text = mb_text
        this.mb_count = mb_count
        this.day_text = day_text
        this.day_count = day_count
        this.activeCode = activeCode
    }

    constructor()
}