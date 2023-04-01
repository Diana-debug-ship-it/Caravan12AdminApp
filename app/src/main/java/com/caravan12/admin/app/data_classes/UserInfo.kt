package com.caravan12.admin.app.data_classes

class UserInfo {
    var name : String? = null
    var email : String? = null
    var number : String? = null

    constructor(email : String?, name : String?, number : String?) {
        this.email = email
        this.name = name
        this.number = number
    }

    constructor() {}
}
