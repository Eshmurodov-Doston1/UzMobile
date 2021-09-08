package com.example.uzmobile.models

class USSD {
    var code:String?=null
    var name:String?=null


    constructor()
    constructor(code: String?, name: String?) {
        this.code = code
        this.name = name
    }

}