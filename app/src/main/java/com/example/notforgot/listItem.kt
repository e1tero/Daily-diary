package com.example.notforgot

class listItem  {
    var id: String = ""
    var title: String = ""
    var details: String = ""
    var datetime: String = ""

    constructor(id: String, title: String, details: String, dateTime: String = "") {
        this.id = id
        this.title = title
        this.details = details
        this.datetime = datetime
    }
}