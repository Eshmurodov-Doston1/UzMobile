package com.example.uzmobile.models

class Replacement {
    var name:String?=null
    var getMinut:String?=null
    var setMinut:String?=null
    var price:String?=null
    var day:String?=null
    var onCodetext:String?=null
    var code:String?=null


    constructor(
        name: String?,
        getMinut: String?,
        setMinut: String?,
        price: String?,
        day: String?,
        onCodetext: String?,
        code: String?
    ) {
        this.name = name
        this.getMinut = getMinut
        this.setMinut = setMinut
        this.price = price
        this.day = day
        this.onCodetext = onCodetext
        this.code = code
    }

    constructor()
}