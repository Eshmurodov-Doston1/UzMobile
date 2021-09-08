package com.example.uzmobile.models

class Service {
    var name:String?=null
    var info1:String?=null
    var onCode:String?=null
    var offCode:String?=null



    constructor()
    constructor(name: String?, info1: String?, onCode: String?, offCode: String?) {
        this.name = name
        this.info1 = info1
        this.onCode = onCode
        this.offCode = offCode
    }

}