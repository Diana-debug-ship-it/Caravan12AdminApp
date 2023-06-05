package com.caravan12.admin.app.data_classes

class EventInfo{
    var title: String? = null
    var description: String? = null
    var id: String? = null

    constructor(title: String?, description: String?, id: String?) {
        this.title = title
        this.description = description
        this.id = id
    }

    constructor() {}
}
