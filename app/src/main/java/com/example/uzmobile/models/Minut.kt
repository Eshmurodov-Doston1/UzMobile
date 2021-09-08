package com.example.uzmobile.models

class Minut {
    var name:String?=null
    var count_min:String?=null
    var day_month:String?=null
    var onCode:String?=null
    var summ:String?=null
    var code:String?=null


    constructor()
    constructor(
        name: String?,
        count_min: String?,
        day_month: String?,
        onCode: String?,
        summ: String?,
        code: String?
    ) {
        this.name = name
        this.count_min = count_min
        this.day_month = day_month
        this.onCode = onCode
        this.summ = summ
        this.code = code
    }
}