package com.example.uzmobile.models

import java.io.Serializable

class News:Serializable {
    var name:String?=null
    var info1:String?=null
    var image1:String?=null
    var info2:String?=null
    var image2:String?=null
    var date:String?=null


    constructor()

    constructor(
        name: String?,
        info1: String?,
        image1: String?,
        info2: String?,
        image2: String?,
        date: String?
    ) {
        this.name = name
        this.info1 = info1
        this.image1 = image1
        this.info2 = info2
        this.image2 = image2
        this.date = date
    }

}